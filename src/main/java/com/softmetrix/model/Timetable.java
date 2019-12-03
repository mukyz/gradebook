package com.softmetrix.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "timetable")
public class Timetable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
   
    @ManyToOne
    @JoinColumn(name = "class_id")
    private SchoolClass schoolClass;
    
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
    
    @Column(name = "day")
    private Integer day;
    
    @Column(name = "order_num")
    private Integer order;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public SchoolClass getSchoolClasss() {
        return schoolClass;
    }

    public void setSchoolClasss(SchoolClass schoolClasss) {
        this.schoolClass = schoolClasss;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }
}