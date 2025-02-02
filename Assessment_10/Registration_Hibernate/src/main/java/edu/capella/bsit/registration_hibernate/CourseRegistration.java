package edu.capella.bsit.registration_hibernate;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Table;

@Entity
@Table(name = "course_registration")
public class CourseRegistration extends Course implements Serializable {

    @Id
    @Column(name = "learner_id")
    private String learnerID;

    public CourseRegistration() {
        // Default constructor for JPA
    }

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
