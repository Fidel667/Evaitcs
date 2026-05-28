package com.example.expensetracker.controller;

import com.example.expensetracker.dto.CategoryForm;
import com.example.expensetracker.entity.Category;
import com.example.expensetracker.entity.CategoryType;
import com.example.expensetracker.repository.TransactionRepository;
import com.example.expensetracker.service.CategoryInUseException;
import com.example.expensetracker.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final TransactionRepository transactionRepository;

    @GetMapping
    public String list(Model model) {
        List<Category> categories = categoryService.findAll();
        Map<Long, Long> txCounts = new HashMap<>();
        for (Category c : categories) {
            txCounts.put(c.getId(), transactionRepository.countByCategory_Id(c.getId()));
        }
        model.addAttribute("categories", categories);
        model.addAttribute("txCounts", txCounts);
        return "categories/list";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("categoryForm", new CategoryForm());
        model.addAttribute("categoryTypes", CategoryType.values());
        model.addAttribute("mode", "create");
        return "categories/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("categoryForm") CategoryForm form,
                         BindingResult result, Model model, RedirectAttributes flash) {
        if (result.hasErrors()) {
            model.addAttribute("categoryTypes", CategoryType.values());
            model.addAttribute("mode", "create");
            return "categories/form";
        }
        categoryService.create(form);
        flash.addFlashAttribute("success", "Category created.");
        return "redirect:/categories";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Category c = categoryService.findById(id);
        CategoryForm form = new CategoryForm(c.getId(), c.getName(), c.getType());
        model.addAttribute("categoryForm", form);
        model.addAttribute("categoryTypes", CategoryType.values());
        model.addAttribute("mode", "edit");
        return "categories/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("categoryForm") CategoryForm form,
                         BindingResult result, Model model, RedirectAttributes flash) {
        if (result.hasErrors()) {
            model.addAttribute("categoryTypes", CategoryType.values());
            model.addAttribute("mode", "edit");
            return "categories/form";
        }
        categoryService.update(id, form);
        flash.addFlashAttribute("success", "Category updated.");
        return "redirect:/categories";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes flash) {
        try {
            categoryService.delete(id);
            flash.addFlashAttribute("success", "Category deleted.");
        } catch (CategoryInUseException e) {
            flash.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/categories";
    }
}
