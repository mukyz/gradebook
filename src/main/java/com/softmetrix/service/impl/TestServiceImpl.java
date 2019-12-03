package com.softmetrix.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softmetrix.helper.TestResult;
import com.softmetrix.model.Course;
import com.softmetrix.model.SchoolClass;
import com.softmetrix.model.Student;
import com.softmetrix.model.Test;
import com.softmetrix.model.TestQuestion;
import com.softmetrix.model.TestQuestionOptions;
import com.softmetrix.model.TestResultId;
import com.softmetrix.model.TestResults;
import com.softmetrix.model.User;
import com.softmetrix.model.DTO.TestAnswersForm;
import com.softmetrix.model.DTO.TestForm;
import com.softmetrix.repository.TestRepository;
import com.softmetrix.repository.TestResultsRepository;
import com.softmetrix.service.CourseService;
import com.softmetrix.service.SchoolClassService;
import com.softmetrix.service.TestService;


@Service
public class TestServiceImpl implements TestService{
	@Autowired TestRepository testRepository;
	@Autowired TestResultsRepository testResultRepository;
	@Autowired CourseService courseService;
	@Autowired SchoolClassService sClassService;

	@Override
	public void saveTest(TestForm testForm, User user) {
		Course course = courseService.findById(testForm.getCourseId());
		
		Test newTest = new Test();
		newTest.setCourse(course);
		newTest.setName(testForm.getName());
		
		List<TestQuestion> questions = testForm.getQuestions();
		questions.forEach( question ->{
			List<TestQuestionOptions> options = question.getQuestionOptions();
			options.forEach(option -> {
				if(option.getIsCorrect() == null) option.setIsCorrect(Boolean.FALSE);
				if(option.getValue() == null) option.setValue(0);
			});			
		});
		
		newTest.setTestQuestions(questions);	

		Test test = testRepository.save(newTest);		
		
		SchoolClass sClass = user.getSchoolClass();
		if(sClass.getTests()!=null){
			sClass.getTests().add(test);
		} else {
			List<Test> tests = new ArrayList<Test>();
			tests.add(test);
			sClass.setTests(tests);
		}
		sClassService.save(sClass);
	}

	@Override
	public Test getTest(Integer id) {
		return testRepository.findById(id).orElse(null);
	}

	@Override
	public TestResult checkAnswers(Student student, Integer testId, TestAnswersForm answers) {
		Test test = testRepository.getOne(testId);
		
		TestResult result = new TestResult();
		Map<Integer, Integer> points = new HashMap<Integer, Integer>();
		Map<TestQuestion, Map<TestQuestionOptions, Boolean>> resultPerQuestion = new HashMap<TestQuestion, Map<TestQuestionOptions,Boolean>>();
		Integer totalPoints = getTotalPoints(test);
		Integer pointsWon = 0;
		
		for(int i = 0 ; i < test.getTestQuestions().size(); i++) {
			TestQuestion question = test.getTestQuestions().get(i);
			
			Map<Integer, Integer> correctAnswers = getCorrectAnswers(question);
			
			List<Integer> studentAnswers = answers.getMap().get(question.getId());
			
			resultPerQuestion.put(question, checkQuestion(question, studentAnswers));
			
			Integer questionPointsWon = 0;
			if(studentAnswers != null) {
				for(int j = 0; j < studentAnswers.size(); j++ ) {
					if(correctAnswers.containsKey(studentAnswers.get(j))) {
						questionPointsWon += correctAnswers.get(studentAnswers.get(j));
					} else {
						questionPointsWon = 0;
						break;
					}
				}
			}
			
			points.put(question.getId(), questionPointsWon);
			pointsWon += questionPointsWon;
		}
		
		result.setPoints(points);
		result.setTotalPoints(totalPoints);
		result.setPointsWon(pointsWon);
		result.setResult(resultPerQuestion);
		
		
		saveTestResult(student, test, result);
		
		
		
		return result;
	}
	
