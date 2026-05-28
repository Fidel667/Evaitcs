package com.example.expensetracker.controller;

import com.example.expensetracker.dto.AccountForm;
import com.example.expensetracker.entity.Account;
import com.example.expensetracker.entity.AccountType;
import com.example.expensetracker.service.AccountInUseException;
import com.example.expensetracker.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("accounts", accountService.findAll());
        return "accounts/list";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("accountForm", new AccountForm());
        model.addAttribute("accountTypes", AccountType.values());
        model.addAttribute("mode", "create");
        return "accounts/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("accountForm") AccountForm form,
                         BindingResult result, Model model, RedirectAttributes flash) {
        if (result.hasErrors()) {
            model.addAttribute("accountTypes", AccountType.values());
            model.addAttribute("mode", "create");
            return "accounts/form";
        }
        accountService.create(form);
        flash.addFlashAttribute("success", "Account created.");
        return "redirect:/accounts";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Account a = accountService.findById(id);
        AccountForm form = new AccountForm(a.getId(), a.getName(), a.getType(), a.getOpeningBalance());
        model.addAttribute("accountForm", form);
        model.addAttribute("accountTypes", AccountType.values());
        model.addAttribute("mode", "edit");
        return "accounts/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("accountForm") AccountForm form,
                         BindingResult result, Model model, RedirectAttributes flash) {
        if (result.hasErrors()) {
            model.addAttribute("accountTypes", AccountType.values());
            model.addAttribute("mode", "edit");
            return "accounts/form";
        }
        accountService.update(id, form);
        flash.addFlashAttribute("success", "Account updated.");
        return "redirect:/accounts";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes flash) {
        try {
            accountService.delete(id);
            flash.addFlashAttribute("success", "Account deleted.");
        } catch (AccountInUseException e) {
            flash.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/accounts";
    }
}
