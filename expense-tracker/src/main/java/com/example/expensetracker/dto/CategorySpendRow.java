package com.example.expensetracker.dto;

import java.math.BigDecimal;

public class CategorySpendRow {

    private final String categoryName;
    private final BigDecimal total;

    public CategorySpendRow(String categoryName, BigDecimal total) {
        this.categoryName = categoryName;
        this.total = total;
    }

    public String getCategoryName() { return categoryName; }
    public BigDecimal getTotal() { return total; }
}
