package com.softmetrix.model;

import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "students")
public class Student{    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "first_name")
    private String firstName;
    
    @Column(name = "last_name")
    private String lastName;
    
    @ManyToOne
    @JoinColumn(name = "class_id")
    private SchoolClass schoolClass;
    
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private User parent;
    
    @Column(name="date_of_birth")
    private Date dateOfBirth;
    
    @Column(name="place_of_birth")
    private String placeOfBirth;
    
    @Column(name="address")
    private String address;
    
    @OneToOne(mappedBy = "student", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private StudentAvatar avatar;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public SchoolClass getSchoolClass() {
        return schoolClass;
    }

    public void setSchoolClass(SchoolClass schoolClass) {
        this.schoolClass = schoolClass;
    }

    public User getParent() {
        return parent;
    }

    public void setParent(User parent) {
        this.parent = parent;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public StudentAvatar getAvatar() {
        return avatar;
    }

    public void setAvatar(StudentAvatar avatar) {
        this.avatar = avatar;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Student){
            Student student = (Student) obj;
            if(this.id == student.getId() ) return true;
            else return false;
        }
        else return false;
    }
}