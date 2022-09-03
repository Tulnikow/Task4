package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class Util {
    private static SessionFactory sessionFactory = null;
    private static final String URL = "jdbc:mysql://localhost:3306/userdb";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "richard";



    public Util() {
    }
    public static Connection getConn() {
        try {
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            System.err.println(e);
            System.out.println("Произошла ошибка соединения");
            return null;
        }
    }

    public static SessionFactory getSessionFactory() throws HibernateException {
        if (sessionFactory == null) {
            Configuration configuration = new Configuration();
            Properties settings = new Properties();
            settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
            settings.put(Environment.URL, "jdbc:mysql://localhost:3306/userdb?useSSL=false");
            settings.put(Environment.USER, USERNAME);
            settings.put(Environment.PASS, PASSWORD);
            //Имя класса Hibernate org.hibernate.dialect.Dialect, которое позволяет Hibernate генерировать SQL,
            // оптимизированный для конкретной реляционной базы данных.
            settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
            //Пишет все инструкции SQL в консоль
            settings.put(Environment.SHOW_SQL, "true");
            //стратегия для определения «текущей» сессии.
            settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
            // settings.put(Environment.HBM2DDL_AUTO, "create-drop"); Создает автаматически таблицу
            configuration.setProperties(settings);
            configuration.addAnnotatedClass(User.class);
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        }
        return sessionFactory;
    }
}
