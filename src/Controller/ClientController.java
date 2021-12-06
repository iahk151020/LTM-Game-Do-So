/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Application.Client;
import Models.User;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import Models.*;
import View.Home;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectOutputStream;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author iahk1510
 */
public class ClientController {
    private User user = null;
    private Home homeView = null;
    private Socket socket;
    private Response res = null;
    private Request req = null;
    private ReadThread rt;
    private WriteThread wt;
    public ClientController(Home homeView) throws IOException {
        this.homeView = homeView;
        homeView.addListener(new Login()); //add Listener vào View
        socket = new Socket("localhost", 9090);
        rt = new ReadThread(socket);
        wt = new WriteThread(socket);
        rt.start();
        wt.start();
        wt.suspend();
    }
    
    class ReadThread extends Thread{
        
        protected Socket socket;
        
        public ReadThread(Socket socket) {
            this.socket = socket;
        }
        
        @Override
        public void run(){
            try {
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                while (true){
                    res = (Response)ois.readObject();
                }
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ClientController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
    }

    class WriteThread extends Thread{

        protected Socket socket;
        public WriteThread(Socket soket) {
            this.socket = socket;
        }
        
        @Override
        public void run(){
            ObjectOutputStream oos = null;
            try {
                oos = new ObjectOutputStream(socket.getOutputStream());
                while (true){
                    oos.writeObject(req);
                    this.suspend();
                }
            } catch (IOException ex) {
                Logger.getLogger(ClientController.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    oos.close();
                } catch (IOException ex) {
                    Logger.getLogger(ClientController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
    }
    
    class Login implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            try {
                User userInfo = homeView.getLogin();
                req = new Request(1, userInfo);
                wt.resume();
                Thread.sleep(1000); //Nếu gửi request để nhận dữ liệu thì gọi Thread.sleep(1000) để nó chờ lấy dữ liệu trong 1s
                if (res.getData() != null){ //Kiểm tra dữ liệu xem có khác null 
                    user = (User)res.getData();
                    homeView.nextUI("main"); //chuuyển qua giao diện MAIN sau khi login thành công
                } else {
                    JOptionPane.showMessageDialog(homeView, "Thông tin đăng nhập không chính xác");
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(ClientController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    //Set request type và response type theo số thứ tự của Listner
    
    //Get và Set dữ liệu ở trên giao diện thì tạo methods ở View.Home rồi gọi trong Listner;

    //2.Logout Listener
    
    //3.Register Listener
    
    //4.GetOnlinePlayer Listener
    class GetOnlinePlayer implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            try {
                req = new Request(4, null);
                wt.resume();
                Thread.sleep(1000);
                List<User> onlinePlayers = (List<User>)res.getData();
                homeView.setOnlinePlayer(onlinePlayers);
                homeView.nextUI("challenge");
            } catch (InterruptedException ex) {
                Logger.getLogger(ClientController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    //5.SendChallenge Listener
    
    //6.GetSentChallenge Listener
    
    //7.JoinGame listener;
    
    //8.GetChallengeList lisnter;
    
    //9.Accept listener
    
    //10.Refuse listener
    
    //11.GetRank listener
    
    //12.Submit listener
    
}
