package com.softmetrix.model.DTO;

public class TimetableForm {    
    private Integer[][] timetable = new Integer[8][5];   //8 classes, 5 days a week

    public Integer[][] getTimetable() {
        return timetable;
    }

    public void setTimetable(Integer[][] timetable) {
        this.timetable = timetable;
    }
}