package com.softmetrix.service;

import java.util.List;
import java.util.Map;

import com.softmetrix.helper.TestResult;
import com.softmetrix.model.Course;
import com.softmetrix.model.SchoolClass;
import com.softmetrix.model.Student;
import com.softmetrix.model.Test;
import com.softmetrix.model.TestResults;
import com.softmetrix.model.User;
import com.softmetrix.model.DTO.TestAnswersForm;
import com.softmetrix.model.DTO.TestForm;

public interface TestService {
	public void saveTest(TestForm testForm, User user);
	public TestResult checkAnswers( Student student, Integer testId, TestAnswersForm answers);
	public Test getTest(Integer id);
	
	public Boolean canDoTest(Student student, Integer testId);
	
	public Map<Course, Map<Test, TestResults>> getTestsForStudent(Student student);

	public Map<Course, List<Test>> getTestsForSchoolClass(SchoolClass schoolClass);
}
