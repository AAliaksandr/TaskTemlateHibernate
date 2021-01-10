package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {



    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() throws SQLException {
        Connection connection =  Util.getConnection();
        String create = "CREATE TABLE IF NOT EXISTS USER (ID INTEGER NOT NULL AUTO_INCREMENT, NAME VARCHAR(20), LAST_NAME VARCHAR(20), AGE INTEGER, PRIMARY KEY (ID));";
        method(connection, create);
    }

    public void dropUsersTable() throws SQLException {
        Connection connection = Util.getConnection();
        String dell = "DROP TABLE database.user;";
        method(connection, dell);
    }

    public void saveUser(String name, String lastName, byte age) throws SQLException {
        Connection connection = Util.getConnection();
        User user = new User(name, lastName, age);
        PreparedStatement preparedStatement = null;
        String add = "INSERT user(NAME, LAST_NAME, AGE) VALUES (?, ?, ?)";

        try {
            preparedStatement = connection.prepareStatement(add);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setLong(3, user.getAge());
            preparedStatement.executeUpdate();
            System.out.println("User с именем – " + user.getName() + " добавлен в базу данных");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            if (preparedStatement != null){
                preparedStatement.close();
            }
            if (connection != null){
                connection.close();
            }
        }
    }

    public void removeUserById(long id) throws SQLException {
        Connection connection = Util.getConnection();
        User user = new User();
        PreparedStatement preparedStatement = null;
        String remove = " DELETE FROM database.user WHERE ID = ?;";

        try {
            preparedStatement = connection.prepareStatement(remove);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            System.out.println("User с именем – " + user.getName() + " удален из базы");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            if (preparedStatement != null){
                preparedStatement.close();
            }
            if (connection != null){
                connection.close();
            }
        }
    }

    public List<User> getAllUsers() throws SQLException {
        Connection connection = Util.getConnection();
        List<User> list = new ArrayList<>();
        String getUsers = "SELECT ID, NAME, LAST_NAME, AGE FROM database.user";
        Statement statement = null;

        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(getUsers);
            while (resultSet.next()){
                User user = new User();
                user.setId(resultSet.getLong("ID"));
                user.setName(resultSet.getString("NAME"));
                user.setLastName(resultSet.getString("LAST_NAME"));
                user.setAge(resultSet.getByte("AGE"));
                list.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null){
                statement.close();
            }
            if (connection != null){
                connection.close();
            }
        }
        return list;
    }

    public void cleanUsersTable() throws SQLException {
        Connection connection = Util.getConnection();
        String clean = "TRUNCATE TABLE database.user;";
        method(connection, clean);
    }

    private void method(Connection connection, String request) throws SQLException {
        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.executeUpdate(request);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (connection != null){
                connection.close();
            }
        }
    }
}
