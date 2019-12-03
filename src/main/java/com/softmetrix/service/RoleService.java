package com.softmetrix.service;

import com.softmetrix.model.Role;

public interface RoleService {
    public Role findById(Integer id);
    public Iterable<Role> findAll();
    public Role findByRoleName(String rolename);
}
