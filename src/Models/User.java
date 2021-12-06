/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import java.io.Serializable;

/**
 *
 * @author iahk1510
 */
public class User implements Serializable{
    private int id=0, win=0, lose=0, status=0;
    private String username = null, fullname=null, password =  null;

    public User(int id, String username, String fullname, String password) {
        this.id = id;
        this.username = username;
        this.fullname = fullname;
        this.password = password;
    }

    public User(int id, String fullname) {
        this.id = id;
        this.fullname = fullname;
    }

    public User(String username, String password) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public int getWin() {
        return win;
    }

    public int getLose() {
        return lose;
    }

    public int getStatus() {
        return status;
    }

    public String getUsername() {
        return username;
    }

    public String getFullname() {
        return fullname;
    }

    public String getPassword() {
        return password;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setWin(int win) {
        this.win = win;
    }

    public void setLose(int lose) {
        this.lose = lose;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    
    
    
    
}
