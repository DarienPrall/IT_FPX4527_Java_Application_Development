package edu.capella.bsit.registerforcourse;

public class Course {
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
