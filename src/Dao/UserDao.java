/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import Models.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author iahk1510
 */
public class UserDao extends DAO{

    public UserDao() throws ClassNotFoundException, SQLException {
        super();
    }
    
    public User checkuser(User user) throws SQLException{
        String sql = "select * from user where username = ? and password = ? and status = 0";
        PreparedStatement st = con.prepareStatement(sql);
        st.setString(1, user.getUsername());
        st.setString(2, user.getPassword());
        ResultSet rs = st.executeQuery();
        while (rs.next()){
            user.setFullname(rs.getString("fullname"));
            user.setStatus(0);
            user.setId(rs.getInt("id"));
            return user;
        }
        return null;
    }
    
    public void online(User user) throws SQLException, SQLException{
        String sql = "update user set status = 1 where id = ?";
        PreparedStatement st = con.prepareStatement(sql);
        st.setInt(1, user.getId());
        st.execute();
    }
    
    public boolean resign(User user) throws SQLException, SQLException{
        String sql = "insert into user(username,password,fullname,win,lose,status) values(?,?,?,0,0,0)";
        try{
        PreparedStatement st = con.prepareStatement(sql);
        st.setString(1, user.getUsername());
        st.setString(2, user.getPassword());
        st.setString(3, user.getFullname());
        System.out.print(user);
        st.execute();
        return true;
    }catch(SQLException e){
       System.out.print(e);
       return false;
    }
    }
    
    public boolean logOut(User user) throws SQLException, SQLException{
        String sql = "update user set status = 0 where id = ? ";
        try{
           PreparedStatement st = con.prepareStatement(sql);
        st.setInt(1, user.getId());
        System.out.print(user.getId());
        st.execute();
        return true;
        }catch(SQLException e){
        System.out.print(e);
       return false;
        }
        
    }
      public ArrayList<User> getRank() throws SQLException, SQLException{
          ArrayList<User> userRank= new ArrayList<>();
        String sql = "select fullname,win,lose from user order by win DESC";
        try{
           PreparedStatement st = con.prepareStatement(sql);
        ResultSet rs = st.executeQuery();
         while (rs.next()){
            User s= new User(rs.getString("fullname"),  rs.getInt("win"),rs.getInt("lose"));
           // System.out.print(s);
            userRank.add(s);
        }
         return userRank;
        }catch(SQLException e){
        System.out.print(e);
       return null;
        }
        
    }
}
