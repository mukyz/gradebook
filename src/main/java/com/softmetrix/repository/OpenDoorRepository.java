package com.softmetrix.repository;

import com.softmetrix.model.OpenDoor;
import com.softmetrix.model.User;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OpenDoorRepository  extends JpaRepository<OpenDoor, Integer>{
    List<OpenDoor> findByTeacher(User user); 
    List<OpenDoor> findByParent(User user);
    
    OpenDoor findTopByTeacherAndDateAfterAndIsAcceptedOrderByDateAsc(User user, Date date, Boolean b);
    OpenDoor findTopByParentAndDateAfterAndIsAcceptedOrderByDateAsc(User user, Date date, Boolean b);
}
