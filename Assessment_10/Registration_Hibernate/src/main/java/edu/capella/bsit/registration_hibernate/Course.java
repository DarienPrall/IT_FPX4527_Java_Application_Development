package edu.capella.bsit.registration_hibernate;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Id;

public class Course implements Serializable {
    private String courseCode;
    private int creditHours;
    

    public Course(String courseCode, int creditHours) {
        this.courseCode = courseCode;
        this.creditHours = creditHours;
    }
    
    public String getCourseCode() {
        return courseCode;
    }
    
    public int getCreditHours() {
        return creditHours;
    }
    
    @Override
    public String toString() {
        return courseCode + " (" + creditHours + ")";
    }
}
