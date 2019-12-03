package com.softmetrix.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.softmetrix.model.Test;

public interface TestRepository extends JpaRepository<Test, Integer>{

}
