package com.softmetrix.service.impl;

import com.softmetrix.model.Role;
import com.softmetrix.repository.RoleRepository;
import com.softmetrix.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService{
    @Autowired
    private RoleRepository roleRepository;


    @Override
    public Iterable<Role> findAll() {
        return roleRepository.findAll();
    }  

    @Override
    public Role findById(Integer id) {
        return roleRepository.findById(id).orElse(null);
    }

    @Override
    public Role findByRoleName(String rolename) {
        return roleRepository.findByRoleName(rolename);
    }
}
