package com.example.expensetracker.service;

import com.example.expensetracker.dto.AccountForm;
import com.example.expensetracker.entity.Account;
import com.example.expensetracker.repository.AccountRepository;
import com.example.expensetracker.repository.TransactionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    @Transactional(readOnly = true)
    public List<Account> findAll() {
        return accountRepository.findAllByOrderByNameAsc();
    }

    @Transactional(readOnly = true)
    public Account findById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Account not found: " + id));
    }

    public Account create(AccountForm form) {
        Account account = Account.builder()
                .name(form.getName().trim())
                .type(form.getType())
                .openingBalance(form.getOpeningBalance())
                .build();
        return accountRepository.save(account);
    }

    public Account update(Long id, AccountForm form) {
        Account account = findById(id);
        account.setName(form.getName().trim());
        account.setType(form.getType());
        account.setOpeningBalance(form.getOpeningBalance());
        return accountRepository.save(account);
    }

    public void delete(Long id) {
        if (!accountRepository.existsById(id)) {
            throw new EntityNotFoundException("Account not found: " + id);
        }
        if (transactionRepository.countByAccount_Id(id) > 0) {
            throw new AccountInUseException(
                "Cannot delete account — it has transactions. Remove them first.");
        }
        accountRepository.deleteById(id);
    }
}
