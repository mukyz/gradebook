/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softmetrix.service;

import com.softmetrix.model.Announcement;
import com.softmetrix.model.DTO.AnnouncementDTO;
import java.util.List;

/**
 *
 * @author Grupa2
 */
public interface AnnouncementService {
    
    public List<Announcement> getAll();
    public void save(AnnouncementDTO announcementDTO);
    public void delete(Integer id);
    public Announcement getLatestAnnouncement();
    
}
