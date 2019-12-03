package com.softmetrix.controllers.administrator;

import com.softmetrix.helper.ResourceNotFoundException;
import com.softmetrix.model.DTO.StudentDTO;
import com.softmetrix.model.Role;
import com.softmetrix.model.Student;
import com.softmetrix.service.SchoolClassService;
import com.softmetrix.service.StudentService;
import com.softmetrix.service.UserService;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/administrator/students")
public class AdministratorStudentController {
    @Autowired private StudentService studentService;
    @Autowired private UserService userService;    
    @Autowired SchoolClassService schoolClassService;
    
    @GetMapping("")
    public String getStudents(Model model, @PageableDefault(size = 10, sort = "id" ) Pageable pageable){             
        model.addAttribute("page", studentService.getPage(pageable));
        List<Sort.Order> sortOrders = pageable.getSort().stream().collect(Collectors.toList());
        if (sortOrders.size() > 0) {
            Sort.Order order = sortOrders.get(0);
            model.addAttribute("sortProperty", order.getProperty());
            model.addAttribute("sortDesc", order.getDirection() == Sort.Direction.DESC);
        }
        return "administrator/students/list";
    }
    
    @GetMapping("/new")
    String createStudent (StudentDTO studentDTO, Model model){        
        model.addAttribute("parents", userService.getByRoleName(Role.PARENT));
        model.addAttribute("classes", schoolClassService.getAll());

        return "administrator/students/form";        
    }
    
    @PostMapping("/new")
    public String proccessCreateStudent(@Valid StudentDTO studentDTO, BindingResult result, Model model){        
        if(result.hasErrors()) {
            model.addAttribute("parents", userService.getByRoleName(Role.PARENT));
            model.addAttribute("classes", schoolClassService.getAll());
            return "administrator/students/form";
        }
        
        studentService.save(studentDTO);
        return "redirect:/administrator/students";        
    }
    
    @GetMapping("/edit")
    public String editStudent(@RequestParam("student_id") Integer studentId, Model model){
        Student student = studentService.findById(studentId);
        if(student == null) throw new ResourceNotFoundException("Student with id:"+ studentId+" does not exist!");
        StudentDTO studentDTO = new StudentDTO(student);
        model.addAttribute("studentDTO", studentDTO);
        model.addAttribute("parents", userService.getByRoleName(Role.PARENT));
        model.addAttribute("classes", schoolClassService.getAll());        
        return "administrator/students/form";
    }
    
    @GetMapping("/delete")
    public String deleteStudent(@RequestParam("student_id")Integer studentId){
        studentService.delete(studentId);
        return "redirect:/administrator/students";
    }
}