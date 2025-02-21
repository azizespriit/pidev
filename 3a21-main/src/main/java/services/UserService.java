package services;

import utils.MyDb;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserService implements Crud<User> {
    Connection conn;

    public UserService() {
        this.conn = MyDb.getInstance().getConn();
    }

    @Override
    public void create(User obj) throws Exception {
        String sql = "insert into user (firstName, lastName, age) values ('" +
                obj.getFirstName() + "','" + obj.getLastName() + "','" +
                obj.getAge() + "')";
        Statement stmt = conn.createStatement();
        stmt.executeUpdate(sql);
    }

    @Override
    public void update(User obj) throws Exception {
        String sql = "update user set firstName = ?,lastName = ?,age = ? where id = ? ";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, obj.getFirstName());
        stmt.setString(2, obj.getLastName());
        stmt.setInt(3, obj.getAge());
        stmt.setInt(4, obj.getId());
        stmt.executeUpdate();
    }

    @Override
    public void delete(User obj) throws Exception {

    }

    @Override
    public List<User> getAll() throws Exception {
        String sql = "select * from user";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        List<User> users = new ArrayList<>();
        while (rs.next()) {
            User user = new User();
            user.setFirstName(rs.getString("firstName"));
            user.setLastName(rs.getString("lastName"));
            user.setAge(rs.getInt("age"));
            users.add(user);
        }
        return users;
    }
}
