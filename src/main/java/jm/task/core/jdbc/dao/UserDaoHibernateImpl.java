package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;


import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private SessionFactory sessionFactory;
    private static final String CREATE_TABLE_SQL = "CREATE TABLE IF NOT EXISTS userdb.users "
            + "(`id` BIGINT NOT NULL AUTO_INCREMENT,"
            + "`name` VARCHAR(45) NULL,"
            + "`lastName` VARCHAR(45) NULL,"
            + "`age` TINYINT NULL," + "PRIMARY KEY (`id`));";
    private static final String SELECT_ALL = "select a from User a";
    private static final String TABLE_DROP_SQL = "DROP TABLE IF EXISTS userdb.users";

    public UserDaoHibernateImpl() {
        try {
            sessionFactory = Util.getSessionFactory();

        } catch (Exception e) {
            System.out.println("Не удалось получить соединение Hibernate");
        }

    }


    @Override
    public void createUsersTable() {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.createSQLQuery(CREATE_TABLE_SQL).addEntity(User.class).executeUpdate();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }

    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery(TABLE_DROP_SQL).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            User user = session.load(User.class, id);
            session.delete(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> listusers;
        try (Session session = sessionFactory.openSession()) {
            listusers = session.createQuery(SELECT_ALL).getResultList();
        } catch (Exception e) {
            System.out.println("Получение списка не удалось");
            e.printStackTrace();
            return null;
        }
        return listusers;
    }

    @Override
    public void cleanUsersTable() {

        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createQuery("DELETE FROM User")
                    .executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }

    }
}
