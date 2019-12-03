package com.softmetrix.helper;

import java.util.Map;

import com.softmetrix.model.TestQuestion;
import com.softmetrix.model.TestQuestionOptions;

public class TestResult {
	private Map<Integer, Integer> points;
	private Map<TestQuestion, Map<TestQuestionOptions, Boolean>> result;
	private Integer totalPoints;
	private Integer pointsWon;
	public Map<Integer, Integer> getPoints() {
		return points;
	}
	public void setPoints(Map<Integer, Integer> points) {
		this.points = points;
	}
	
	
	public Map<TestQuestion, Map<TestQuestionOptions, Boolean>> getResult() {
		return result;
	}
	
	public void setResult(Map<TestQuestion, Map<TestQuestionOptions, Boolean>> result) {
		this.result = result;
	}
	
	public Integer getTotalPoints() {
		return totalPoints;
	}
	
	public void setTotalPoints(Integer totalPoints) {
		this.totalPoints = totalPoints;
	}
	
	public Integer getPointsWon() {
		return pointsWon;
	}
	
	public void setPointsWon(Integer pointsWon) {
		this.pointsWon = pointsWon;
	}

}
