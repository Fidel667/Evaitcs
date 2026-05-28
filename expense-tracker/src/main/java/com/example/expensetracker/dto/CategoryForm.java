package com.example.expensetracker.dto;

import com.example.expensetracker.entity.CategoryType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CategoryForm {

    private Long id;

    @NotBlank(message = "Name is required")
    @Size(max = 80, message = "Name cannot exceed 80 characters")
    private String name;

    @NotNull(message = "Category type is required")
    private CategoryType type;
}
