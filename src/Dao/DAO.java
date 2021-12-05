/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

//import com.sun.jdi.connect.spi.Connection;
import java.sql.*;
/**
 *
 * @author iahk1510
 */
public class DAO {

    Connection con;
    public DAO() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ltm", "root", "Khai15102@");
    }
    
    
}
