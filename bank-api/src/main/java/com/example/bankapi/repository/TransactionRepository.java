package com.example.bankapi.repository;

import com.example.bankapi.entity.BankTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<BankTransaction, Long> {

    @Query("""
        SELECT t FROM BankTransaction t
        WHERE t.fromAccount.id = :accountId OR t.toAccount.id = :accountId
        ORDER BY t.occurredAt DESC
        """)
    List<BankTransaction> findHistoryForAccount(@Param("accountId") Long accountId);

    // Belt-and-suspenders immutability: override destructive JpaRepository methods
    @Override
    default void deleteById(Long id) {
        throw new UnsupportedOperationException("Bank transactions are immutable and cannot be deleted.");
    }

    @Override
    default void delete(BankTransaction entity) {
        throw new UnsupportedOperationException("Bank transactions are immutable and cannot be deleted.");
    }

    @Override
    default void deleteAll() {
        throw new UnsupportedOperationException("Bank transactions are immutable and cannot be deleted.");
    }
}
