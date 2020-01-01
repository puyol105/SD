package Servidor;

import java.lang.Thread;
import java.io.*;
import java.net.*;

public class ServerRead implements Runnable{
    private BufferedReader read_socket;
    private SoundCloud sc;
    private Utilizador user;
    private ServerMessage sm;

    public ServerRead(BufferedReader r, SoundCloud s, ServerMessage m){
        this.read_socket = r;
        this.sc = s;
        this.sm = m;
        this.user = null;
    }

    public void run(){
        try{
            String input;
            while((input = read_socket.readLine()) != null){
                if(input.equals("login")){
                    String username = read_socket.readLine();
                    String pass = read_socket.readLine();

                    try{
                        this.user = sc.login(username,pass,sm);
                        sm.setMessage("Logged in.",null);
                    }
                    catch(Exception e){
                        sm.setMessage(e.getMessage(),null);
                    }
                }
                else if(input.equals("create_user")){
                    String username = read_socket.readLine();
                    String pass = read_socket.readLine();
                    String name = read_socket.readLine();
                    try{
                        sc.createUser(username,pass,name,sm);
                        sm.setMessage("Created new user.",null);
                    }
                    catch(Exception e){
                        sm.setMessage(e.getMessage(),null);
                    }
                }
                else if(input.equals("logout")){
                    this.user = null;
                    sm.setMessage("Logged out.",null);
                }
            }
            read_socket.close();
            sm.setMessage("Exit.",null);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}

