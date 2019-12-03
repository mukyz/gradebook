package com.softmetrix.repository;

import com.softmetrix.model.StudentAvatar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentAvatarRepository extends JpaRepository<StudentAvatar, Integer>{
    
}
