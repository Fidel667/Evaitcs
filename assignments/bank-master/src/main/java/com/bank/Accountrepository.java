package com.bank;

import com.bank.model.Account;
import org.springframework.stereotype.Repository;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import java.util.*;

// ===================================================
// AccountRepository - Database Layer
//
// @Repository:
//   1. Registers it as a Bean
//   2. Translates database errors automatically
//
// @PostConstruct: loads fake data on startup
// @PreDestroy:    saves data before shutdown
// ===================================================

@Repository
public class AccountRepository {

    // Fake in-memory database
    private Map<String, Account> database = new HashMap<>();

    // -----------------------------------------------
    // @PostConstruct - runs after creation and injection
    // -----------------------------------------------
    @PostConstruct
    public void init() {
        System.out.println("Repository ready - loading initial data...");

        // Fake data for testing
        database.put("ACC001", new Account("ACC001", "Mohammed Ali",   10000));
        database.put("ACC002", new Account("ACC002", "Sara Ahmed",     25000));
        database.put("ACC003", new Account("ACC003", "Khalid Mahmoud",  5000));

        System.out.println("Loaded " + database.size() + " accounts");
    }

    // -----------------------------------------------
    // @PreDestroy - runs before app shuts down
    // -----------------------------------------------
    @PreDestroy
    public void cleanup() {
        System.out.println("Repository shutting down - saving data...");
        System.out.println("Saved " + database.size() + " accounts successfully");
    }

    // -----------------------------------------------
    // Core operations
    // -----------------------------------------------
    public Account findById(String id) {
        return database.get(id);
    }

    public void save(Account account) {
        database.put(account.getId(), account);
    }

    public List<Account> findAll() {
        return new ArrayList<>(database.values());
    }
}