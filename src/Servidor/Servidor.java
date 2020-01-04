package Servidor;

import java.io.*;
import java.net.*;
import java.util.concurrent.locks.*;

public class Servidor {
    public final static int SOCKET_PORT = 12345;

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(SOCKET_PORT);
        SoundCloud sc =  new SoundCloud();
        Socket clSock = null;
        ReentrantLock lock = new ReentrantLock();

        System.out.println("Ola sou um servidor muito lindo");
        try{
            while((clSock = serverSocket.accept()) != null){
                System.out.println("Ola sou uma pessoa");
                
                Condition cond = lock.newCondition();
                ServerMessage sm = new ServerMessage(cond,lock);

                ServerRead server_read = new ServerRead(clSock,sc,sm);
                ServerWrite server_write = new ServerWrite(clSock,sm);
                Thread t_read = new Thread(server_read);
                Thread t_write = new Thread(server_write);
                t_read.start();
                t_write.start();
            }
            serverSocket.close();
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
}

