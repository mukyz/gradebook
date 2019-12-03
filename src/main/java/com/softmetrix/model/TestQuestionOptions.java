package com.softmetrix.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "question_options")
public class TestQuestionOptions {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "option_text")
	private String text;
	 
	@Column(name = "value", columnDefinition = "integer default 0")
	private Integer value;
	
	@Column(name = "is_correct", columnDefinition = "tinyint default 0")
	private Boolean isCorrect;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public Boolean getIsCorrect() {
		return isCorrect;
	}

	public void setIsCorrect(Boolean isCorrect) {
		this.isCorrect = isCorrect;
	}
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof TestQuestionOptions) {
			if( ((TestQuestionOptions) obj).getId().equals(this.getId())) return true;
			else return false;
			
		} else return false;
	}
}
