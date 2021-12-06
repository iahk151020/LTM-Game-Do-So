/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Application;

import Controller.ClientController;
import Models.User;
import View.Home;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author iahk1510
 */
public class Client {

    public static void main(String[] args) throws IOException {
        Home homeV = new Home();
        ClientController clientController = new ClientController(homeV);
        homeV.setVisible(true);
    }
    
}
