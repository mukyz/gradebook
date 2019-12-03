package com.softmetrix.service.impl;

import com.softmetrix.model.Course;
import com.softmetrix.model.SchoolClass;
import com.softmetrix.model.Student;
import com.softmetrix.repository.GradeRepository;
import com.softmetrix.service.SchoolClassService;
import com.softmetrix.service.StatisticsService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatisticsServiceImpl implements StatisticsService{
    @Autowired GradeRepository gradeRepository;
    @Autowired SchoolClassService schoolClassService;

    @Override
    public List<HashMap<Object, Object>> getStasByCourse(Course course) {
        List<HashMap<Object, Object>> stat = new ArrayList<>();
        Integer studentsWithGrade = 0;
        for(int i = 1; i <= 5; i++){
            HashMap<Object, Object> map = new HashMap<>();
            map.put("grade", i);           
            Integer count = gradeRepository.getStatByCourseAndGrade(course, i);
            studentsWithGrade+=count;
            map.put("count", count);
            stat.add(map);
        }
        
        List<SchoolClass> classes = schoolClassService.getAll();
        Integer totalStudents = 0;
        
        for(int i = 0; i < classes.size(); i++ ) {
        	if(classes.get(i).getCourses() != null && classes.get(i).getCourses().contains(course)){        		
        		totalStudents += classes.get(i).getStudents() != null? classes.get(i).getStudents().size() : 0;
        	}
        }
        
        HashMap<Object, Object> map = new HashMap<>();
        map.put("grade", "No Final Grade");
        map.put("count", totalStudents - studentsWithGrade);    
        
        stat.add(map);
        
        return stat;
    }

    @Override
    public List<HashMap<Object, Object>> getStasBySchoolClass(SchoolClass schoolClass) {
        List<Student> students = schoolClass.getStudents();
        if(students == null) return null;
        
        List<Course> courses = schoolClass.getCourses();
        if(courses == null) return null;
        
        List<HashMap<Object, Object>> stat = new ArrayList<>();
        
        for(int i = 0; i < courses.size(); i++){
            HashMap<Object, Object> map = new HashMap<>();
            map.put("course", courses.get(i).getName());
            Integer studentsWithGrade = 0;
            for(Integer grade = 1; grade <= 5; grade++){
            	Integer studentsWithThisGrade = 0;
                if(students.size() != 0){
                	studentsWithThisGrade = gradeRepository.getStatBySchoolClass(courses.get(i), grade, students);
                } 
                map.put(grade, studentsWithThisGrade);
                studentsWithGrade += studentsWithThisGrade;
            }
            map.put("No Final Grade", students.size() - studentsWithGrade);
            
            stat.add(map);
        }        
        return stat;      
    }
    

    
    
}
