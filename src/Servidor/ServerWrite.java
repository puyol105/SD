package Servidor;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.locks.*;

public class ServerWrite implements Runnable {
    private Socket socket;
    private PrintWriter write_socket;
    private Condition c;
    private ServerMessage sm;
    private ReentrantLock lock;

    public ServerWrite(Socket sock, ServerMessage sm) {
        this.socket = sock;

        try {
            this.write_socket = new PrintWriter(this.socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.c = sm.getCondition();
        this.sm = sm;
        this.lock = sm.getLock();
    }

    public void run(){
        this.lock.lock();
        try{
            String linha;

            while(true){
                while((linha = sm.getMessage())==null)
                    c.await();
                if(linha.equals("Exit."))
                    break;
                if(linha.contains("Logged")
                || linha.contains("Created")
                || linha.contains("Uploaded")
                || linha.contains("Download")
                || linha.contains("Search")
                || linha.contains(("ID"))
                || linha.contains(("Error"))){
                    this.write_socket.println(linha);
                }
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }

        this.lock.unlock();
    }
}

