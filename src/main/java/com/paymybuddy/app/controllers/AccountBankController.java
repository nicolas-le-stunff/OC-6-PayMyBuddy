package com.paymybuddy.app.controllers;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.paymybuddy.app.models.AccountBank;
import com.paymybuddy.app.repository.AccountBankRepository;
import com.paymybuddy.app.repository.UserRepository;



@Controller
public class AccountBankController {
	
	@Autowired
	private AccountBankRepository accountBankRepository;
	
	@Autowired
	private UserRepository userRepository;
	
    @GetMapping("/accountBanks")
    public String accountBankList(Model model) {
    	int idUser = userRepository.findByEmail(nameUserConnect()).getId();
    	Optional<AccountBank> accounts = accountBankRepository.findById(idUser);
    	if(accounts.isPresent()) {
    		model.addAttribute("accounts",accounts.get());
    	}
    	
    	return "accountBankList";
    }
	
	
	@GetMapping("/createAccountBank")
    public String accountBankForm(Model model) {
        model.addAttribute("accountBankForm", new AccountBank());
        
        return "createAccountBank";
    }

    @PostMapping("/accountBank")
    public String submissionResult(@ModelAttribute("personForm") AccountBank accountBank,HttpServletRequest request) {
    	accountBank.setUser(userRepository.findByEmail(nameUserConnect()));
    	//TODO Vérification si user exist
    	accountBankRepository.save(accountBank);
        return "index";
    }
    
    private String nameUserConnect() {
    	Authentication authentification = SecurityContextHolder.getContext().getAuthentication();
    	return authentification.getName();
    }
    

	
}
