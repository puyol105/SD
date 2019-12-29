package Servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    
    public final static int SOCKET_PORT = 12345;

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(SOCKET_PORT);
        SoundCloud sc =  new SoundCloud();

        while(true) {
            Socket clSock;

            clSock = serverSocket.accept();

            Worker worker = new Worker(sc,clSock);
            Thread thread = new Thread(worker);

            thread.start();
        }
    }
}

