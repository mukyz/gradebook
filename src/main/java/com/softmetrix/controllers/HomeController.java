package com.softmetrix.controllers;

import com.softmetrix.helper.BaseControllerParentTeacher;
import com.softmetrix.model.Role;
import com.softmetrix.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController extends BaseControllerParentTeacher{
    @GetMapping("/")
    public String redirectBasedOnRole(User user){        
        switch(user.getRole().getRoleName()){
            case Role.ADMINISTRATOR: return "redirect:/administrator";
            case Role.PRINCIPAL: return "redirect:/principal";
            case Role.PARENT: case Role.TEACHER:return "parent-teacher-home";
            default: return "redirect: /login";            
        }
    }
}
