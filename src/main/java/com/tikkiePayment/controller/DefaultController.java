package com.tikkiepayment.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tikkie-payment")
public class DefaultController {

    @GetMapping("/welcome")
    public String home(@AuthenticationPrincipal OidcUser user) {
        return "Hello, Welcome "+ user.getFullName() +" to Tikkie Payment!";
    }

}
