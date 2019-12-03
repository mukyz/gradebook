package com.softmetrix.model.DTO;

import java.util.List;

import com.softmetrix.model.TestQuestion;

public class TestForm {
	
	private String name;
	private Integer courseId;
	
	private List<TestQuestion> questions;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCourseId() {
		return courseId;
	}

	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}

	public List<TestQuestion> getQuestions() {
		return questions;
	}

	public void setQuestions(List<TestQuestion> questions) {
		this.questions = questions;
	}
	
}
