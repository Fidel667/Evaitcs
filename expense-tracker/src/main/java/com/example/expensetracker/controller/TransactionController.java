package com.example.expensetracker.controller;

import com.example.expensetracker.dto.TransactionForm;
import com.example.expensetracker.entity.Transaction;
import com.example.expensetracker.service.AccountService;
import com.example.expensetracker.service.CategoryService;
import com.example.expensetracker.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.time.LocalDate;

@Controller
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;
    private final AccountService accountService;
    private final CategoryService categoryService;

    /** Populates dropdowns on every request to this controller — prevents null on re-render */
    @ModelAttribute
    public void populateDropdowns(Model model) {
        model.addAttribute("accounts", accountService.findAll());
        model.addAttribute("categories", categoryService.findAll());
    }

    @GetMapping
    public String list(@RequestParam(required = false)
                       @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
                       @RequestParam(required = false)
                       @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end,
                       @RequestParam(required = false) Long categoryId,
                       Model model) {
        model.addAttribute("transactions", transactionService.findFiltered(start, end, categoryId));
        model.addAttribute("start", start);
        model.addAttribute("end", end);
        model.addAttribute("selectedCategoryId", categoryId);
        return "transactions/list";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("transactionForm", new TransactionForm());
        model.addAttribute("mode", "create");
        return "transactions/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("transactionForm") TransactionForm form,
                         BindingResult result, Model model, RedirectAttributes flash) {
        if (result.hasErrors()) {
            model.addAttribute("mode", "create");
            return "transactions/form";
        }
        transactionService.create(form);
        flash.addFlashAttribute("success", "Transaction added.");
        return "redirect:/transactions";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Transaction tx = transactionService.findById(id);
        TransactionForm form = new TransactionForm(
                tx.getId(), tx.getAmount(), tx.getOccurredOn(),
                tx.getNote(), tx.getAccount().getId(), tx.getCategory().getId());
        model.addAttribute("transactionForm", form);
        model.addAttribute("mode", "edit");
        return "transactions/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("transactionForm") TransactionForm form,
                         BindingResult result, Model model, RedirectAttributes flash) {
        if (result.hasErrors()) {
            model.addAttribute("mode", "edit");
            return "transactions/form";
        }
        transactionService.update(id, form);
        flash.addFlashAttribute("success", "Transaction updated.");
        return "redirect:/transactions";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes flash) {
        transactionService.delete(id);
        flash.addFlashAttribute("success", "Transaction deleted.");
        return "redirect:/transactions";
    }
}
