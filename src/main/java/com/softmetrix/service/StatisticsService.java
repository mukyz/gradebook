package com.softmetrix.service;

import com.softmetrix.model.Course;
import com.softmetrix.model.SchoolClass;
import java.util.HashMap;
import java.util.List;

public interface StatisticsService {
    List<HashMap<Object, Object>> getStasByCourse(Course course);
    List<HashMap<Object, Object>> getStasBySchoolClass(SchoolClass schoolClass);
}
