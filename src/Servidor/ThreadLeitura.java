package Servidor;

import java.lang.Thread;
import java.io.*;
import java.net.*;

public class ThreadLeitura extends Thread{
    private BufferedReader read_socket;
    private SoundCloud sc;
    private Utilizador u;

    public ThreadLeitura(BufferedReader read_socket, SoundCloud sc){
        this.read_socket = read_socket;
        this.sc = sc;
        this.u = null;
    }

    public void run(){
        try{
            String input;
            while((input = read_socket.readLine()) != null){ 

                if(input.equals("login")){
                    String user,pass;
                    user = read_socket.readLine();
                    pass = read_socket.readLine();
                    this.u = sc.login(user,pass);
                    //TODO - Mandar mensagem a dizer que fez login como musico/fan

                } else if(input.equals("signup_musico")){
                    String user,pass,nome;
                    user = read_socket.readLine();
                    pass = read_socket.readLine();
                    nome = read_socket.readLine();
                    sc.createMusico(user, pass, nome);
                    //TODO - Mandar mensagem a dizer que foi registado

                } else if(input.equals("signup_fa")){
                    String user,pass,nome;
                    user = read_socket.readLine();
                    pass = read_socket.readLine();
                    nome = read_socket.readLine();
                    sc.createFan(user, pass, nome);
                    //TODO - Mandar mensagem a dizer que foi registado

                } else if(input.equals("upload")){
                    
                } else if(input.equals("download")){
                    
                } else if(input.equals("search")){
                    
                } else if(input.equals("logout")){
                    this.u = null;
                    //TODO - Mandar mensagem a dizer que fez logout
                    
                }

            }
            read_socket.close();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

}
