package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.*;
import jm.task.core.jdbc.util.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private static final String CREATE_TABLE_SQL = "CREATE TABLE IF NOT EXISTS userdb.users "
            + "(`id` BIGINT NOT NULL AUTO_INCREMENT,"
            + "`name` VARCHAR(45) NULL,"
            + "`lastName` VARCHAR(45) NULL,"
            + "`age` TINYINT NULL," + "PRIMARY KEY (`id`));";
    private static final String TABLE_DROP_SQL = "DROP TABLE IF EXISTS userdb.users";
    private static final String INSERT_IN_TABLE_SQL = "INSERT INTO userdb.users (name,lastName,age) VALUES (?,?,?);";
    private static final String DELETE_FROM_TABLE_SQL = "DELETE FROM  userdb.users WHERE id =";
    private static final String CLEAR_TABLE_SQL = "DELETE FROM  userdb.users";
    private static final String SELECT_ALL = "select * from userdb.users";

    public void createUsersTable() {
        try (Connection connect = Util.getConn()) {
            Statement statement = connect.createStatement();
            statement.executeUpdate(CREATE_TABLE_SQL);
        } catch (SQLException e) {
            System.out.println("Ошибка создания таблицы");
        }
    }

    public void dropUsersTable() {
        try (Connection connect = Util.getConn()) {
            Statement statement = connect.createStatement();
            statement.executeUpdate(TABLE_DROP_SQL);
        } catch (SQLException e) {
            System.out.println("Ошибка удаления таблицы");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = Util.getConn()) {
            PreparedStatement pstm = connection.prepareStatement(INSERT_IN_TABLE_SQL);
            pstm.setString(1, name);
            pstm.setString(2, lastName);
            pstm.setString(3, String.valueOf(age));
            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Ошибка сохранения записи");
        }
    }

    public void removeUserById(long id) {
        try (Connection connection = Util.getConn()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate(DELETE_FROM_TABLE_SQL + id);
        } catch (SQLException e) {
            System.out.println("Ошибка удаления записи");
        }
    }

    public List<User> getAllUsers() {
        List<User> listuser = new ArrayList<>();
        try (Connection connection = Util.getConn()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_ALL);
            while (resultSet.next()) {
                listuser.add(new User(resultSet.getLong("id"), resultSet.getString("name"),
                        resultSet.getString("lastName"), resultSet.getByte("age")));
            }
            return listuser;
        } catch (SQLException e) {
            System.out.println("Получения данных из таблицы не удалась");
        }
        return null;
    }

    public void cleanUsersTable() {
        try (Connection connection = Util.getConn()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate(CLEAR_TABLE_SQL);
        } catch (SQLException e) {
            System.out.println("Ошибка очистки таблицы");
        }
    }
}


