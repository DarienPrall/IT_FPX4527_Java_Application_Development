package edu.capella.bsit.registration_hibernate;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.hibernate.Session;

public class RegistrationService {
    // EntityManager must be declared protected - one of relatively few uses
    // for a protected attribute. 
    protected EntityManager em;
    
    // Inject the EntityManager via the constructor
    public RegistrationService(EntityManager em) {
        this.em = em;
    }
    
    // Get list of all courses
    public List<Course> getAvailableCourses() {
        // HQL queries reference class and attribute names, not tables and columns.
        // No need to use select when retrieving all attributes.
        String hql = "from Course order by courseCode";
        // Typed query returns Java objects of specified class
        TypedQuery<Course> query = em.createQuery(hql, Course.class);
        // Return results as a Java List<Course>
        return query.getResultList();
    }
    
    // Create registration in a course for the user. 
    public void registerForCourse(String id, Course crs) {
        CourseRegistration reg = new CourseRegistration(id, crs.getCourseCode(), 
                crs.getCreditHours());
        // Avoids javax.persistence.EntityExistsException
        em.unwrap(Session.class).clear();
        // Need to use database transaction to insert
        em.getTransaction().begin();
        // Save object to database
        em.persist(reg);
        em.getTransaction().commit();
    }
    
    // Get list of registered courses for a given ID
    public List<CourseRegistration> getCourseRegistrationsForID(String id) {
        String hql = "from CourseRegistration where learnerID = :id";
        TypedQuery<CourseRegistration> query = em.createQuery(hql, CourseRegistration.class);
        query.setParameter("id", id);
        return query.getResultList();
    }
    
        public String validateRegistration(Course c, String learnerID) {
        String message = "";
        int registeredCredits = 0;

        List<CourseRegistration> registeredCourses = getCourseRegistrationsForID(learnerID);

        for (CourseRegistration crsReg : registeredCourses) {
            if (crsReg.getCourseCode().equals(c.getCourseCode())) {
                message = "Already registered for " + c.getCourseCode();
                break;
            }
            registeredCredits += crsReg.getCreditHours();
        }

        if (message.isEmpty() && registeredCredits + c.getCreditHours() > 9) {
            message = c.getCourseCode() + " exceeds the 9 credit hour limit.";
        }

        return message;
    }
    
    public long getCreditsForID(String id) {
        String hql = "select sum(crs.creditHours) from CourseRegistration crs "
                + "where learnerID = :id";
        Query query = em.createQuery(hql);
        query.setParameter("id", id);
        return (long) query.getSingleResult();
    }
}
