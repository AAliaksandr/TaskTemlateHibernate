package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {

        UserServiceImpl userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Ivan", "Ivanov", (byte) 25);
        userService.saveUser("Petr", "Petrov", (byte) 26);
        userService.saveUser("Sidor", "Sidorov", (byte) 27);
        userService.saveUser("Mariya", "Ivanova", (byte) 24);

        List<User> list = userService.getAllUsers();
        for (User user:list) {
            System.out.println(user);
        }
        userService.cleanUsersTable();
        userService.dropUsersTable();


    }
}
