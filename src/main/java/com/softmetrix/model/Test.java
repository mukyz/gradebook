package com.softmetrix.model;

import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "tests")
public class Test {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "name")
	private String name;
	
	@ManyToOne
	@JoinColumn(name = "course_id")
	private Course course;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "test_id")
	List<TestQuestion> testQuestions;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public List<TestQuestion> getTestQuestions() {
		return testQuestions;
	}

	public void setTestQuestions(List<TestQuestion> testQuestions) {
		this.testQuestions = testQuestions;
	}	
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(obj instanceof Test) {
			Test that = (Test) obj;
			if(Objects.equals(getId(), that.getId())) return true;
			else return false;
		} else return false;
	}
}
