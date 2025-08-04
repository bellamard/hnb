package com.b2la.hnb.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.HashMap;
import java.util.Map;

public class JPAUtil {
    private static final String PERSISTENCE_UNIT_NAME = "gestion_hnb";
    private static EntityManagerFactory factory;

    public static EntityManagerFactory getEntityManagerFactory() {
        if (factory == null) {

            Map<String, String> properties = new HashMap<>();
            properties.put("jakarta.persistence.jdbc.url","jdbc:mysql://localhost:3306/gestion_hnb");
            properties.put("jakarta.persistence.jdbc.user","root");
            properties.put("jakarta.persistence.jdbc.password","");
            properties.put("jakarta.persistence.jdbc.driver","com.mysql.cj.jdbc.Driver");
            properties.put("hibernate.hbm2ddl.auto", "update");
            properties.put("hibernate.show_sql", "true");
            properties.put("hibernate.format_sql", "true");

            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME, properties);
        }
        return factory;
    }

    public static EntityManager getEntityManager() {
        return  getEntityManagerFactory().createEntityManager();
    }

    public static void shutdown() {
        if (factory != null) {
            factory.close();
        }
    }
}
