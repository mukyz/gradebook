package com.softmetrix.service.impl;

import com.softmetrix.model.DTO.StudentDTO;
import com.softmetrix.model.Student;
import com.softmetrix.model.StudentAvatar;
import com.softmetrix.model.User;
import com.softmetrix.repository.SchoolClassRepository;
import com.softmetrix.repository.StudentRepository;
import com.softmetrix.repository.UserRepository;
import com.softmetrix.service.StudentService;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.rowset.serial.SerialBlob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SrudentServiceImpl implements StudentService{    
    @Autowired private StudentRepository studentRepository;    
    @Autowired private UserRepository userRepository;    
    @Autowired private SchoolClassRepository schoolClassRepository;

    @Override
    public Page<Student> getPage(Pageable pageable) {
        return studentRepository.findAll(pageable);
    }

    @Override
    public Student save(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Student save(StudentDTO studentDTO) {
        Student student = new Student();
        if(studentDTO.getId() != null){
            student = studentRepository.findById(studentDTO.getId()).orElse(new Student());
        }
        
        if(studentDTO.getFirstName()!= null){
            student.setFirstName(studentDTO.getFirstName());
        }
        
        if(studentDTO.getLastName()!= null){
            student.setLastName(studentDTO.getLastName());
        }       
        
        student.setDateOfBirth(studentDTO.getDateOfBirt());
        student.setPlaceOfBirth(studentDTO.getPlaceOfBirth());
        student.setAddress(studentDTO.getAddress());
        
        student.setParent(userRepository.findById(studentDTO.getParentId()).orElse(null));
        student.setSchoolClass(schoolClassRepository.findById(studentDTO.getClassId()).orElse(null));
        
        if(studentDTO.getAvatarFile().getSize() !=0){
            student = studentRepository.save(student);
            StudentAvatar avatar = new StudentAvatar();
            if(student.getAvatar()!= null) avatar = student.getAvatar();
            
            avatar.setName(studentDTO.getAvatarFile().getName());
            avatar.setType(studentDTO.getAvatarFile().getContentType());
            try {
                avatar.setImage(new SerialBlob(studentDTO.getAvatarFile().getBytes()));
            } catch (IOException ex) {
                Logger.getLogger(SrudentServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(SrudentServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            student = studentRepository.save(student);
            avatar.setStudent(student);
            student.setAvatar(avatar);
        }

        return studentRepository.save(student);
    }

    @Override
    public List<Student> getStudentsWithoutClass() {        
        return studentRepository.findBySchoolClassIsNull();
    }    

    @Override
    public void delete(Integer id) {
        studentRepository.deleteById(id);
    }

    @Override
    public Student findById(Integer id) {
        return studentRepository.findById(id).orElse(null);
    }

    @Override
    public Boolean isChildOf(User parent, Integer studentId) {
        if(parent.getChildren() == null) return false;
        List<Student> children = parent.getChildren();
        
        Boolean isChild = Boolean.FALSE;
        
        for(int i = 0; i < children.size(); i++){
            if(children.get(i).getId().equals(studentId)){
                isChild = Boolean.TRUE;
            }
        }
        
        return isChild;
    }

    @Override
    public Boolean isInTeachersClass(User teacher, Integer studentId) {
        if(teacher.getSchoolClass() == null) return false;
        List<Student> students = teacher.getSchoolClass().getStudents();
        if(students == null) return false;
        
        Boolean isStudent = Boolean.FALSE;
        
        for(int i = 0; i < students.size(); i++){
            if(students.get(i).getId().equals(studentId)){
                isStudent = Boolean.TRUE;
            }
        }
        
        return isStudent;
    }
}