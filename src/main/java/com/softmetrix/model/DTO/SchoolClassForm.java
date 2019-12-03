package com.softmetrix.model.DTO;

import com.softmetrix.model.Course;
import com.softmetrix.model.SchoolClass;
import com.softmetrix.model.Student;
import java.util.ArrayList;
import java.util.List;

public class SchoolClassForm {    
    private Integer id;
    private String name;
    private Integer teacherId;
    private List<Integer> studentIds;
    private List<Integer> coursesIds;
    
    public SchoolClassForm(){}

    public SchoolClassForm(SchoolClass sclass) {
        this.id = sclass.getId();
        this.name = sclass.getName();
        if(sclass.getTeacher() != null) this.teacherId = sclass.getTeacher().getId();
        
        List<Student> students = sclass.getStudents();
        if(students != null){
            studentIds = new ArrayList<>();
            students.forEach((t) -> {
                studentIds.add(t.getId());
            });
        }
        
        List<Course> courses = sclass.getCourses();
        if(courses != null){
            coursesIds = new ArrayList<>();
            courses.forEach((t) -> {
                coursesIds.add(t.getId());
            });
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    public List<Integer> getStudentIds() {
        return studentIds;
    }

    public void setStudentIds(List<Integer> studentIds) {
        this.studentIds = studentIds;
    }

    public List<Integer> getCoursesIds() {
        return coursesIds;
    }

    public void setCoursesIds(List<Integer> coursesIds) {
        this.coursesIds = coursesIds;
    }  
}