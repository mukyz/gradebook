package com.softmetrix.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "open_door")
public class OpenDoor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private User parent;
    
    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private User teacher;
    
    @Column(name = "date")
    private Date date;
    
    @Column(name = "is_accepted")
    private Boolean isAccepted;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getParent() {
        return parent;
    }

    public void setParent(User parent) {
        this.parent = parent;
    }

    public User getTeacher() {
        return teacher;
    }

    public void setTeacher(User teacher) {
        this.teacher = teacher;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Boolean getIsAccepted() {
        return isAccepted;
    }

    public void setIsAccepted(Boolean accepted) {
        this.isAccepted = accepted;
    }
}