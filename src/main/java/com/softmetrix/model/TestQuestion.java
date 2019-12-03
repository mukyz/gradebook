package com.softmetrix.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "test_questions")
public class TestQuestion {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "question_text")
	private String questionText;
	
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "question_id")
	List<TestQuestionOptions> questionOptions;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getQuestionText() {
		return questionText;
	}

	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}

	public List<TestQuestionOptions> getQuestionOptions() {
		return questionOptions;
	}

	public void setQuestionOptions(List<TestQuestionOptions> questionOptions) {
		this.questionOptions = questionOptions;
	}
}
