package com.softmetrix.service;

import com.softmetrix.model.DTO.StudentDTO;
import com.softmetrix.model.Student;
import com.softmetrix.model.User;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface StudentService {
    public Page<Student> getPage(Pageable pageable);
    public Student findById(Integer id);
    public Student save(Student student);
    public Student save(StudentDTO studentDTO);
    public List<Student> getStudentsWithoutClass();
    public void delete(Integer id);
    public Boolean isChildOf(User parent, Integer studentId);
    public Boolean isInTeachersClass(User teacher, Integer studentId);
}
