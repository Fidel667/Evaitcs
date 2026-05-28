package org.example.jdbankapp;

import org.example.jdbankapp.entities.BankAccount;
import org.example.jdbankapp.repositories.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;

@SpringBootApplication
public class JdBankAppApplication implements CommandLineRunner {

    @Autowired
    private BankAccountRepository repository;

    public static void main(String[] args) {
        SpringApplication.run(JdBankAppApplication.class, args);
    }

    @Override
    public void run (String... args) throws Exception {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setBalance(new BigDecimal("5000"));

    }
}
