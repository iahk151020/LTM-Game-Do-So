/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

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
   
    public ServerController() throws IOException, ClassNotFoundException, SQLException {
        onlinePlayers = new ArrayList<User>();
        serverSocket = new ServerSocket(port);
        userDao = new UserDao();
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
        
        public ServerThread(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }
        
        @Override
        public void run(){
            ObjectInputStream ois = null;
            try {
                ois = new ObjectInputStream(clientSocket.getInputStream());
                ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
                Request req = (Request)ois.readObject();
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
                        break;
                    case 3: 
                        break;
                    case 4:
                        oos.writeObject(new Response(4, onlinePlayers));
                        oos.flush();
                        break;
                    case 5: 
                        break;
                    case 6: 
                        break;
                    case 7: 
                        break;
                    case 8: 
                        break;
                    case 9: 
                        break;
                    case 10: 
                        break;
                    case 11: 
                        break;
                    case 12: 
                        break;
                }
            
            } catch (IOException ex) {
                Logger.getLogger(ServerController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ServerController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(ServerController.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    ois.close();
                } catch (IOException ex) {
                    Logger.getLogger(ServerController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
    }
    
    
}
