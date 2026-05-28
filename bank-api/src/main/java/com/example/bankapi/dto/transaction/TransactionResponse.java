package com.example.bankapi.dto.transaction;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class TransactionResponse {
    private Long id;
    private String type;
    private BigDecimal amount;
    private String fromAccountNumber;
    private String toAccountNumber; // null for DEPOSIT / WITHDRAWAL
    private String description;
    private LocalDateTime occurredAt;
}
