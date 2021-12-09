/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Dao.ChallengeDao;
import Dao.QuestionDao;
import Dao.UserDao;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import Models.*;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author iahk1510
 */
public class ServerController {
    private ServerSocket serverSocket = null;
    private static int port = 9090;
    private List<User> onlinePlayers;
    private UserDao userDao;
    private ChallengeDao challengeDao;
    private QuestionDao questionDao;
   
    public ServerController() throws IOException, ClassNotFoundException, SQLException {
        onlinePlayers = new ArrayList<User>();
        serverSocket = new ServerSocket(port);
        userDao = new UserDao();
        challengeDao = new ChallengeDao();
        questionDao = new QuestionDao();
        while (true){
            listening();
        }
    }

    private void listening() throws IOException {
        Socket clientSocket = serverSocket.accept();
        new ServerThread(clientSocket).start();
    }
    
    class ServerThread extends Thread{
        
        protected Socket clientSocket;

        protected Request req = null;
        public ServerThread(Socket clientSocket) throws IOException {
            this.clientSocket = clientSocket;
 
        }
        
        @Override
        public void run(){
            ObjectInputStream ois = null;
            try {
                ois = new ObjectInputStream(this.clientSocket.getInputStream());
                ObjectOutputStream oos = new ObjectOutputStream(this.clientSocket.getOutputStream());
                while(true){
                req = (Request)ois.readObject();
                System.out.println("req type :" + req.getType());
                switch (req.getType()){
                    case 1:
                        User userInfo = (User)req.getData();
                        System.out.println("info: " + userInfo.getUsername() + " " + userInfo.getPassword());
                        User user = userDao.checkuser(userInfo);
                        System.out.println(user);
                        userDao.online(user);
                        onlinePlayers.add(user);
                        oos.writeObject(new Response(1, user));
                        oos.flush();
                        break;
                    case 2:
                        User userOut = (User)req.getData();
                        System.out.println("out: " + userOut.getFullname());
                        for(int i=0; i<onlinePlayers.size(); i++)
                            if(onlinePlayers.get(i).getId() == userOut.getId()){
                                onlinePlayers.remove(i);
                            }
                        System.out.println("size: " + onlinePlayers.size());
                        boolean success = userDao.logout(userOut);
                        System.out.println("success: " + success);
                        oos.writeObject(new Response(2, success));
                        oos.flush();
                        break;
                    case 3: 
                        break;
                    case 4:
                        oos.writeObject(new Response(4, onlinePlayers));
                        oos.flush();
                        break;
                    case 5: 
                        Challenge challenge = (Challenge)req.getData();
                        challengeDao.addChallenge(challenge);
                        break;
                    case 6: 
                        int from = (int)req.getData();
                        List<Challenge> sent = challengeDao.getSent(from);
                        oos.writeObject(new Response(6, sent));
                        oos.flush();
                        break;
                    case 7: 
                        break;
                    case 8: 
                        int to = (int)req.getData();
                        List<Challenge> chList = challengeDao.getChallengeList(to);
                        oos.writeObject(new Response(8, chList));
                        oos.flush();
                        break;
                    case 9: 
                        int accept = (int)req.getData();
                        System.out.println("accpt id: " + accept);
                        List<Question> ques = questionDao.generateGame(accept);
                        System.out.println("ques: " + ques);
                        oos.writeObject(new Response(9, ques));
                        oos.flush();
                        break;
                    case 10: 
                        int refuse = (int)req.getData();
                        challengeDao.refuse(refuse);
                        break;
                    case 11: 
                        break;
                    case 12: 
                        break;
                }
               }
            
          
            } catch (IOException ex) {
                Logger.getLogger(ServerController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ServerController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(ServerController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
        }
        
    }
    
    
}
