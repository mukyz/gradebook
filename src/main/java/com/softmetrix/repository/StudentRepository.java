package com.softmetrix.repository;

import com.softmetrix.model.Student;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Integer>{
    List<Student> findBySchoolClassIsNull();
}
