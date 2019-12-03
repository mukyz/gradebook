package com.softmetrix.controllers.administrator;

import com.softmetrix.model.Course;
import com.softmetrix.model.DTO.SchoolClassForm;
import com.softmetrix.model.DTO.TimetableForm;
import com.softmetrix.model.SchoolClass;
import com.softmetrix.model.Student;
import com.softmetrix.model.User;
import com.softmetrix.service.CourseService;
import com.softmetrix.service.SchoolClassService;
import com.softmetrix.service.StudentService;
import com.softmetrix.service.TimetableService;
import com.softmetrix.service.UserService;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("administrator/classes")
public class AdministratorSchoolClassesController {    
    @Autowired private SchoolClassService schoolClassService;
    @Autowired private TimetableService timetableService;    
    @Autowired private CourseService courseService;    
    @Autowired private StudentService studentService;    
    @Autowired private UserService userService;      
    
    @GetMapping("")
    public String getClasses(Model model, @PageableDefault(size = 10, sort = "id") Pageable pageable){
        Page<SchoolClass> classes = schoolClassService.getPage(pageable);       
        model.addAttribute("page", classes);    
        
        List<Sort.Order> sortOrders = pageable.getSort().stream().collect(Collectors.toList());
        if (sortOrders.size() > 0) {
            Sort.Order order = sortOrders.get(0);
            model.addAttribute("sortProperty", order.getProperty());
            model.addAttribute("sortDesc", order.getDirection() == Sort.Direction.DESC);
        }
        return "administrator/schoolClass/list";        
    }
    

    @GetMapping("/new")
    public String createSchoolClass(SchoolClassForm schoolClassForm, Model model){
        model.addAttribute("students", studentService.getStudentsWithoutClass());
        model.addAttribute("teachers", userService.findUnassignedTeachers());
        model.addAttribute("courses", courseService.getAll());
        return "administrator/schoolClass/form";
    }
    
    @GetMapping("/edit")
    public String editSchoolClass(@RequestParam("class_id") Integer id, Model model){
        SchoolClass schoolClass = schoolClassService.findById(id);
        SchoolClassForm schoolClassForm = new SchoolClassForm(schoolClass);
        model.addAttribute("schoolClassForm", schoolClassForm);
        
        List<Student> students = studentService.getStudentsWithoutClass();
        if(schoolClass.getStudents() != null) students.addAll(schoolClass.getStudents());      
        model.addAttribute("students", students);
        
        List<User> teachers = userService.findUnassignedTeachers();
        if(schoolClass.getTeacher() != null) teachers.add(schoolClass.getTeacher());        
        model.addAttribute("teachers", teachers);
        
        model.addAttribute("courses", courseService.getAll());
        
        return "administrator/schoolClass/form";
    }
    
    @PostMapping("/new")
    public String proccessCreateSchoolClass(@Valid SchoolClassForm schoolClassForm, BindingResult result, Model model){
        if(result.hasErrors()) {
            if(schoolClassForm.getId() == null){
                model.addAttribute("students", studentService.getStudentsWithoutClass());
                model.addAttribute("teachers", userService.findUnassignedTeachers());                
            } else {
                SchoolClass schoolClass = schoolClassService.findById(schoolClassForm.getId());
                
                List<Student> students = studentService.getStudentsWithoutClass();
                if(schoolClass.getStudents() != null) students.addAll(schoolClass.getStudents());        
                model.addAttribute("students", students);
        
                List<User> teachers = userService.findUnassignedTeachers();
                if(schoolClass.getTeacher() != null) teachers.add(schoolClass.getTeacher()); 
                
                model.addAttribute("teachers", teachers);
            }            
            model.addAttribute("courses", courseService.getAll());
            return "administrator/schoolClass/form";
        }
        
        schoolClassService.save(schoolClassForm);
        return "redirect:/administrator/classes";        
    }
    
    @GetMapping("/timetable")
    String createTimetable(@RequestParam("class_id") Integer id, Model model){
        
        //TODO check if schoolClass with id exists
        
        List<List<String>> timetable = timetableService.getTimetable(schoolClassService.findById(id));
        
        model.addAttribute("classId", id);
        model.addAttribute("timetable",timetable);        

        return "administrator/schoolClass/timetable";
    }
    
    @GetMapping("/timetable/edit")
    String viewTimetable(@RequestParam("class_id") Integer id, Model model){        
        SchoolClass schoolClass = schoolClassService.findById(id);
        if(schoolClass== null){
            //TODO somewhere to redirect if passed integer is invalid
        }
        
        model.addAttribute("classId", id);
        model.addAttribute("timetableForm", timetableService.getTimetableForm(id).orElse(new TimetableForm()));
        
        List<Course> classCourses = schoolClass.getCourses();
        HashMap<Integer, String> courses = new HashMap<>();        
        for(Course course: classCourses){
            courses.put(course.getId(), course.getName());
        }        
        model.addAttribute("courses", courses);
        
        return "administrator/schoolClass/timetableForm";
    }
    
    @PostMapping("/timetable/edit")
    String createTimetable(@RequestParam("class_id") Integer id, @ModelAttribute("timetableForm") TimetableForm timetableForm, Model model){        
        timetableService.save(id, timetableForm);
        return "redirect:"+"/administrator/classes/timetable?class_id="+id;
    }
    
    @GetMapping("/delete")
    public String deleteClass(@RequestParam("class_id") Integer classId){
        schoolClassService.delete(classId);
        return "redirect:/administrator/classes";
    }
}
