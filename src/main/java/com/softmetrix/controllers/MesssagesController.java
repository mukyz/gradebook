package com.softmetrix.controllers;

import com.softmetrix.helper.ForbidenException;
import com.softmetrix.model.DTO.MessageDTO;
import com.softmetrix.model.DTO.NewMessageForm;
import com.softmetrix.model.Message;
import com.softmetrix.model.User;
import com.softmetrix.service.MessagesService;
import com.softmetrix.service.UserService;
import java.security.Principal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MesssagesController {
    @Autowired private UserService userService;    
    @Autowired private MessagesService messagesService;
    
    @ModelAttribute
    public User getCurrentUser (Principal p){
        return userService.findByUsername(p.getName());
    }
    
    @GetMapping("/messages")
    public String getMessagesView(User user, Model model){
        List<MessageDTO> messages = messagesService.getReceivedMessages(user, 0);
        Integer lastMessageId = 0;
        
        for(int i = 0; i < messages.size(); i++){
            if(messages.get(i).getId() > lastMessageId)
                lastMessageId = messages.get(i).getId();
        }
        
        model.addAttribute("lastMessageId", lastMessageId);
        model.addAttribute("messages", messages);
        
        return "messages/inbox";
    }
    
    @GetMapping("/messages/outbox")
    public String getOutbox(User user, Model model){
        List<MessageDTO> messages = messagesService.getSentMessages(user);
        model.addAttribute("messages", messages);
        
        return "messages/outbox";        
    }
    
    @GetMapping("/messages/{msg_id}")
    public String getMessage(User user, @PathVariable("msg_id") Integer msg_id, Model model){
        Message msg = messagesService.getMessageById(msg_id).orElse(null);
        
        if(!messagesService.canRead(user, msg_id)) throw new ForbidenException("You can not access messages that you are neither receiver nor sender");        
        
        if(msg.getIsRead() == false) messagesService.markRead(msg); 
        
        Boolean canReply = msg.getSender().equals(user)? Boolean.FALSE: Boolean.TRUE;
        model.addAttribute("canReply", canReply);
        model.addAttribute("message", msg);
        
        return "messages/message";        
    }
    
    @GetMapping("/messages/new_message")
    public String newMessage(NewMessageForm newMessageForm, Model model, User user){               
        model.addAttribute("receivers", messagesService.getAvailableReceievers(user));
        return "messages/newMessage";
    }
    
    @PostMapping("/messages/new_message")
    public String proccessNewMessage(NewMessageForm newMessageForm, User user){
        messagesService.sendMessage(user, newMessageForm);
        return "redirect:/messages";
    }
}
