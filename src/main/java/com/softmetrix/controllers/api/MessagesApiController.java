package com.softmetrix.controllers.api;

import com.google.gson.Gson;
import com.softmetrix.model.User;
import com.softmetrix.service.MessagesService;
import com.softmetrix.service.UserService;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/messages")
public class MessagesApiController {
    @Autowired UserService userService;
    @Autowired MessagesService messagesService;
    
    @ModelAttribute
    public User currentUser(Principal p) {
    	return userService.findByUsername(p.getName());
    }
    
    @GetMapping("/inbox")
    public String getInbox(User user, @RequestParam("last_id") Integer id){
        return new Gson().toJson(messagesService.getReceivedMessages(user, id));
    }
    
    @GetMapping("/count")
    public String getCount(User user){
        return new Gson().toJson(messagesService.getCount(user));
    }
}
