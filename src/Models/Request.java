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
public class Request implements Serializable{
    private int type;
    private Object data;

    public Request(int type, Object data) {
        this.type = type;
        this.data = data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Object getData() {
        return data;
    }

    public int getType() {
        return type;
    }
    
    
}
