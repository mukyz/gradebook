package com.softmetrix.service.impl;

import com.softmetrix.model.Course;
import com.softmetrix.model.DTO.TimetableForm;
import com.softmetrix.model.SchoolClass;
import com.softmetrix.model.Timetable;
import com.softmetrix.repository.CourseRepository;
import com.softmetrix.repository.SchoolClassRepository;
import com.softmetrix.repository.TimetableRepository;
import com.softmetrix.service.TimetableService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TimetableServiceImpl implements TimetableService{
    
    @Autowired
    private TimetableRepository timetableRepository;
    
    @Autowired
    private SchoolClassRepository schoolClassRepository;
    
    @Autowired 
    private CourseRepository courseRepository;

    @Override
    public void save(Integer schoolClassId, TimetableForm timetableForm) {
        SchoolClass schoolClass = schoolClassRepository.findById(schoolClassId).orElse(null);        
        if(schoolClass == null) return;  
        
        deleteEntries(schoolClass.getTimetable());
        
        List<Timetable> timetableEntries = new  ArrayList<>();
        
        Integer[][] timetable = timetableForm.getTimetable();
        
        for(int i = 0; i < timetable.length; i++){
            for(int j = 0; j<timetable[i].length; j++){
                if(timetable[i][j] == -1) continue;
                Course course = courseRepository.findById(timetable[i][j]).orElse(null);
                if(course == null) continue;  
                
                Timetable timetableEntry = new Timetable();
                timetableEntry.setCourse(course);
                timetableEntry.setSchoolClasss(schoolClass);
                timetableEntry.setDay(j);
                timetableEntry.setOrder(i);
                
                timetableEntries.add(timetableEntry);
            }            
        }        
        timetableRepository.saveAll(timetableEntries);        
    }

    @Override
    public List<List<String>> getTimetable(SchoolClass sClass) {
        if(sClass == null) return null;
        List<List<String>> timetable = new  ArrayList<>();
        
        List<Timetable> timetableEntrys = sClass.getTimetable();
        
        for(int i = 0; i < 8 ; i++){
            List<String> orderList = new ArrayList<>();
            for(int j = 0; j < 5; j++){                
                String value= "";
                for(Timetable timetableEntry: timetableEntrys){
                    if(timetableEntry.getOrder() == i && timetableEntry.getDay() == j){
                        value = timetableEntry.getCourse().getName();
                    }
                }
                orderList.add(value);
            }
            timetable.add(orderList);
        }
        
        return timetable;        
    }    

    @Override
    public Optional<TimetableForm> getTimetableForm(Integer schoolClassId) {        
        SchoolClass  sClass = schoolClassRepository.findById(schoolClassId).orElse(null);        
        if(sClass == null){
            return Optional.empty();
        }
        
        TimetableForm form = new TimetableForm();
        List<Timetable> timetableEntrys = sClass.getTimetable();
        
        for(int i = 0; i < 8 ; i++){
            for(int j = 0; j < 5; j++){               
                for(Timetable timetableEntry: timetableEntrys){
                    if(timetableEntry.getOrder() == i && timetableEntry.getDay() == j){
                        form.getTimetable()[i][j] = timetableEntry.getCourse().getId();
                    }
                }
            }
        }
        
        return Optional.of(form);
    }

    @Override
    public void deleteEntries(List<Timetable> entries) {
        timetableRepository.deleteAll(entries);
    }
}
