package com.softmetrix.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.softmetrix.model.Course;
import com.softmetrix.model.SchoolClass;
import com.softmetrix.model.Student;
import com.softmetrix.model.User;
import com.softmetrix.model.DTO.SchoolClassForm;
import com.softmetrix.repository.CourseRepository;
import com.softmetrix.repository.SchoolClassRepository;
import com.softmetrix.repository.StudentRepository;
import com.softmetrix.repository.UserRepository;
import com.softmetrix.service.SchoolClassService;

@Service
public class SchoolClassServiceImpl implements SchoolClassService{
    @Autowired
    private SchoolClassRepository schoolClassRepository;
    
    @Autowired 
    private UserRepository userRepository;
    
    @Autowired
    private StudentRepository studentRepository;
    
    @Autowired
    private CourseRepository courseRepository;

    @Override
    public Page<SchoolClass> getPage(Pageable pageable) {
        return schoolClassRepository.findAll(pageable);
    }

    @Override
    public void delete(Integer id) {
        schoolClassRepository.deleteById(id);
    }

    @Override
    public SchoolClass save(SchoolClass schoolClass) {
        return schoolClassRepository.save(schoolClass);
    }

    public SchoolClass save(SchoolClassForm schoolClassDTO) {
        SchoolClass schoolClass = new SchoolClass();
        
        if(schoolClassDTO.getId() != null){
            schoolClass = schoolClassRepository.findById(schoolClassDTO.getId()).orElse(new SchoolClass());
        }
        
        if(schoolClassDTO.getName()!=null){
            schoolClass.setName(schoolClassDTO.getName());
        }
        
        if(schoolClassDTO.getTeacherId()!= null){
            User teacher = userRepository.findById(schoolClassDTO.getTeacherId()).orElse(null);
            schoolClass.setTeacher(teacher);
        }      
        
        if(schoolClassDTO.getStudentIds()!=null){
            schoolClass = schoolClassRepository.save(schoolClass);
            
            List<Student> students = studentRepository.findAllById(schoolClassDTO.getStudentIds());  
            schoolClass.setStudents(students);
            for(Student student: students){                
                student.setSchoolClass(schoolClass);
            }
            studentRepository.saveAll(students);
        }
        
        if(schoolClassDTO.getCoursesIds()!=null){
            schoolClass = schoolClassRepository.save(schoolClass);
            
            List<Course> coursess = courseRepository.findAllById(schoolClassDTO.getCoursesIds());  
            schoolClass.setCourses(coursess);
        }

        return schoolClassRepository.save(schoolClass);
    }    

    @Override
    public List<SchoolClass> getAll() {
        return schoolClassRepository.findAll();
    }

    @Override
    public SchoolClass findById(Integer id) {
        return schoolClassRepository.findById(id).orElse(null);
    }
}
