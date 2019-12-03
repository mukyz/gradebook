package com.softmetrix.model;
import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class TestResultId implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8955975153003672870L;

	@Column(name = "student_id")
	private Integer studentId;
	
	@Column(name="test_id")
	private Integer testId;
	
	public TestResultId() {
	}
	
	public TestResultId(Integer studentId, Integer testId) {
		this.studentId 	= studentId;
		this.testId 	= testId;
	}
	
	public Integer getStudentId() {
		return studentId;
	}

	public Integer getTestId() {
		return testId;
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TestResultId)) return false;
        TestResultId that = (TestResultId) o;
        return Objects.equals(getStudentId(), that.getStudentId()) &&
                Objects.equals(getTestId(), that.getTestId());
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(getStudentId(), getTestId());
	}	
}