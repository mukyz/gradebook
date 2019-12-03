/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softmetrix.service.impl;

import com.softmetrix.model.Announcement;
import com.softmetrix.model.DTO.AnnouncementDTO;
import com.softmetrix.repository.AnnouncementRepository;
import com.softmetrix.service.AnnouncementService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Grupa2
 */
@Service
public class AnnouncementServiceImpl implements AnnouncementService{
    @Autowired
    private AnnouncementRepository announcementRepository;

    @Override
    public List<Announcement> getAll() {
        return announcementRepository.findAll();
    }

    @Override
    public void save(AnnouncementDTO announcementDTO) {
        Announcement newAnnouncement = new Announcement();
        
        newAnnouncement.setAnnouncement(announcementDTO.getText());
        
        announcementRepository.save(newAnnouncement);
    }

    @Override
    public void delete(Integer id) {
        announcementRepository.deleteById(id);
    }

    @Override
    public Announcement getLatestAnnouncement() {
        return announcementRepository.findTopByOrderByIdDesc();
    }
    
    
    
}
