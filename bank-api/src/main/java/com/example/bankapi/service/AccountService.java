package com.example.bankapi.service;

import com.example.bankapi.dto.account.*;
import com.example.bankapi.entity.*;
import com.example.bankapi.exception.*;
import com.example.bankapi.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    private final Random random = new Random();

    // ─── Helpers ──────────────────────────────────────────────────────────────

    private AccountResponse toResponse(Account a) {
        return AccountResponse.builder()
                .id(a.getId())
                .accountNumber(a.getAccountNumber())
                .balance(a.getBalance().setScale(2, RoundingMode.HALF_UP))
                .status(a.getStatus().name())
                .createdAt(a.getCreatedAt())
                .build();
    }

    /** Generate a unique 12-digit numeric account number. */
    private String generateAccountNumber() {
        String number;
        do {
            // 100_000_000_000 to 999_999_999_999 — always 12 digits
            long n = 100_000_000_000L + (long) (random.nextDouble() * 900_000_000_000L);
            number = String.valueOf(n);
        } while (accountRepository.existsByAccountNumber(number));
        return number;
    }

    // ─── Service methods ──────────────────────────────────────────────────────

    public AccountResponse openAccount(String username) {
        User owner = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + username));

        Account account = Account.builder()
                .accountNumber(generateAccountNumber())
                .owner(owner)
                .balance(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP))
                .status(AccountStatus.ACTIVE)
                .build();

        return toResponse(accountRepository.save(account));
    }

    @Transactional(readOnly = true)
    public List<AccountResponse> findMyAccounts(String username) {
        return accountRepository.findByOwner_UsernameOrderByCreatedAtDesc(username)
                .stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public AccountResponse findMyAccount(Long id, String username) {
        Account account = accountRepository.findByIdAndOwner_Username(id, username)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found: " + id));
        return toResponse(account);
    }

    public AccountResponse deposit(Long accountId, DepositRequest request, String username) {
        Account account = accountRepository.findByIdAndOwner_Username(accountId, username)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found: " + accountId));

        if (account.getStatus() != AccountStatus.ACTIVE) {
            throw new IllegalStateException("Account is " + account.getStatus() + " and cannot accept deposits.");
        }

        account.setBalance(account.getBalance().add(request.getAmount()).setScale(2, RoundingMode.HALF_UP));

        transactionRepository.save(BankTransaction.builder()
                .type(TransactionType.DEPOSIT)
                .amount(request.getAmount().setScale(2, RoundingMode.HALF_UP))
                .fromAccount(account)
                .build());

        return toResponse(accountRepository.save(account));
    }

    public AccountResponse withdraw(Long accountId, WithdrawRequest request, String username) {
        Account account = accountRepository.findByIdAndOwner_Username(accountId, username)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found: " + accountId));

        if (account.getStatus() != AccountStatus.ACTIVE) {
            throw new IllegalStateException("Account is " + account.getStatus() + " and cannot be withdrawn from.");
        }

        // Use compareTo — BigDecimal.equals() considers scale
        if (account.getBalance().compareTo(request.getAmount()) < 0) {
            throw new InsufficientFundsException(
                    "Insufficient funds. Available: " + account.getBalance().setScale(2, RoundingMode.HALF_UP));
        }

        account.setBalance(account.getBalance().subtract(request.getAmount()).setScale(2, RoundingMode.HALF_UP));

        transactionRepository.save(BankTransaction.builder()
                .type(TransactionType.WITHDRAWAL)
                .amount(request.getAmount().setScale(2, RoundingMode.HALF_UP))
                .fromAccount(account)
                .build());

        return toResponse(accountRepository.save(account));
    }

    @Transactional
    public AccountResponse transfer(Long fromAccountId, TransferRequest request, String username) {
        // 1. Load source — must exist AND be owned by caller
        Account source = accountRepository.findByIdAndOwner_Username(fromAccountId, username)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found: " + fromAccountId));

        // 2. Load destination by account number — NOT scoped by owner (transfers can cross users)
        Account destination = accountRepository.findByAccountNumber(request.getToAccountNumber())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Destination account not found: " + request.getToAccountNumber()));

        // 3. Same-account transfer rejected with 400
        if (source.getId().equals(destination.getId())) {
            throw new InvalidTransferException("Cannot transfer to the same account.");
        }

        // 4. Both must be ACTIVE
        if (source.getStatus() != AccountStatus.ACTIVE) {
            throw new IllegalStateException("Source account is " + source.getStatus() + ".");
        }
        if (destination.getStatus() != AccountStatus.ACTIVE) {
            throw new IllegalStateException("Destination account is " + destination.getStatus() + ".");
        }

        // 5. Overdraft check
        if (source.getBalance().compareTo(request.getAmount()) < 0) {
            throw new InsufficientFundsException(
                    "Insufficient funds. Available: " + source.getBalance().setScale(2, RoundingMode.HALF_UP));
        }

        // 6. Debit source, credit destination
        source.setBalance(source.getBalance().subtract(request.getAmount()).setScale(2, RoundingMode.HALF_UP));
        destination.setBalance(destination.getBalance().add(request.getAmount()).setScale(2, RoundingMode.HALF_UP));
        accountRepository.save(source);
        accountRepository.save(destination);

        // 7. Single TRANSFER transaction record — if this throws, steps 6 roll back
        transactionRepository.save(BankTransaction.builder()
                .type(TransactionType.TRANSFER)
                .amount(request.getAmount().setScale(2, RoundingMode.HALF_UP))
                .fromAccount(source)
                .toAccount(destination)
                .description(request.getDescription())
                .build());

        return toResponse(source);
    }
}
