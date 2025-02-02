package edu.capella.bsit.registration_hibernate;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Table(name = "course")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Course implements Serializable {

    @Id
    @Column(name = "course_code")
    private String courseCode;

    @Column(name = "credit_hours")
    private int creditHours;

    public Course() {
        // Default constructor for JPA
    }

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
