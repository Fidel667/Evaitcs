package com.example.expensetracker.service;

import com.example.expensetracker.dto.TransactionForm;
import com.example.expensetracker.entity.Account;
import com.example.expensetracker.entity.Category;
import com.example.expensetracker.entity.Transaction;
import com.example.expensetracker.repository.AccountRepository;
import com.example.expensetracker.repository.CategoryRepository;
import com.example.expensetracker.repository.TransactionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public Transaction findById(Long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Transaction not found: " + id));
    }

    @Transactional(readOnly = true)
    public List<Transaction> findFiltered(LocalDate start, LocalDate end, Long categoryId) {
        if (start == null || end == null) {
            LocalDate today = LocalDate.now();
            start = today.withDayOfMonth(1);
            end = today.withDayOfMonth(today.lengthOfMonth());
        }
        if (categoryId != null) {
            return transactionRepository
                    .findByOccurredOnBetweenAndCategory_IdOrderByOccurredOnDesc(start, end, categoryId);
        }
        return transactionRepository.findByOccurredOnBetweenOrderByOccurredOnDesc(start, end);
    }

    public Transaction create(TransactionForm form) {
        Account account = accountRepository.findById(form.getAccountId())
                .orElseThrow(() -> new EntityNotFoundException("Account not found: " + form.getAccountId()));
        Category category = categoryRepository.findById(form.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found: " + form.getCategoryId()));

        Transaction tx = Transaction.builder()
                .amount(form.getAmount())
                .occurredOn(form.getOccurredOn())
                .note(form.getNote())
                .account(account)
                .category(category)
                .build();
        return transactionRepository.save(tx);
    }

    public Transaction update(Long id, TransactionForm form) {
        Transaction tx = findById(id);
        Account account = accountRepository.findById(form.getAccountId())
                .orElseThrow(() -> new EntityNotFoundException("Account not found: " + form.getAccountId()));
        Category category = categoryRepository.findById(form.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found: " + form.getCategoryId()));

        tx.setAmount(form.getAmount());
        tx.setOccurredOn(form.getOccurredOn());
        tx.setNote(form.getNote());
        tx.setAccount(account);
        tx.setCategory(category);
        return transactionRepository.save(tx);
    }

    public void delete(Long id) {
        if (!transactionRepository.existsById(id)) {
            throw new EntityNotFoundException("Transaction not found: " + id);
        }
        transactionRepository.deleteById(id);
    }
}
