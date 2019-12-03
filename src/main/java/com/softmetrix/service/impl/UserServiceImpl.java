package com.softmetrix.service.impl;

import com.softmetrix.model.Role;
import com.softmetrix.model.User;
import com.softmetrix.model.DTO.UserDTO;
import com.softmetrix.repository.UserRepository;
import com.softmetrix.service.RoleService;
import com.softmetrix.service.UserService;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    @Autowired private UserRepository userRepository;    
    @Autowired private RoleService roleService;    
    @Autowired private BCryptPasswordEncoder passwordEncoder;    

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        
        if(user == null) throw new UsernameNotFoundException("Korisničko ime ne postoji");
        
        HashSet<GrantedAuthority> roles = new HashSet<>();
        
        Role role = user.getRole();
        if(role != null){
            roles.add(new SimpleGrantedAuthority(role.getRoleName()));
        }
        
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),roles);
    }

    @Override
    public Page<User> getPage(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> getByRoleName(String rolename) {
        return userRepository.findByRole(roleService.findByRoleName(rolename));
    }

    @Override
    public Optional<User> findById(Integer id) {
        return userRepository.findById(id);
    }

    @Override
    public User save(UserDTO userDTO) {
        User newUser = new User();
        
        if(userDTO.getId() != null){
            newUser = userRepository.findById(userDTO.getId()).orElse(null);
        }
        
        if(userDTO.getUsername()!= null){
            newUser.setUsername(userDTO.getUsername());
        }
        
        if(userDTO.getPassword()!= null && userDTO.getPassword().isEmpty() == false){
            newUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }
        
        if(userDTO.getRoleId()!= null){
            Role role = roleService.findById(userDTO.getRoleId());
            newUser.setRole(role); 
        }
        
        newUser = userRepository.save(newUser);
        
        return newUser;
    }
    
    @Override
    public User save(User user) {
        return userRepository.save(user);             
    }

    @Override
    public void delete(Integer id) {
        if(id != null){
            userRepository.deleteById(id);
        }
    }

    @Override
    public List<User> findUnassignedTeachers() {
        return userRepository.findByRoleAndSchoolClassIsNull(roleService.findByRoleName(Role.TEACHER));
    }
}
