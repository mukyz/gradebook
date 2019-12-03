package com.softmetrix.repository;

import org.springframework.data.repository.CrudRepository;

import com.softmetrix.model.TestResultId;
import com.softmetrix.model.TestResults;

public interface TestResultsRepository extends CrudRepository<TestResults, TestResultId> {

}
