package com.softmetrix.repository;

import com.softmetrix.model.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnnouncementRepository  extends JpaRepository<Announcement, Integer>{
    public Announcement findTopByOrderByIdDesc();
}
