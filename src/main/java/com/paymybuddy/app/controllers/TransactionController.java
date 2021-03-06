package com.paymybuddy.app.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.paymybuddy.app.models.AccountBank;
import com.paymybuddy.app.models.Transaction;
import com.paymybuddy.app.models.User;
import com.paymybuddy.app.services.AccountBankService;
import com.paymybuddy.app.services.TransactionService;
import com.paymybuddy.app.services.UserService;


@Controller
public class TransactionController {

	@Autowired
	private TransactionService transactionService;

	@Autowired
	private AccountBankService accountBankService;

	@Autowired
	private UserService userService;

	@GetMapping("/createTransfer")
	public String transactionForm(Model model) {
		model.addAttribute("createTransferForm", new Transaction());
		List<User> userList = new ArrayList<>(userService.getUserConnected().getListFriend());
		model.addAttribute("users", userList);
		return "transaction/transactionCreate";
	}

	//Récuperation des transactions
	@GetMapping("/transactions")
	public String transaction(Model model, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size) {
		int currentPage = page.orElse(1);
		int pageSize = size.orElse(6);

		Page<Transaction> trans = transactionService.pageFindAllByUserConnected(currentPage - 1, pageSize, false);
		int totalPages = trans.getTotalPages();

		model.addAttribute("totalPages", totalPages);

		List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
		model.addAttribute("pageNumbers", pageNumbers);

		Iterable<AccountBank> bankList = accountBankService.findAll();
		List<User> userList = new ArrayList<>(userService.getUserConnected().getListFriend());

		model.addAttribute("createTransferForm", new Transaction());
		model.addAttribute("users", userList);
		model.addAttribute("idUser");
		model.addAttribute("transactions", trans);
		model.addAttribute("bank", bankList);
		return "transaction/transactions";
	}

	@PostMapping("/transactions")
	public String submissionResult(@Valid @ModelAttribute("createTransferForm") Transaction transaction, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "transaction/transactions";
		}
		transactionService.createTransaction(transaction);

		return "redirect:/transactions";
	}

	@GetMapping("/bank")
	public String transactionBankSendkForm(Model model) {
		model.addAttribute("createTransferForm", new Transaction());
		model.addAttribute("banks", accountBankService.findAllAccountsByIdUser(userService.getUserConnected().getId()));
		return "transaction/transactionBankCreate";
	}

	@PostMapping("/bankTransaction")
	public String submissionBankSend(@Valid @ModelAttribute("createTransferForm") Transaction transaction, BindingResult result) throws Exception {
		transactionService.createTransactionBank(transaction);
		return "redirect:/profil";
	}




}
