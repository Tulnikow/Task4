package jm.task.core.jdbc.util;

import java.util.Properties;


import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;


public class HibernateUtil {
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();

                Properties settings = new Properties();
                settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                settings.put(Environment.URL, "jdbc:mysql://localhost:3306/userdb?useSSL=false");
                settings.put(Environment.USER, "root");
                settings.put(Environment.PASS, "richard");
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
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Не удалось получить sessionFactory");
            }
        }
        return sessionFactory;
    }
}
