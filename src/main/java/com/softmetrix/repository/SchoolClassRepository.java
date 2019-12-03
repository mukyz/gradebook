package com.softmetrix.repository;

import com.softmetrix.model.SchoolClass;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchoolClassRepository extends JpaRepository<SchoolClass, Integer>{
    
}
