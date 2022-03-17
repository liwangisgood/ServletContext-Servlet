package com.example.dao;

import com.example.entity.Users;
import com.example.util.JdbcUtil;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 2020/4/27
 */
public class UserDao {

    private JdbcUtil util = new JdbcUtil();
    //用户注册
    public int add(Users user){
        String sql ="insert into users(userName,password,sex,email)" +
                    " values(?,?,?,?)";
        PreparedStatement ps = util.createStatement(sql);
        int result = 0;
        try {
            ps.setString(1, user.getUserName());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getSex());
            ps.setString(4, user.getEmail());
            result = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            util.close();
        }
        return result;
    }
    //查询所有用户信息
    public List findAll(){
        String sql ="select * from users";
        PreparedStatement ps = util.createStatement(sql);
        ResultSet rs = null;
        List userList = new ArrayList();
        try {
            rs = ps.executeQuery();
            while(rs.next()){
                Integer userId = rs.getInt("userId");
                String  userName =rs.getString("userName");
                String  password = rs.getString("password");
                String  sex = rs.getString("sex");
                String  email =rs.getString("email");
                Users users = new Users(userId, userName, password, sex, email);
                userList.add(users);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            util.close(rs);
        }
        return userList;
    }
    //根据用户编号删除用户信息
    public int delete(String userId){
        String sql="delete from users where userId=?";
        PreparedStatement ps = util.createStatement(sql);
        int result = 0;
        try {
            ps.setInt(1, Integer.valueOf(userId));
            result = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            util.close();
        }
        return result;
    }
    //登录验证
    public int login(String id,String name){
        String sql="select count(*) from users where id=? and password=name";
        PreparedStatement ps = util.createStatement(sql);
        ResultSet rs = null;
        int result = 0;
        try {
            ps.setString(1, id);
            ps.setString(2, name);
            rs = ps.executeQuery();
            while(rs.next()){
               result =  rs.getInt("count(*)");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            util.close(rs);
        }
        return result;
    }
}
