package com.softmetrix.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "test_results")
public class TestResults {
	
	@EmbeddedId TestResultId id;
	
	@Column(name = "points_won")
	Integer pointsWon;
	
	@Column(name = "points_total")
	Integer totalPoints;
	public TestResultId getId() {
		return id;
	}
	public void setId(TestResultId id) {
		this.id = id;
	}
	public Integer getPointsWon() {
		return pointsWon;
	}
	public void setPointsWon(Integer pointsWon) {
		this.pointsWon = pointsWon;
	}
	public Integer getTotalPoints() {
		return totalPoints;
	}
	public void setTotalPoints(Integer totalPoints) {
		this.totalPoints = totalPoints;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(!(obj instanceof TestResults)) return false;
		TestResults that = (TestResults) obj;
		if(Objects.equals(getId(), that.getId())) return true;
		else return false;
	}
}


