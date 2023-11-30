package com.nppgks.reportingsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginErrorPage(@RequestParam(required = false) Boolean error, ModelMap modelMap) {
        if (error != null && error) {
            modelMap.put("error", true);
        }
        return "login";
    }
}
