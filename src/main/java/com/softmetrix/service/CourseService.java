package com.softmetrix.service;

import com.softmetrix.model.Course;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CourseService {
    public List<Course> getAll();   
    public Page<Course> getPage(Pageable pageable);
    public Course findById(Integer id);
    public Course saveCourse(Course course);
    public void deleteCourse(Integer id);    
}
