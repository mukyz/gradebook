package com.softmetrix.repository;

import com.softmetrix.model.Course;
import com.softmetrix.model.Grade;
import com.softmetrix.model.Student;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GradeRepository extends JpaRepository<Grade, Integer> {
    @Query("select count(g) from Grade g where g.course = ?1 and grade=?2 and isFinalGrade = true")
    Integer getStatByCourseAndGrade(Course course, Integer grade);
    
    @Query("select count(g) from Grade g where g.course = ?1  and g.grade = ?2 and g.student in ?3 and g.isFinalGrade = true")
    Integer getStatBySchoolClass(Course course, Integer grade, List<Student> students);
    
    List<Grade> findByStudentAndCourseAndIsFinalGrade(Student student, Course course, Boolean isFinalGrade);
    List<Student> findByCourseAndIsFinalGrade(Course course, Boolean isFinalGrade);
}
