package com.example.expensetracker.dto;

import com.example.expensetracker.entity.Transaction;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class DashboardData {

    private final LocalDate start;
    private final LocalDate end;
    private final BigDecimal totalExpense;
    private final List<CategorySpendRow> topCategories;
    private final List<Transaction> recentTransactions;

    public DashboardData(LocalDate start, LocalDate end, BigDecimal totalExpense,
                         List<CategorySpendRow> topCategories,
                         List<Transaction> recentTransactions) {
        this.start = start;
        this.end = end;
        this.totalExpense = totalExpense;
        this.topCategories = topCategories;
        this.recentTransactions = recentTransactions;
    }

    public LocalDate getStart() { return start; }
    public LocalDate getEnd() { return end; }
    public BigDecimal getTotalExpense() { return totalExpense; }
    public List<CategorySpendRow> getTopCategories() { return topCategories; }
    public List<Transaction> getRecentTransactions() { return recentTransactions; }
}
