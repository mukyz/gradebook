package com.softmetrix.model;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "username")
    private String username;
    
    @Column (name = "password")
    private String password;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "roles_id")
    private Role role;
    
    @OneToOne(mappedBy = "teacher", fetch = FetchType.LAZY)
    private SchoolClass schoolClass;
    
    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    private List<Student> children;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public SchoolClass getSchoolClass() {
        return schoolClass;
    }

    public void setSchoolClass(SchoolClass schoolClass) {
        this.schoolClass = schoolClass;
    }

    public List<Student> getChildren() {
        return children;
    }

    public void setChildren(List<Student> children) {
        this.children = children;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof User){
            User user = (User) obj;
            if(this.id == user.getId() ) return true;
            else return false;
        }
        else return false;
    }
}
