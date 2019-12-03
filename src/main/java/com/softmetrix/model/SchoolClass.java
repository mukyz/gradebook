package com.softmetrix.model;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

@Entity
@Table(name = "class")
public class SchoolClass{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "name")
    private String name;
    
    @OneToOne
    @JoinColumn(name = "teacher_id")
    private User teacher;
    
    @OneToMany(mappedBy = "schoolClass", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<Student> students;
    
    @ManyToMany
    @JoinTable(
        name = "class_course",
        joinColumns = @JoinColumn(name = "class_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "course_id", referencedColumnName = "id"))
    @OrderBy("name ASC")
    private List<Course> courses;
    
    @OneToMany
    @JoinTable(
        name = "class_test",
        joinColumns = @JoinColumn(name = "class_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "test_id", referencedColumnName = "id"))
    @OrderBy("name ASC")
    private List<Test> tests;
    
    @OneToMany(mappedBy = "schoolClass")
    private List<Timetable> timetable;

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

    public User getTeacher() {
        return teacher;
    }

    public void setTeacher(User teacher) {
        this.teacher = teacher;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    } 

    public List<Timetable> getTimetable() {
        return timetable;
    }

    public void setTimetable(List<Timetable> timetable) {
        this.timetable = timetable;
    }

	public List<Test> getTests() {
		return tests;
	}

	public void setTests(List<Test> tests) {
		this.tests = tests;
	}
    
    
}