package com.softmetrix.model.DTO;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

public class OpenDoorDTO {
    
    private Integer parentId;
    private Integer teacherId;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date date;    

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
