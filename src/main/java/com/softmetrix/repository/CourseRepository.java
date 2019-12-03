package com.softmetrix.repository;

import com.softmetrix.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Integer>{
    
}
