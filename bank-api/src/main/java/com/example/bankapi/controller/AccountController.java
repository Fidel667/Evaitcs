package com.example.bankapi.controller;

import com.example.bankapi.dto.account.*;
import com.example.bankapi.dto.transaction.TransactionResponse;
import com.example.bankapi.service.AccountService;
import com.example.bankapi.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final TransactionService transactionService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AccountResponse openAccount(@RequestBody(required = false) OpenAccountRequest request,
                                       @AuthenticationPrincipal UserDetails user) {
        return accountService.openAccount(user.getUsername());
    }

    @GetMapping
    public List<AccountResponse> listMyAccounts(@AuthenticationPrincipal UserDetails user) {
        return accountService.findMyAccounts(user.getUsername());
    }

    @GetMapping("/{id}")
    public AccountResponse getMyAccount(@PathVariable Long id,
                                        @AuthenticationPrincipal UserDetails user) {
        return accountService.findMyAccount(id, user.getUsername());
    }

    @PostMapping("/{id}/deposits")
    public AccountResponse deposit(@PathVariable Long id,
                                   @Valid @RequestBody DepositRequest request,
                                   @AuthenticationPrincipal UserDetails user) {
        return accountService.deposit(id, request, user.getUsername());
    }

    @PostMapping("/{id}/withdrawals")
    public AccountResponse withdraw(@PathVariable Long id,
                                    @Valid @RequestBody WithdrawRequest request,
                                    @AuthenticationPrincipal UserDetails user) {
        return accountService.withdraw(id, request, user.getUsername());
    }

    @PostMapping("/{id}/transfers")
    public AccountResponse transfer(@PathVariable Long id,
                                    @Valid @RequestBody TransferRequest request,
                                    @AuthenticationPrincipal UserDetails user) {
        return accountService.transfer(id, request, user.getUsername());
    }

    @GetMapping("/{id}/transactions")
    public List<TransactionResponse> transactionHistory(@PathVariable Long id,
                                                        @AuthenticationPrincipal UserDetails user) {
        return transactionService.findHistoryForAccount(id, user.getUsername());
    }
}
