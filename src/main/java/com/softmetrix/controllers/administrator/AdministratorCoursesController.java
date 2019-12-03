package com.softmetrix.controllers.administrator;

import com.softmetrix.helper.ResourceNotFoundException;
import com.softmetrix.model.Course;
import com.softmetrix.service.CourseService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("administrator/courses")
public class AdministratorCoursesController {
    @Autowired
    private CourseService courseService;
     /*
        todo error pages
    */
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleResourceNotFoundException() {
        return "meters/notfound";
    }
    
    @GetMapping("")
    public String getCourses(@PageableDefault(size = 10) Pageable pageable, Model model){          
        Page<Course> courses = courseService.getPage(pageable);        
        model.addAttribute("page", courses);    
        return "administrator/courses/list";        
    }
    
    @GetMapping("/new")
    public String createCourse(Course course, Model model){
        model.addAttribute("heading", "New Course");
        return "administrator/courses/form";
    }
    
    @PostMapping("/new")
    public String proccessCreateCourse(@Valid Course course, BindingResult result){
        if(result.hasErrors()) {
            return "administrator/courses/form";
        }        
        courseService.saveCourse(course);
        return "redirect:/administrator/courses";        
    }
    
    @GetMapping("/edit")
    public String editCourse(@RequestParam(name = "course_id",required = true) Integer courseId, Model model){
        Course course = courseService.findById(courseId);        
        if(course == null) throw new ResourceNotFoundException("Course with id:"+courseId+" does not exist!");
        
        model.addAttribute("heading", "Edit Course");
        model.addAttribute("course", course);
        return "administrator/courses/form";        
    }
    
    @GetMapping("/delete")
    String processDeleteCourse(@RequestParam("course_id") Integer id){
        courseService.deleteCourse(id); 
        return "redirect:/administrator/courses";
    }    
}