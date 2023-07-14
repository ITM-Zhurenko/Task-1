package jm.task.core.jdbc.util;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    public Util() {

    }

    public static SessionFactory getSessionFactory() throws HibernateException {
        Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
        return configuration.buildSessionFactory();
    }

    public static Connection getConnection() throws SQLException {
        final Properties JDBCProperties = new Properties();
        JDBCProperties.setProperty("user", "postgres");
        JDBCProperties.setProperty("password", "1234");
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/db", JDBCProperties);
    }
}
