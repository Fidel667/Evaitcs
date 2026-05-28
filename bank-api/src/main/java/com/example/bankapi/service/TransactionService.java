package com.example.bankapi.service;

import com.example.bankapi.dto.transaction.TransactionResponse;
import com.example.bankapi.entity.BankTransaction;
import com.example.bankapi.exception.ResourceNotFoundException;
import com.example.bankapi.repository.AccountRepository;
import com.example.bankapi.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.RoundingMode;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    public List<TransactionResponse> findHistoryForAccount(Long accountId, String username) {
        // Verify ownership — returns 404 if not found or not owned
        accountRepository.findByIdAndOwner_Username(accountId, username)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found: " + accountId));

        return transactionRepository.findHistoryForAccount(accountId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private TransactionResponse toResponse(BankTransaction tx) {
        return TransactionResponse.builder()
                .id(tx.getId())
                .type(tx.getType().name())
                .amount(tx.getAmount().setScale(2, RoundingMode.HALF_UP))
                .fromAccountNumber(tx.getFromAccount().getAccountNumber())
                .toAccountNumber(tx.getToAccount() != null ? tx.getToAccount().getAccountNumber() : null)
                .description(tx.getDescription())
                .occurredAt(tx.getOccurredAt())
                .build();
    }
}
