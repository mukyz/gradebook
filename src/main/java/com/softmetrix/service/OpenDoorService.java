/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softmetrix.service;

import com.softmetrix.model.DTO.OpenDoorDTO;
import com.softmetrix.model.OpenDoor;
import com.softmetrix.model.User;
import java.util.List;

/**
 *
 * @author Grupa2
 */
public interface OpenDoorService {
    public List<OpenDoor> getOpenDoorRequest(User user);
    public void setStatus (Integer id, String status);

    public void save(OpenDoorDTO openDoorDTO);
    
    public OpenDoor getNextApointment(User user);
    
}
