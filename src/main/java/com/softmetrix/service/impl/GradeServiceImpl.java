package com.softmetrix.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softmetrix.model.Course;
import com.softmetrix.model.Grade;
import com.softmetrix.model.Student;
import com.softmetrix.model.DTO.GradeFormDTO;
import com.softmetrix.repository.CourseRepository;
import com.softmetrix.repository.GradeRepository;
import com.softmetrix.repository.StudentRepository;
import com.softmetrix.service.GradeService;

@Service
public class GradeServiceImpl implements GradeService{
    @Autowired StudentRepository studentRepository;    
    @Autowired CourseRepository courseRepository;    
    @Autowired GradeRepository gradeRepository;
    
    @Override
    public void newGrade(GradeFormDTO gradeForm) {
        Student student = studentRepository.findById(gradeForm.getStudentId()).orElse(null);    
    
        if (student==null) {
            return;
        } 
        Course course = courseRepository.findById(gradeForm.getCourseId()).orElse(null);
        if (course==null) {
            return;
        }
        
        Grade newGrade = null;
        
        if(gradeForm.isIsFinal()){
            newGrade = getFinalGrade(student, course);
        }
        
        if(newGrade == null) newGrade = new Grade();
          
        newGrade.setStudent(student);
        newGrade.setCourse(course);
        newGrade.setGrade(gradeForm.getGrade());
        newGrade.setNote(gradeForm.getNote());
        newGrade.setIsFinalGrade(gradeForm.isIsFinal());

        gradeRepository.save(newGrade);
    }

    @Override
    public List<Grade> getGrades(Student student, Course course) {
        return gradeRepository.findByStudentAndCourseAndIsFinalGrade(student, course, Boolean.FALSE);
    }

    @Override
    public Grade getFinalGrade(Student student, Course course) {
        List<Grade> finalGrade = gradeRepository.findByStudentAndCourseAndIsFinalGrade(student, course, Boolean.TRUE);
        if(finalGrade == null || finalGrade.size() == 0) return null;
        else return finalGrade.get(0);
    }

    @Override
    public Double getAverage(Student student, Course course) {
        List<Grade> grades = gradeRepository.findByStudentAndCourseAndIsFinalGrade(student, course, Boolean.FALSE);
        if(grades == null) return null;
        
        Double sum = 0.0;
        for(int i = 0; i < grades.size(); i++){
            sum += grades.get(i).getGrade();
        }
        
        Double avg = sum / grades.size();
        
        return avg;
    }

	@Override
	public void delete(Integer id) {
		gradeRepository.deleteById(id);
		
	}
    
}
