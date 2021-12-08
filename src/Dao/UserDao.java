/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import Models.User;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author iahk1510
 */
public class UserDao extends DAO{

    public UserDao() throws ClassNotFoundException, SQLException {
        super();
    }
    
    public User checkuser(User user) throws SQLException{
        String sql = "select * from tbluser where username = ? and password = ? and status = 0";
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
        String sql = "update tbluser set status = 1 where id = ?";
        PreparedStatement st = con.prepareStatement(sql);
        st.setInt(1, user.getId());
        st.execute();
    }
    
    public boolean logout(User user){
        try {
            String sql = "update tbluser set status = 0 where id = ? ";
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, user.getId());
            st.execute();
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }
    
}
