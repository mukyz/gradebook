package com.softmetrix.service;

import com.softmetrix.model.User;
import com.softmetrix.model.DTO.UserDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService{
    
    public Page<User> getPage(Pageable pageable); 
    public User save(User user);
    public User save(UserDTO userDTO);
    public User findByUsername(String username);  
    public Optional<User> findById(Integer id);    
    public void delete(Integer id);    
    public List<User> getByRoleName(String rolename);
    public List<User> findUnassignedTeachers();
}
