package com.softmetrix.controllers.parentTeacher;

import com.softmetrix.helper.BaseControllerParentTeacher;
import com.softmetrix.model.DTO.OpenDoorDTO;
import com.softmetrix.model.Role;
import com.softmetrix.model.User;
import com.softmetrix.service.MessagesService;
import com.softmetrix.service.OpenDoorService;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class OpenDoorController extends BaseControllerParentTeacher{
    @Autowired OpenDoorService openDoorService;
    @Autowired MessagesService messagesService;
    
    @GetMapping("/open_door")
    public String getOpenDoor(User user, Model model, OpenDoorDTO openDoorDTO){
        model.addAttribute("isTeacher", user.getRole().getRoleName().equals(Role.TEACHER));
        model.addAttribute("isParent", user.getRole().getRoleName().equals(Role.PARENT));
        
        model.addAttribute("receivers", messagesService.getAvailableReceievers(user));
        model.addAttribute("requests", openDoorService.getOpenDoorRequest(user));
        
        return "open-door";
    }
    
    @GetMapping("/open_door/{id}")
    public String getOpenDoor(@PathVariable Integer id, @RequestParam("status") String status){
        openDoorService.setStatus(id, status);
        
        return "redirect:/open_door";
    }
    
    @PostMapping("/open_door/new")
    public String newOpenDoorReq(OpenDoorDTO openDoorDTO, HttpServletRequest request){
        String referer = request.getHeader("Referer");
        openDoorService.save(openDoorDTO);
        return "redirect:"+referer;
    }
}