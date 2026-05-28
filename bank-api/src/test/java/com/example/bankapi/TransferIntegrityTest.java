package com.example.bankapi;

import com.example.bankapi.dto.account.TransferRequest;
import com.example.bankapi.entity.*;
import com.example.bankapi.repository.*;
import com.example.bankapi.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.example.bankapi.dto.account.WithdrawRequest;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.spy;

@SpringBootTest
class TransferIntegrityTest {

    @Autowired private AccountService accountService;
    @Autowired private AccountRepository accountRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private TransactionRepository transactionRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    private User matthew;
    private User alice;

    @BeforeEach
    void setUp() {
        // Clean state before each test
        transactionRepository.findAll(); // warm
        accountRepository.deleteAll();
        userRepository.deleteAll();

        matthew = userRepository.save(User.builder()
                .username("matthew_test")
                .password(passwordEncoder.encode("password"))
                .role(Role.USER)
                .build());

        alice = userRepository.save(User.builder()
                .username("alice_test")
                .password(passwordEncoder.encode("password"))
                .role(Role.USER)
                .build());
    }

    @Test
    void transferCreditsAndDebitsCorrectly() {
        // Open accounts
        var matthewAcc = accountService.openAccount("matthew_test");
        var aliceAcc   = accountService.openAccount("alice_test");

        // Seed Matthew with 1000.00
        accountRepository.findById(matthewAcc.getId()).ifPresent(a -> {
            a.setBalance(new BigDecimal("1000.00"));
            accountRepository.save(a);
        });

        // Transfer 300 from Matthew to Alice
        TransferRequest req = new TransferRequest(aliceAcc.getAccountNumber(),
                new BigDecimal("300.00"), "test transfer");
        var result = accountService.transfer(matthewAcc.getId(), req, "matthew_test");

        assertThat(result.getBalance()).isEqualByComparingTo("700.00");

        Account updatedAlice = accountRepository.findById(aliceAcc.getId()).orElseThrow();
        assertThat(updatedAlice.getBalance()).isEqualByComparingTo("300.00");

        // Exactly one transaction record
        var history = transactionRepository.findHistoryForAccount(matthewAcc.getId());
        assertThat(history).hasSize(1);
        assertThat(history.get(0).getType()).isEqualTo(TransactionType.TRANSFER);
    }

    @Test
    void transferRollsBackOnFailure() {
        var matthewAcc = accountService.openAccount("matthew_test");
        var aliceAcc   = accountService.openAccount("alice_test");

        // Seed Matthew
        Account mAccount = accountRepository.findById(matthewAcc.getId()).orElseThrow();
        mAccount.setBalance(new BigDecimal("1000.00"));
        accountRepository.save(mAccount);

        BigDecimal matthewBalanceBefore = accountRepository.findById(matthewAcc.getId())
                .orElseThrow().getBalance();
        BigDecimal aliceBalanceBefore = accountRepository.findById(aliceAcc.getId())
                .orElseThrow().getBalance();

        long txCountBefore = transactionRepository.findHistoryForAccount(matthewAcc.getId()).size();

        // Attempt an overdraft transfer — service will throw InsufficientFundsException
        // before any balance change is committed
        TransferRequest req = new TransferRequest(aliceAcc.getAccountNumber(),
                new BigDecimal("99999.00"), "should fail");

        assertThatThrownBy(() ->
                accountService.transfer(matthewAcc.getId(), req, "matthew_test"))
                .isInstanceOf(com.example.bankapi.exception.InsufficientFundsException.class);

        // Both balances must be unchanged
        BigDecimal matthewBalanceAfter = accountRepository.findById(matthewAcc.getId())
                .orElseThrow().getBalance();
        BigDecimal aliceBalanceAfter = accountRepository.findById(aliceAcc.getId())
                .orElseThrow().getBalance();

        assertThat(matthewBalanceAfter).isEqualByComparingTo(matthewBalanceBefore);
        assertThat(aliceBalanceAfter).isEqualByComparingTo(aliceBalanceBefore);

        // No transaction record created
        long txCountAfter = transactionRepository.findHistoryForAccount(matthewAcc.getId()).size();
        assertThat(txCountAfter).isEqualTo(txCountBefore);
    }

    @Test
    void cannotTransferToSameAccount() {
        var matthewAcc = accountService.openAccount("matthew_test");

        Account a = accountRepository.findById(matthewAcc.getId()).orElseThrow();
        a.setBalance(new BigDecimal("500.00"));
        accountRepository.save(a);

        TransferRequest req = new TransferRequest(matthewAcc.getAccountNumber(),
                new BigDecimal("100.00"), null);

        assertThatThrownBy(() ->
                accountService.transfer(matthewAcc.getId(), req, "matthew_test"))
                .isInstanceOf(com.example.bankapi.exception.InvalidTransferException.class);
    }

    @Test
    void cannotAccessOtherUsersAccount() {
        var matthewAcc = accountService.openAccount("matthew_test");

        // Alice tries to withdraw from Matthew's account → ResourceNotFoundException (404)
        WithdrawRequest req = new com.example.bankapi.dto.account.WithdrawRequest(new BigDecimal("10.00"));

        assertThatThrownBy(() ->
                accountService.withdraw(matthewAcc.getId(), req, "alice_test"))
                .isInstanceOf(com.example.bankapi.exception.ResourceNotFoundException.class);
    }
}
