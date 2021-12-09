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
    public Response res = null;
    public Request req = null;
    private ReadThread rt;
    private WriteThread wt;
   
    public ClientController(Home homeView) throws IOException {
        this.homeView = homeView;
        homeView.addListener(new Login(), new Logout(), new GetOnlinePlayer(), new SendChallenge()
        , new GetSentChallenge(), new GetChallengeList(), new Refuse(), new Accept()); //add Listener vào View
        socket = new Socket("localhost", 9090);
        rt = new ReadThread(socket);
        wt = new WriteThread(socket);
        rt.start();
        wt.start();
        wt.suspend();
    }
    
//    public void sendData(Object data) throws IOException{
//        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
//        oos.writeObject(data);
//    }
    
    class ReadThread extends Thread{
        
        private Socket clientSocket;
        
        public ReadThread(Socket csocket) {
            this.clientSocket = csocket;
        }
        
        @Override
        public void run(){
            ObjectInputStream ois = null;
            try {
                ois = new ObjectInputStream(this.clientSocket.getInputStream());
                while (true){
                    
                    res = (Response)ois.readObject();
                    System.out.println("res type: " + res.getType());
                }
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ClientController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
    }
    
    class WriteThread extends Thread{
        private Socket clientSocket;

        public WriteThread(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }
         @Override
         public void run(){
             ObjectOutputStream oos = null;
            try {
                oos = new ObjectOutputStream(this.clientSocket.getOutputStream());
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
//                sendData(req);
                wt.resume();
                Thread.sleep(1000); //Nếu gửi request để nhận dữ liệu thì gọi Thread.sleep(1000) để nó chờ lấy dữ liệu trong 1s
                if (res.getData() != null){ //Kiểm tra dữ liệu xem có khác null 
                    user = (User)res.getData();
                    homeView.nextUI("main"); //chuuyển qua giao diện MAIN sau khi login thành công
                    res = null;
                    req = null;
                    
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
    class Logout implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            try {
                req = new Request(2, user);
                wt.resume();
//                sendData(req);
                Thread.sleep(1000);
               
                if ((boolean)res.getData()){
                    homeView.nextUI("login");
                    req = null;
                    res = null;
                    user = null;
                } else {
                    JOptionPane.showMessageDialog(homeView, "LOGOUT ERROR");
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(ClientController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
    }
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
                req = null;
            } catch (InterruptedException ex) {
                Logger.getLogger(ClientController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    //5.SendChallenge Listener
    
    class SendChallenge implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
           int to = homeView.getTo();
           int from = user.getId();
           System.out.println("f: " + from + " " + to);
           req = new Request(5, new Challenge(from, to));
           wt.resume();
          
        }
        
    }
    
    //6.GetSentChallenge Listener
    class GetSentChallenge implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            try {
                int from = user.getId();
                req = new Request(6, from);
                wt.resume();
                Thread.sleep(1000);
                List<Challenge> sent = (List<Challenge>)res.getData();
                homeView.setSent(sent);
                homeView.nextUI("sent");
            } catch (InterruptedException ex) {
                Logger.getLogger(ClientController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    //7.JoinGame listener;
    
    //8.GetChallengeList lisnter;
    class GetChallengeList implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            try {
                int to = user.getId();
                req = new Request(8, to);
                wt.resume();
                Thread.sleep(1000);
                List<Challenge> chList = (List<Challenge>)res.getData();
                homeView.setChallengeList(chList);
                homeView.nextUI("request");
            } catch (InterruptedException ex) {
                Logger.getLogger(ClientController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    //9.Accept listener
    class Accept implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            try {
                int accept = homeView.getAccept();
                req = new Request(9, accept);
                wt.resume();
                Thread.sleep(1000);
                List<Question> ques = (List<Question>)res.getData();
                homeView.setQues(ques);
                homeView.removeAccept();
                homeView.nextUI("gameroom");
            } catch (InterruptedException ex) {
                Logger.getLogger(ClientController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    //10.Refuse listener
     class Refuse implements ActionListener{
        

        @Override
        public void actionPerformed(ActionEvent ae) {
            try {
                int challengeId = homeView.getRefuse();
                req = new Request(10, challengeId);
                wt.resume();
                Thread.sleep(1000);
                homeView.removeRefuse();
            } catch (InterruptedException ex) {
                Logger.getLogger(ClientController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
     }
    //11.GetRank listener
    
    //12.Submit listener
    
}
