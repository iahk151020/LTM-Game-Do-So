/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import Models.Challenge;
import java.sql.*;

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
    
}
