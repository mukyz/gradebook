package com.softmetrix.controllers.parentTeacher;

import com.softmetrix.helper.BaseControllerParentTeacher;
import com.softmetrix.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AnnouncementsController extends BaseControllerParentTeacher{
    @Autowired AnnouncementService announcementService;

    @GetMapping("/announcements")
    public String getAll(Model model){
        model.addAttribute("announcements", announcementService.getAll());
        return "announcements";
    }    
}
