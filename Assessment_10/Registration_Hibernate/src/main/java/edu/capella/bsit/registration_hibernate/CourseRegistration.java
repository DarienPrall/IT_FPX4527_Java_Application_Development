package edu.capella.bsit.registration_hibernate;

import java.io.Serializable;

public class CourseRegistration extends Course implements Serializable {
    private String learnerID;
    
    public CourseRegistration(String learnerID, Course crs) {
        super(crs.getCourseCode(), crs.getCreditHours());
        this.learnerID = learnerID;
    }
    
    public CourseRegistration(String learnerID, String courseCode, int creditHours) {
        super(courseCode, creditHours);
        this.learnerID = learnerID;
    }

    public String getLearnerID() {
        return learnerID;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
