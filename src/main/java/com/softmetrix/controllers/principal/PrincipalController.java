package com.softmetrix.controllers.principal;

import com.softmetrix.model.Course;
import com.softmetrix.model.SchoolClass;
import com.softmetrix.service.CourseService;
import com.softmetrix.service.SchoolClassService;
import com.softmetrix.service.StatisticsService;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PrincipalController {
    @Autowired CourseService 		courseService;
    @Autowired StatisticsService 	statisticsService;
    @Autowired SchoolClassService 	schoolClassService;
    
    @GetMapping("/principal")
    public String getHome(){
        return "principal/home";
    }
    
    @GetMapping("/principal/global")
    public String getGlobal(Model model, @RequestParam("course_id") Optional<Integer> id){
        List<Course> courses = courseService.getAll();
        model.addAttribute("courses", courses);
        
        if(id.isPresent()){
            Course course = courseService.findById(id.get());
            model.addAttribute("course", course);
            List<HashMap<Object, Object>> data = statisticsService.getStasByCourse(course);
            model.addAttribute("data",data);            
        } else {
        	model.addAttribute("course", courses.get(0));
            List<HashMap<Object, Object>> data = statisticsService.getStasByCourse(courses.get(0));
            model.addAttribute("data",data);   
        }
        
        return "principal/global";
    }
    
    @GetMapping("/principal/class")
    public String getSchoolClass(Model model, @RequestParam("class_id") Optional<Integer> id){
        List<SchoolClass> classes = schoolClassService.getAll();
        model.addAttribute("classes", classes);
        
        if(id.isPresent()){
            SchoolClass schoolClass = schoolClassService.findById(id.get());
            model.addAttribute("schoolClass", schoolClass);
            List<HashMap<Object, Object>> data = statisticsService.getStasBySchoolClass(schoolClass);
            model.addAttribute("data",data);            
        } else {
        	 model.addAttribute("schoolClass", classes.get(0));
             List<HashMap<Object, Object>> data = statisticsService.getStasBySchoolClass(classes.get(0));
             model.addAttribute("data",data);
        }
        
        return "principal/class";
    }
}
