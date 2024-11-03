package com.helmes.demo.HelmesAssignment.controller;

import com.helmes.demo.HelmesAssignment.model.AppAccount;
import com.helmes.demo.HelmesAssignment.repository.AppAccountRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@CrossOrigin
@RestController
public class LoginController {

    private final AppAccountRepository appAccountRepository;

    public LoginController(AppAccountRepository appAccountRepository) {
        this.appAccountRepository = appAccountRepository;
    }

    @GetMapping("/login")
    public ResponseEntity<String> loginByUsername(@RequestParam String username) {
        Optional<AppAccount> appAccount = appAccountRepository.findByUsernameIgnoreCase(username);

        if (appAccount.isPresent()) {
            Long appAccountId = appAccount.get().getAppAccountId();
            String jsonResponse = "{\"appAccountId\":" + appAccountId + "}";
            return ResponseEntity.status(HttpStatus.OK).body(jsonResponse);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }
}