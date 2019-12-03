package com.softmetrix.controllers.parentTeacher;

import com.softmetrix.helper.BaseControllerParentTeacher;
import com.softmetrix.model.Role;
import com.softmetrix.model.Student;
import com.softmetrix.model.User;
import com.softmetrix.service.TimetableService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TimetableController extends BaseControllerParentTeacher {
    @Autowired TimetableService timetableService;

    @GetMapping("/timetable")
    public String getTimetable(User user, @RequestParam("child_id") Optional<Integer> childId, Model model){
        if(user.getRole().getRoleName().equals(Role.TEACHER)){
            model.addAttribute("timetable", timetableService.getTimetable(user.getSchoolClass()));
        } else if(user.getRole().getRoleName().equals(Role.PARENT)){
            if(childId.isPresent()){
                model.addAttribute("child_id", childId.get());                
                List<Student> children = user.getChildren();
                if(children != null){
                    children.forEach((child) -> {
                        if(child.getId().equals(childId.get())){
                            model.addAttribute("timetable", timetableService.getTimetable(child.getSchoolClass()));
                        }
                    });
                }                
            }
        }       
        return "timetable";
    }
}
