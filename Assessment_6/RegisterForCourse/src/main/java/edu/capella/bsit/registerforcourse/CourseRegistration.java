package edu.capella.bsit.registerforcourse;

public class CourseRegistration extends Course {
    private String learnerID;

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
