package com.example.expensetracker.repository;

import com.example.expensetracker.entity.Category;
import com.example.expensetracker.entity.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAllByOrderByNameAsc();
    List<Category> findByType(CategoryType type);
}
