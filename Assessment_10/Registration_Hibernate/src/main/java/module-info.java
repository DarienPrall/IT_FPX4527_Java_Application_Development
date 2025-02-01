module edu.capella.bsit.registration_hibernate {
    requires javafx.controls;
    requires org.mariadb.jdbc;
    requires org.hibernate.orm.core;
    requires org.hibernate.commons.annotations;
    requires java.persistence;
    opens edu.capella.bsit.registration_hibernate;
    exports edu.capella.bsit.registration_hibernate;
}
