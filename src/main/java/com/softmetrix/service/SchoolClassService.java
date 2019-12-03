package com.softmetrix.service;

import com.softmetrix.model.DTO.SchoolClassForm;
import com.softmetrix.model.SchoolClass;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SchoolClassService {    
    public Page<SchoolClass> getPage(Pageable pageable);
    public List<SchoolClass> getAll();
    public SchoolClass save(SchoolClass schoolClass);
    public SchoolClass save(SchoolClassForm schoolClassDTO);
    public SchoolClass findById(Integer id);
    public void delete(Integer id); 
}
