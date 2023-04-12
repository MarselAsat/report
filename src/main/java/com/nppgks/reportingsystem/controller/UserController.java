package com.nppgks.reportingsystem.controller;

import com.nppgks.reportingsystem.db.common.entity.User;
import com.nppgks.reportingsystem.security.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PatchMapping ("/updatePassword")
    @ResponseBody
    public String changeUserPassword(@RequestBody Map<String, String> passwords) {
        User user = userService.findUserByUsername(
                SecurityContextHolder.getContext().getAuthentication().getName());

        if (!userService.checkIfValidPassword(user, passwords.get("currentPassword"))) {
            return "The current password is not valid";
        }
        userService.changeUserPassword(user, passwords.get("newPassword"));
        return "The password was changed successfully";
    }

    @GetMapping("/updatePassword")
    public String updatePasswordPage(){
        return "change-password";
    }
}
