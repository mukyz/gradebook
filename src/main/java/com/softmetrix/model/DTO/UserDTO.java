package com.softmetrix.model.DTO;

import com.softmetrix.model.User;

public class UserDTO{
    private Integer id;
    private String username;   
    private String password; 
    private Integer roleId;
    
    public UserDTO(){}

    public UserDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        if(user.getRole().getId()!= null) this.roleId = user.getRole().getId();
    }

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

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}