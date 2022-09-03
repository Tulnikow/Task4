package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public void createUsersTable() {
        Connection connect = null;
        Statement statement = null;
        String sql = "CREATE TABLE IF NOT EXISTS userdb.users "
                + "(`id` BIGINT NOT NULL AUTO_INCREMENT,"
                + "`name` VARCHAR(45) NULL,"
                + "`lastName` VARCHAR(45) NULL,"
                + "`age` TINYINT NULL," + "PRIMARY KEY (`id`));";
        try {
            connect = Util.getConn();
            statement = connect.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println("Ошибка создания таблицы");
        } finally {
            try {
                if (statement != null) statement.close();
                if (connect != null) connect.close();
            } catch (SQLException e) {
                System.out.println("Ошибка закрытия");
            }
        }
    }

    public void dropUsersTable() {
        Connection connect = null;
        Statement statement = null;
        String sql = "DROP TABLE IF EXISTS userdb.users";
        try {
            connect = Util.getConn();
            statement = connect.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println("Ошибка удаления таблицы");
        } finally {
            try {
                if (statement != null) statement.close();
                if (connect != null) connect.close();
            } catch (SQLException e) {
                System.out.println("Ошибка закрытия");
            }
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        Connection connection = null;
        PreparedStatement pstm = null;
        String sql = "INSERT INTO userdb.users (name,lastName,age) " + "VALUES (?,?,?);";

        try {
            connection = Util.getConn();
            pstm = connection.prepareStatement(sql);
            pstm.setString(1, name);
            pstm.setString(2, lastName);
            pstm.setString(3, String.valueOf(age));
            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Ошибка сохранения записи");
        } finally {
            try {
                if (pstm != null) pstm.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println("Ошибка закрытия");
            }

        }
    }

    public void removeUserById(long id) {
        Connection connection = null;
        Statement statement = null;
        String sql = "DELETE FROM  userdb.users WHERE id =" + id;
        try {
            connection = Util.getConn();
            statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println("Ошибка удаления записи");
        } finally {
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println("Ошибка закрытия");
            }
        }
    }

    public List<User> getAllUsers() {
        Connection connection = null;
        Statement statement = null;
        List<User> listuser = new ArrayList<>();
        ResultSet resultSet = null;
        try {
            connection = Util.getConn();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from userdb.users");
            while (resultSet.next()) {
                listuser.add(new User(resultSet.getLong("id"), resultSet.getString("name"),
                        resultSet.getString("lastName"), resultSet.getByte("age")));

            }
            return listuser;
        } catch (SQLException e) {
            System.out.println("Получения данных из таблицы не удалась");
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println("Ошибка закрытия");

            }
        }
        return null;
    }

    public void cleanUsersTable() {
        Connection connection = null;
        Statement statement = null;
        String sql = "DELETE FROM  userdb.users ;";
        try {
            connection = Util.getConn();
            statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println("Ошибка очистки таблицы");
        } finally {
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println("Ошибка закрытия statement");
            }

        }
    }

}
