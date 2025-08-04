module com.b2la.hnb {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires jakarta.persistence;
    requires com.fasterxml.jackson.annotation;
    requires org.hibernate.orm.core;
    requires jbcrypt;
    requires java.prefs;
    requires com.google.gson;
    requires java.desktop;

    opens com.b2la.hnb to javafx.fxml;
    exports com.b2la.hnb;

    requires java.naming;
    requires java.sql;

    opens com.b2la.hnb.models to org.hibernate.orm.core, javafx.base;

    exports com.b2la.hnb.models;
    exports com.b2la.hnb.services;
    exports com.b2la.hnb.util;
}