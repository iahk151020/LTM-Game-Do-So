/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import Models.Question;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author iahk1510
 */
public class QuestionDao extends DAO{

    public QuestionDao() throws ClassNotFoundException, SQLException {
        super();
    }
    
    public List<Question> generateQues(int gameId) throws SQLException{
        String sql = "select count(id) as current from tablequestion";
        PreparedStatement st = con.prepareStatement(sql);
        ResultSet rs = st.executeQuery();
        int current = 0 ;
        while (rs.next()){
            current = rs.getInt("current");
        }
        List<Question> ques = new ArrayList<Question>();
        List<Integer> arr = new ArrayList<Integer>();
        for(int i=1; i<=9; i++){
            arr.add(i);
        }
        Collections.shuffle(arr);
        for(int i=0; i<arr.size(); i++){
            ques.add(new Question(current++, i+1, arr.get(i), gameId));
            sql = "insert into tablequestion values(?,?,?,?)";
            st = con.prepareStatement(sql);
            st.setInt(1, current);
            st.setInt(2, i+1);
            st.setInt(3, arr.get(i));
            st.setInt(4, gameId);
            st.execute();
        }
        return ques;
        
    }
    
    public List<Question> generateGame(int accept) throws SQLException{
        String sql = "select count(id) as current from tblgame";
        PreparedStatement st = con.prepareStatement(sql);
        ResultSet rs = st.executeQuery();
        int current = 0 ;
        while (rs.next()){
            current = rs.getInt("current");
        }
        current ++;
        sql = "insert into tblgame values(?,?,?,?)";
        st = con.prepareStatement(sql);
        st.setInt(1, current);
        st.setInt(2, 0);
        st.setInt(3, 0);
        st.setInt(4, accept);
        st.execute();
        return generateQues(current);
                
    }
    
}
