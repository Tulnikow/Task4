package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();
        userService.dropUsersTable();

        User user1 = new User("Пупыркин", "Андрей", (byte) 53);
        User user2 = new User("Самоедова", "Наташа", (byte) 41);
        User user3 = new User("Кузнецов", "Владимир", (byte) 18);
        User user4 = new User("Горелкина", "Анна", (byte) 15);
        User[] users = new User[]{user1, user2, user3, user4};

        userService.createUsersTable();
        for (User user : users) {
            userService.saveUser(user.getName(), user.getLastName(), user.getAge());
            System.out.println("Добавлен в базу  " + user);
        }
        //userService.removeUserById(2L);
        List<User> listusers = userService.getAllUsers();
        for (User user : listusers) {
            System.out.println(user);
        }
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
