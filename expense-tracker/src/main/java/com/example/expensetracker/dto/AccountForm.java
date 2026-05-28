package com.example.expensetracker.dto;

import com.example.expensetracker.entity.AccountType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import java.math.BigDecimal;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class AccountForm {

    private Long id;

    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name cannot exceed 100 characters")
    private String name;

    @NotNull(message = "Account type is required")
    private AccountType type;

    @NotNull(message = "Opening balance is required")
    @DecimalMin(value = "0.00", message = "Opening balance cannot be negative")
    private BigDecimal openingBalance;
}
