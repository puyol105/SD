package Servidor;

import java.io.*;
import java.net.*;
import java.util.ResourceBundle;
import java.util.concurrent.locks.*;

public class ServerWrite implements Runnable{
    private PrintWriter write_socket;
    private Condition c;
    private ServerMessage sm;
    private ReentrantLock lock;

    public ServerWrite(PrintWriter write_socket, ServerMessage sm){
        this.write_socket = write_socket;
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
                this.write_socket.println(linha);
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }

        this.lock.unlock();
    }
}

