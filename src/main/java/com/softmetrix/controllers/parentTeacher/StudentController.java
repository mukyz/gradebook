package com.softmetrix.controllers.parentTeacher;

import com.softmetrix.helper.BaseControllerParentTeacher;
import com.softmetrix.helper.ForbidenException;
import com.softmetrix.helper.ResourceNotFoundException;
import com.softmetrix.model.Course;
import com.softmetrix.model.DTO.GradeFormDTO;
import com.softmetrix.model.DTO.StatusMessage;
import com.softmetrix.model.Grade;
import com.softmetrix.model.Role;
import com.softmetrix.model.Student;
import com.softmetrix.model.User;
import com.softmetrix.service.GradeService;
import com.softmetrix.service.StudentService;
import com.softmetrix.service.TestService;

import java.util.LinkedHashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class StudentController extends BaseControllerParentTeacher{
    @Autowired StudentService studentService;
    @Autowired GradeService gradeService;
    @Autowired TestService testService;

    @GetMapping("/students")
    public String getStudents(User user, Model model){
        if(user.getRole().getRoleName().equals(Role.PARENT)){
            model.addAttribute("title", "Children");
            model.addAttribute("students", user.getChildren());            
        }
        if(user.getRole().getRoleName().equals(Role.TEACHER)){
            model.addAttribute("title", "Students");
            if(user.getSchoolClass() != null){
                model.addAttribute("students", user.getSchoolClass().getStudents());
            }            
        }        
        return "studentList";
    }
    
    @GetMapping("/students/student/{id}")
    public String getStudent(User user, @PathVariable Integer id, Model model, GradeFormDTO gradeFormDTO){
        if(user.getRole().getRoleName().equals(Role.PARENT)  && !studentService.isChildOf(user, id))
            throw new ForbidenException("You are not the parent of this student!");        
        
        if(user.getRole().getRoleName().equals(Role.TEACHER) && !studentService.isInTeachersClass(user, id))
            throw new ForbidenException("You are not the teacher of this student!");        
        
        
        if((user.getRole().getRoleName().equals(Role.PARENT) || user.getRole().getRoleName().equals(Role.TEACHER)) && 
                (studentService.isChildOf(user, id) || studentService.isInTeachersClass(user, id)) ){

            Student student = studentService.findById(id);
            model.addAttribute("student", student);
            LinkedHashMap<Course, List<Grade>> gradeMap = new LinkedHashMap<>();
            LinkedHashMap<Course, Grade> finalGradeMap = new LinkedHashMap<>();
            if(student.getSchoolClass() != null){
                List<Course> courses = student.getSchoolClass().getCourses();
                if(courses != null){
                    for(int i = 0; i < courses.size(); i++){
                        gradeMap.put(courses.get(i), gradeService.getGrades(student, courses.get(i)));
                        finalGradeMap.put(courses.get(i), gradeService.getFinalGrade(student, courses.get(i)));
                    }
                    
                    model.addAttribute("gradeMap", gradeMap);
                    model.addAttribute("finalGradeMap", finalGradeMap);
                    model.addAttribute("tests", testService.getTestsForStudent(student));
                }
            }     
        } else {
            throw new ResourceNotFoundException("NOT FOUND!");
        }       
        return "student";
    }
    
    @PreAuthorize("hasAuthority('"+Role.TEACHER+"')")
    @PostMapping("/students/new_grade")
    public String newGrade(User user, GradeFormDTO gradeFormDTO, HttpServletRequest request, RedirectAttributes ra){
        String referer = request.getHeader("Referer");
        if(studentService.isInTeachersClass(user,gradeFormDTO.getStudentId())){
            gradeService.newGrade(gradeFormDTO); 
            ra.addFlashAttribute("modifiedCourseId", gradeFormDTO.getCourseId());  
            if(gradeFormDTO.isIsFinal()){
                ra.addFlashAttribute("message", new StatusMessage("info", "Final Grade " + gradeFormDTO.getGrade() + " has been succesefuly added."));
            } else {
                ra.addFlashAttribute("message", new StatusMessage("info", "Grade " + gradeFormDTO.getGrade() + " has been succesefuly added."));
            }   
        } else {
            throw new ForbidenException("Student has to be in your class to add a new grade!");
        }
        return "redirect:" + referer;
    } 
    
    @PreAuthorize("hasAuthority('"+Role.TEACHER+"')")
    @GetMapping("/students/delete_grade")
    public String newGrade(@RequestParam("id") Integer id, HttpServletRequest request){
        String referer = request.getHeader("Referer");
        gradeService.delete(id);
        return "redirect:" + referer;
    } 
    
}
