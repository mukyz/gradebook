package com.softmetrix.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.softmetrix.model.Course;
import com.softmetrix.repository.CourseRepository;
import com.softmetrix.service.CourseService;

@Service
public class CourseServiceImpl implements CourseService{
    
    @Autowired
    private CourseRepository courseRepository;

    @Override
    public Course saveCourse(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public Page<Course> getPage(Pageable pageable) {
        return courseRepository.findAll(pageable);
    }

    @Override
    public void deleteCourse(Integer id) {
        courseRepository.deleteById(id);
    }

    @Override
    public List<Course> getAll() {
        return courseRepository.findAll();
    }

    @Override
    public Course findById(Integer id) {
        return courseRepository.findById(id).orElse(null);
    }
}