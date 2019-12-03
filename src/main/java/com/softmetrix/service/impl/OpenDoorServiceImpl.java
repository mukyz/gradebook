package com.softmetrix.service.impl;

import com.softmetrix.model.DTO.OpenDoorDTO;
import com.softmetrix.model.OpenDoor;
import com.softmetrix.model.Role;
import com.softmetrix.model.User;
import com.softmetrix.repository.OpenDoorRepository;
import com.softmetrix.service.OpenDoorService;
import com.softmetrix.service.UserService;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OpenDoorServiceImpl implements OpenDoorService{
    @Autowired OpenDoorRepository openDoorRepository;
    @Autowired UserService userService;

    @Override
    public List<OpenDoor> getOpenDoorRequest(User user) {
        if(user.getRole().getRoleName().equals(Role.TEACHER))
            return openDoorRepository.findByTeacher(user);
        if(user.getRole().getRoleName().equals(Role.PARENT))
            return openDoorRepository.findByParent(user);
        
        return null;
    }

    @Override
    public void setStatus(Integer id, String status) {
        OpenDoor od = openDoorRepository.findById(id).orElse(null);
        
        if(od != null){
            if(status.equals("accept")){
                od.setIsAccepted(Boolean.TRUE);
            } else if(status.equals("reject")){
                od.setIsAccepted(Boolean.FALSE);
            }
            
            openDoorRepository.save(od);
        }
    }

    @Override
    public void save(OpenDoorDTO openDoorDTO) {
        User parent = userService.findById(openDoorDTO.getParentId()).orElse(null);
        if (parent == null) return;
        User teacher = userService.findById(openDoorDTO.getTeacherId()).orElse(null);
        if (teacher == null) return;
        
        OpenDoor od = new OpenDoor();
        od.setParent(parent);
        od.setTeacher(teacher);
        od.setDate(openDoorDTO.getDate());
        
        openDoorRepository.save(od);
    }

    @Override
    public OpenDoor getNextApointment(User user) {
        if(user.getRole().getRoleName().equals(Role.PARENT))
            return openDoorRepository.findTopByParentAndDateAfterAndIsAcceptedOrderByDateAsc(user, new Date(), Boolean.TRUE);
        if(user.getRole().getRoleName().equals(Role.TEACHER))
            return openDoorRepository.findTopByTeacherAndDateAfterAndIsAcceptedOrderByDateAsc(user, new Date(), Boolean.TRUE);
        return null;
    }
    
}
