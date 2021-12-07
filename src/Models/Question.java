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
public class Question implements  Serializable{
    private int id = 0, no = 0, value = 0, gameId = 0;

    public Question(int id, int no, int value, int gameId) {
        this.id = id;
        this.no = no;
        this.value = value;
        this.gameId = gameId;
    }

    public int getId() {
        return id;
    }

    public int getNo() {
        return no;
    }

    public int getValue() {
        return value;
    }

    public int getGameId() {
        return gameId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }
       
    
}
