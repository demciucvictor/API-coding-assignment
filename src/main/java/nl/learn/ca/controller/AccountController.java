package nl.learn.ca.controller;

import nl.learn.ca.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/new-account/{customerId}")
    public ResponseEntity<Void> createNewAccount(@PathVariable Long customerId, @RequestParam Double initialCredit) {
        Boolean accountCreated = accountService.createNewAccount(customerId, initialCredit);
        if (accountCreated) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

}
