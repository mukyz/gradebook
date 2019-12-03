package com.softmetrix.helper;

import com.softmetrix.model.Announcement;
import com.softmetrix.model.OpenDoor;
import com.softmetrix.model.User;
import com.softmetrix.service.AnnouncementService;
import com.softmetrix.service.OpenDoorService;
import com.softmetrix.service.UserService;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;

public class BaseControllerParentTeacher {
    @Autowired UserService userService;
    @Autowired AnnouncementService announcementService;
    @Autowired OpenDoorService openDoorService;
    
    @ModelAttribute
    public User currentUser(Principal p){
        return userService.findByUsername(p.getName());
    }
    
    @ModelAttribute
    public Announcement latestAnnouncement(){
        return announcementService.getLatestAnnouncement();
    }
    
    @ModelAttribute
    public OpenDoor nextApointment(Principal p){
        return openDoorService.getNextApointment(userService.findByUsername(p.getName()));
    }  
}
