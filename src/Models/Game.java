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
public class Game implements Serializable{
    private int id = 0, challengeId = 0, status = 0, winner = 0;

    public Game(int id, int challengeId, int status, int winner) {
        this.id = id;
        this.challengeId = challengeId;
        this.status = status;
        this.winner = winner;
    }

    public int getId() {
        return id;
    }

    public int getChallengeId() {
        return challengeId;
    }

    public int getStatus() {
        return status;
    }

    public int getWinner() {
        return winner;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setChallengeId(int challengeId) {
        this.challengeId = challengeId;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setWinner(int winner) {
        this.winner = winner;
    }
    
    
    

    
    
}
