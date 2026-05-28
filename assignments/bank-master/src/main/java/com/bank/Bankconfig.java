package com.bank;

import org.springframework.context.annotation.*;
import org.springframework.beans.factory.annotation.Value;

// ===================================================
// BankConfig - The heart of configuration
// Spring knows here:
//   1. Where to find Beans (@ComponentScan)
//   2. Read external settings (@PropertySource)
//   3. Create Beans that need special setup (@Bean)
// ===================================================

@Configuration
@ComponentScan("com.bank")
@PropertySource("classpath:app.properties")
public class BankConfig {

    // 1. @Value gets the value from app.properties
    @Value("${bank.name}")
    private String bankName;

    @Value("${bank.transfer.limit}")
    private double transferLimit;

    // 2. @Bean - print bank info on startup
    @Bean
    public String bankInfo() {
        System.out.println("Bank: " + bankName);
        System.out.println("Transfer limit: " + transferLimit);
        return bankName;
    }
}