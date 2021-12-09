/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import Models.Challenge;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author iahk1510
 */
public class ChallengeDao extends  DAO{

    public ChallengeDao() throws ClassNotFoundException, SQLException {
        super();
    }
    
    public void addChallenge(Challenge ch) throws SQLException{
        
        String sql1 = "select count(id) as current from tblchallenge";
        PreparedStatement st = con.prepareStatement(sql1);
        ResultSet rs = st.executeQuery();
        int current = 0;
        while (rs.next()){
            current = rs.getInt("current") + 1;
        }
        
        String sql = "insert into tblchallenge values (?,?,?,?)";
        st = con.prepareStatement(sql);
        st.setInt(1, current);
        st.setInt(2, ch.getFrom());
        st.setInt(3, ch.getTo());
        st.setInt(4, 0);
        st.execute();
    }

    public List<Challenge> getSent(int from) throws SQLException {
        List<Challenge> sent = new ArrayList<Challenge>();
        String sql = "select * from tblchallenge where fromm = ?";
        PreparedStatement st = con.prepareStatement(sql);
        st.setInt(1, from);
        ResultSet rs = st.executeQuery();
        while (rs.next()){
            sent.add(new Challenge(rs.getInt("id"), from, rs.getInt("too"), rs.getInt("status")));
        }
        return sent;
    }

    public List<Challenge> getChallengeList(int to) throws SQLException {
        List<Challenge> chList = new ArrayList<Challenge>();
        String sql = "select * from tblchallenge where too = ? and status = 0";
        PreparedStatement st = con.prepareStatement(sql);
        st.setInt(1, to);
        ResultSet rs = st.executeQuery();
        while (rs.next()){
            chList.add(new Challenge(rs.getInt("id"),  rs.getInt("fromm"), to,  rs.getInt("status")));
        }
        return chList;
    }

    public void refuse(int refuse) throws SQLException {
        String sql = "update tblchallenge set status = 2 where id = ?";
        PreparedStatement st = con.prepareStatement(sql);
        st.setInt(1, refuse);
        st.execute();
    }
    
}
