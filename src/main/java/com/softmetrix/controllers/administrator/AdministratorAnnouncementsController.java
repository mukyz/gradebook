package com.softmetrix.controllers.administrator;

import com.softmetrix.model.Announcement;
import com.softmetrix.model.DTO.AnnouncementDTO;
import com.softmetrix.service.AnnouncementService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/administrator/announcements")
public class AdministratorAnnouncementsController {
    
    @Autowired
    private AnnouncementService announcementService;
    
    @GetMapping("")
    public String getAnnouncements(Model model){        
        List<Announcement> announcements = announcementService.getAll();
        model.addAttribute("announcements", announcements);
        
        return "administrator/announcements/list";
    }
    
    @GetMapping("/new")
    public String createNewAnnouncement(AnnouncementDTO announcementDTO){
        return "administrator/announcements/form";
    }
    
    @PostMapping("/new")
    public String proccessCreateNewAnnnouncement(@Valid AnnouncementDTO announcementDTO, BindingResult result){        
        if(result.hasErrors()){
            return "administrator/announcements/form";
        }        
        announcementService.save(announcementDTO);        
        return "redirect:/administrator/announcements";
    }
    
    @GetMapping("/delete")
    public String deleteAnnouncement(@RequestParam("id") Integer id){        
        announcementService.delete(id);
        return "redirect:/administrator/announcements";
    }    
}
