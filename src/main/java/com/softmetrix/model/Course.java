package com.softmetrix.model;

import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "course")
public class Course{    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "name")
    private String name;
    
    @Column(name="description")
    private String description;
    
    @OneToMany(mappedBy = "course")
    private List<Test> tests;
    
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    
    @Override
    public boolean equals(Object obj) {
    	if(obj == this) return true;
    	if(obj instanceof Course) {
    		Course that = (Course) obj;
    		if(Objects.equals(that.getId(), getId())) return true;
    		else return false;
    	}
    	return false;
    }
    
}