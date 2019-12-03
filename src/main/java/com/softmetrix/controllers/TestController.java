package com.softmetrix.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.softmetrix.helper.BaseControllerParentTeacher;
import com.softmetrix.helper.ForbidenException;
import com.softmetrix.helper.TestResult;
import com.softmetrix.model.Role;
import com.softmetrix.model.Student;
import com.softmetrix.model.User;
import com.softmetrix.model.DTO.TestAnswersForm;
import com.softmetrix.model.DTO.TestForm;
import com.softmetrix.service.CourseService;
import com.softmetrix.service.StudentService;
import com.softmetrix.service.TestService;

@Controller
public class TestController extends BaseControllerParentTeacher{
	@Autowired CourseService courseService;
	@Autowired TestService testService;
	@Autowired StudentService studentService;
	
	@GetMapping("tests")
	@PreAuthorize("hasAuthority('"+Role.TEACHER+"')")
	public String getTests(User user, Model model) {
		model.addAttribute("tests", testService.getTestsForSchoolClass(user.getSchoolClass()));
		return "test/list";
	}
	
	@GetMapping("tests/{testId}")
	public String getTest(@PathVariable Integer testId, @RequestParam("student_id") Integer studentId, Model model) {
		Student student = studentService.findById(studentId);
		if(testService.canDoTest(student, testId)) {
			model.addAttribute("student", student );
			model.addAttribute("answers", new TestAnswersForm() );
			model.addAttribute("test", testService.getTest(testId));
			
			return "test/test";
		} else {
			throw new ForbidenException("You can not do the same test twice!");
		}
	}
	
	@PostMapping("tests/{testId}")
	public String submitTest(@PathVariable Integer testId, @RequestParam("student_id") Integer studentId,  Model model, TestAnswersForm answers) {
		Student student = studentService.findById(studentId);
		TestResult result = testService.checkAnswers(student, testId, answers);
		model.addAttribute("result", result);
		model.addAttribute("test", testService.getTest(testId));		

		return "test/result";
	}
		
	@GetMapping("tests/new")
	public String getNewTest(Model model, User user, @RequestParam("course_id") Integer courseId) {
		TestForm  testForm= new TestForm();
		testForm.setCourseId(courseId);
		model.addAttribute("testForm", testForm);
		return "test/form";
	}
	
	@PostMapping("tests/new")
	public String postNewTest(TestForm testForm, User user) {
		testService.saveTest(testForm, user);
		return "redirect:/tests";
	}
	
	
}
