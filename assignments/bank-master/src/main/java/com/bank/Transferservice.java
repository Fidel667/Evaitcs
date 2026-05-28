package com.bank;

import com.bank.model.Account;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;

// ===================================================
// TransferService - Bank Transfer Logic
//
// Here we see:
//   1. Constructor Injection (the right way)
//   2. @Qualifier (choose SMS or Email)
//   3. @Value (transfer limit from settings)
// ===================================================

@Service
public class TransferService {

    // 1. Dependencies - all final
    private final AccountRepository   accountRepository;
    private final NotificationService notificationService;

    // 2. @Value gets transfer limit from app.properties
    @Value("${bank.transfer.limit}")
    private double transferLimit;

    // -----------------------------------------------
    // Constructor Injection
    // @Qualifier picks SMS or Email
    // -----------------------------------------------
    public TransferService(
            AccountRepository accountRepository,
            @Qualifier("smsNotification") NotificationService notificationService) {

        this.accountRepository   = accountRepository;
        this.notificationService = notificationService;
    }

    // -----------------------------------------------
    // Transfer operation
    // -----------------------------------------------
    public void transfer(String fromId, String toId, double amount) {

        // 1. Check transfer limit
        if (amount > transferLimit) {
            System.out.println("Amount exceeds limit: " + transferLimit);
            return;
        }

        // 2. Get accounts
        Account from = accountRepository.findById(fromId);
        Account to   = accountRepository.findById(toId);

        if (from == null || to == null) {
            System.out.println("Account not found!");
            return;
        }

        // 3. Check balance
        if (from.getBalance() < amount) {
            System.out.println("Insufficient balance!");
            return;
        }

        // 4. Execute transfer
        from.setBalance(from.getBalance() - amount);
        to.setBalance(to.getBalance()     + amount);

        // 5. Save
        accountRepository.save(from);
        accountRepository.save(to);

        // 6. Send notification
        notificationService.send(
                "Transferred " + amount + " from " + from.getOwner() + " to " + to.getOwner()
        );

        System.out.println("Transfer completed successfully!");
    }
}