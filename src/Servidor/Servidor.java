package Servidor;

import java.io.*;
import java.net.*;
import java.util.ResourceBundle;
import java.util.concurrent.locks.*;

public class Servidor {
    public final static int SOCKET_PORT = 12345;

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(SOCKET_PORT);
        SoundCloud sc =  new SoundCloud();
        Socket clSock = null;
        ReentrantLock lock = new ReentrantLock();

        try{
            while((clSock = serverSocket.accept()) != null){
                BufferedReader read_socket = new BufferedReader(new InputStreamReader(clSock.getInputStream()));
                PrintWriter write_socket = new PrintWriter(clSock.getOutputStream(),true);

                Condition cond = lock.newCondition();
                ServerMessage sm = new ServerMessage(cond,lock);

                ServerRead server_read = new ServerRead(read_socket,sc,sm);
                ServerWrite server_write = new ServerWrite(write_socket,sm);
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

