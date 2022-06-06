package com.nppgks.reports.controller;

import com.nppgks.reports.security.User;
import com.nppgks.reports.security.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/updatePassword")
    @ResponseBody
    public String changeUserPassword(Locale locale,
                                              @RequestParam("password") String password,
                                              @RequestParam("oldpassword") String oldPassword) {
        User user = userService.findUserByUsername(
                SecurityContextHolder.getContext().getAuthentication().getName());

        if(user!=null){
            if (!userService.checkIfValidOldPassword(user, oldPassword)) {
                return "The old password is not valid";
            }
            userService.changeUserPassword(user, password);
            return "The password was changed successfully";
        }
        else{
            return "Log in to change the password";
        }

    }

    @GetMapping("/updatePassword")
    public String updatePasswordPage(){
        return "change-password";
    }
}
