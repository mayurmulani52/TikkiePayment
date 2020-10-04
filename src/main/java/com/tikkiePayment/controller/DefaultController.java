package com.tikkiepayment.controller;

import java.security.Principal;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DefaultController {

	@GetMapping("/")
    public String welcome(){
        return "Hello, Welcome to Tikkie Payment Application!";
    }
	
	@PreAuthorize("#oauth2.hasScope('profile')")
    @GetMapping("/protected/")
    public String helloWorldProtected(Principal principal) {
        return "Hello VIP " + principal.getName();
    }
	
}
