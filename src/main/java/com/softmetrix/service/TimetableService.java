/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softmetrix.service;

import com.softmetrix.model.DTO.TimetableForm;
import com.softmetrix.model.SchoolClass;
import com.softmetrix.model.Timetable;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Grupa2
 */
public interface TimetableService {
    
    public void save(Integer schoolClassId, TimetableForm timetableForm);
    public List<List<String>> getTimetable(SchoolClass sClass);
    public Optional<TimetableForm> getTimetableForm(Integer schoolClassId);  
    public void deleteEntries(List<Timetable> entries);
}
