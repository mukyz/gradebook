package com.softmetrix.service;

import com.softmetrix.model.Course;
import com.softmetrix.model.DTO.GradeFormDTO;
import com.softmetrix.model.Grade;
import com.softmetrix.model.Student;
import java.util.List;

public interface GradeService {
    public void newGrade(GradeFormDTO gradeForm);
    
    public List<Grade> getGrades(Student student, Course course);
    public Grade getFinalGrade(Student student, Course course);
    public Double getAverage(Student student, Course course);
    public void delete(Integer id);
}