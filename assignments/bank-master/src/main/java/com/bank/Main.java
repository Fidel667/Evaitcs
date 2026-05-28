package com.bank;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

// ===================================================
// Main - Entry Point
//
// Only one getBean() here
// Spring handles everything else
// ===================================================

public class Main {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("Bank starting...");
        System.out.println("=================================");

        // 1. Spring starts and reads config
        // 2. Scans and creates Beans
        // 3. Injects dependencies
        // 4. Runs @PostConstruct
        var context = new AnnotationConfigApplicationContext(BankConfig.class);

        System.out.println("=================================");
        System.out.println("Bank ready! Starting operations...");
        System.out.println("=================================");

        // getBean() only once - everything else is injected
        TransferService transferService = context.getBean(TransferService.class);

        // -----------------------------------------------
        // Test 1: Normal transfer
        // -----------------------------------------------
        System.out.println("--- Transfer 1: Normal ---");
        transferService.transfer("ACC001", "ACC002", 3000);

        // -----------------------------------------------
        // Test 2: Exceeds transfer limit
        // -----------------------------------------------
        System.out.println("--- Transfer 2: Exceeds limit ---");
        transferService.transfer("ACC001", "ACC002", 99999);

        // -----------------------------------------------
        // Test 3: Insufficient balance
        // -----------------------------------------------
        System.out.println("--- Transfer 3: Insufficient balance ---");
        transferService.transfer("ACC003", "ACC001", 99999);

        // -----------------------------------------------
        // Show final accounts
        // -----------------------------------------------
        System.out.println("=================================");
        System.out.println("Final accounts:");
        System.out.println("=================================");
        AccountRepository repo = context.getBean(AccountRepository.class);
        repo.findAll().forEach(acc -> System.out.println("  " + acc));

        // Shutdown -> runs @PreDestroy
        System.out.println("=================================");
        System.out.println("Bank shutting down...");
        System.out.println("=================================");
        context.close();
    }
}