package com.softmetrix.repository;

import com.softmetrix.model.Role;
import com.softmetrix.model.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer>{
    List<User> findByRole(Role role);    
    List<User> findByRoleAndSchoolClassIsNull(Role role);
    User findByUsername(String username);
}