	private Map<TestQuestionOptions, Boolean> checkQuestion(TestQuestion question, List<Integer> answers){
		
		Map<TestQuestionOptions, Boolean> result = new HashMap<TestQuestionOptions, Boolean>();
		if(answers != null) {		
		List<TestQuestionOptions> options = question.getQuestionOptions();
			for(int i = 0 ; i < options.size(); i++) {
				if(answers.contains(options.get(i).getId())) {
					if(options.get(i).getIsCorrect()) {
						result.put(options.get(i), Boolean.TRUE);
					} else {
						result.put(options.get(i), Boolean.FALSE);
					}
				}
			}
		}
		
		return result;	
	}
	
	
	private Map<Integer, Integer> getCorrectAnswers(TestQuestion question){
		Map<Integer, Integer> answers = new HashMap<Integer, Integer>();
		
		List<TestQuestionOptions> options = question.getQuestionOptions();
		
		for (int i = 0; i < options.size(); i++) {
			if(options.get(i).getIsCorrect() == true) {
				answers.put(options.get(i).getId(), options.get(i).getValue());
			}
		}
		
		return answers;		
	}
	
	private Integer getTotalPoints(Test test) {
		List<TestQuestion> questions = test.getTestQuestions();
		
		Integer totalPoints = 0;
		for(int i = 0; i < questions.size(); i++) {
			List<TestQuestionOptions> options = questions.get(i).getQuestionOptions();
			
			for(int j = 0; j < options.size(); j++) {
				if(options.get(j).getValue()!= null)
					totalPoints += options.get(j).getValue();
				
			}
			
		}
		
		return totalPoints;
	}
	
	
	private void saveTestResult(Student student, Test test, TestResult result) {
		TestResultId resultId = new TestResultId(student.getId(), test.getId());
		
		TestResults persistResult = new TestResults();
		persistResult.setId(resultId);
		persistResult.setPointsWon(result.getPointsWon());
		persistResult.setTotalPoints(result.getTotalPoints());
		
		
		testResultRepository.save(persistResult);		
	}

	@Override
	public Map<Course, Map<Test, TestResults>> getTestsForStudent(Student student) {
		SchoolClass schoolClass = student.getSchoolClass();
		Map<Course, Map<Test, TestResults>> result = new HashMap<>();
		if(schoolClass != null) {
			List<Course> courses = schoolClass.getCourses();
			if(courses != null) {
				List<Test> tests = schoolClass.getTests();
				for(int i = 0; i < courses.size(); i++) {
					Map<Test, TestResults> testsForCourse = new HashMap<Test, TestResults>();
					
					for(int j = 0 ; j < tests.size(); j++) {
						if(tests.get(j).getCourse().equals(courses.get(i))) {
							TestResultId trId = new TestResultId(student.getId(), tests.get(j).getId());
							TestResults results = testResultRepository.findById(trId).orElse(null);
							testsForCourse.put(tests.get(j), results);
						}
					}
					result.put(courses.get(i), testsForCourse);
				}
				
				return result;
			} else return null;
		}

		return null;
	}

	@Override
	public Boolean canDoTest(Student student, Integer testId) {
		Map<Course, Map<Test, TestResults>> tests = getTestsForStudent(student);
		Test test = getTest(testId);

		Boolean alreadeyDone = Boolean.FALSE;
		for(Map.Entry<Course, Map<Test, TestResults>> entry: tests.entrySet()) {
			if(entry.getValue().containsKey(test)) {
				TestResults tResult = entry.getValue().get(test);
				if(tResult != null) alreadeyDone = Boolean.TRUE;
			}
		}
		
		return !alreadeyDone;
	}

	@Override
	public Map<Course, List<Test>> getTestsForSchoolClass(SchoolClass schoolClass) {
		if(schoolClass == null) return null;
		Map<Course, List<Test>> result = new HashMap<Course, List<Test>>();
		
		List<Course> courses = schoolClass.getCourses();
		if(courses != null) {
			List<Test> tests = schoolClass.getTests();
			if(tests != null) {
				
				for(int i = 0; i < courses.size(); i++) {
					List<Test> testsForCourse = new ArrayList<Test> ();
					for(int j = 0; j < tests.size(); j++) {
						if(tests.get(j).getCourse().equals(courses.get(i))) {
							testsForCourse.add(tests.get(j));
						}
					}
					result.put(courses.get(i), testsForCourse);
				}
				
				return result;
			}			
		}
		return null;
	}
}
