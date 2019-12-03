package com.softmetrix.controllers;

import com.softmetrix.model.Student;
import com.softmetrix.service.StudentService;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AvatarController {
    @Autowired StudentService studentService;
    
    @GetMapping(value = "/avatar/{studId}")
    @ResponseBody
    public ResponseEntity<InputStreamResource> downloadStudentAvatarImage(@PathVariable Integer studId) {
        Student student = studentService.findById(studId);
        
        if(student.getAvatar()!= null){
            try {
                return ResponseEntity.ok()
                        .contentLength(student.getAvatar().getImage().length())
                        .contentType(MediaType.parseMediaType(student.getAvatar().getType()))
                        .body(new InputStreamResource(student.getAvatar().getImage().getBinaryStream()));
            } catch (SQLException ex) {
                Logger.getLogger(AvatarController.class.getName()).log(Level.SEVERE, null, ex);
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }    
}
