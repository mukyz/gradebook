package com.softmetrix.repository;

import com.softmetrix.model.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Integer>{
    public Role findByRoleName(String rolename);
}
