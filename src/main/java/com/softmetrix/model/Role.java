package com.softmetrix.model;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "roles")
public class Role {
    public static final String ADMINISTRATOR="ADMINISTRATOR";
    public static final String PRINCIPAL="PRINCIPAL";
    public static final String TEACHER="TEACHER";
    public static final String PARENT="PARENT";
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "rolename")
    private String roleName;
    
    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
    private List<User> users;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }  

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}