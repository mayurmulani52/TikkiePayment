package com.tikkiepayment.controller;

import java.security.Principal;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tikkie-payment")
public class DefaultController {

	@PreAuthorize("#oauth2.hasScope('profile')")
    @GetMapping("/welcome")
    public String home(Principal principal) {
        return "Hello, Welcome to Tikkie Payment!"+ principal.getName();
    }

}
