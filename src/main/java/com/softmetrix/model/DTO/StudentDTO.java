package com.softmetrix.model.DTO;

import com.softmetrix.model.Student;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

public class StudentDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private Integer parentId;
    private Integer classId;
    @DateTimeFormat (pattern="yyyy-MM-dd")
    private Date dateOfBirt;
    private String placeOfBirth;
    private String address;
    private MultipartFile avatarFile;
    
    public StudentDTO(){}

    public StudentDTO(Student student) {
        this.id = student.getId();
        this.firstName = student.getFirstName();
        this.lastName = student.getLastName();
        if(student.getParent()!= null)this.parentId = student.getParent().getId();
        if(student.getSchoolClass()!=null)this.classId = student.getSchoolClass().getId();
        this.dateOfBirt = student.getDateOfBirth();
        this.placeOfBirth = student.getPlaceOfBirth();
        this.address = student.getAddress();
    }

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

    public Date getDateOfBirt() {
        return dateOfBirt;
    }

    public void setDateOfBirt(Date dateOfBirt) {
        this.dateOfBirt = dateOfBirt;
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

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public MultipartFile getAvatarFile() {
        return avatarFile;
    }

    public void setAvatarFile(MultipartFile avatarFile) {
        this.avatarFile = avatarFile;
    }
    
    
}
