package com.softmetrix.repository;

import com.softmetrix.model.Timetable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimetableRepository extends JpaRepository<Timetable, Integer>{
    
}
