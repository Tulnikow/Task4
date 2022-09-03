package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;

import java.util.List;

public class UserServiceImpl implements UserService {
    //private UserDaoJDBCImpl dao;
    private UserDaoHibernateImpl dao;


    public UserServiceImpl() {
        //dao = new UserDaoJDBCImpl();
        dao = new UserDaoHibernateImpl();
    }

    public void createUsersTable() {
        this.dao.createUsersTable();
    }

    public void dropUsersTable() {
        this.dao.dropUsersTable();
    }

    public void saveUser(String name, String lastName, byte age) {
        this.dao.saveUser(name, lastName, age);
    }

    public void removeUserById(long id) {
        this.dao.removeUserById(id);
    }

    public List<User> getAllUsers() {
        return this.dao.getAllUsers();
    }

    public void cleanUsersTable() {
        this.dao.cleanUsersTable();
    }


}
