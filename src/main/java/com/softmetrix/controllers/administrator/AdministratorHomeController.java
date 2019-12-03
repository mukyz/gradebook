package com.softmetrix.controllers.administrator;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdministratorHomeController {
    @GetMapping("/administrator")
    public String getHome(){
        return "administrator/home";
    }
}
