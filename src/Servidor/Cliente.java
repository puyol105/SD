package Servidor;

import java.io.*;
import java.net.*;
import java.util.concurrent.locks.*;

public class Cliente{
    public final static int SOCKET_PORT = 12345;
    
    public static void main(String[] args){
        Socket clSock = null;
        ReentrantLock lock = new ReentrantLock();
        Condition cond = lock.newCondition();

        try{
            clSock = new Socket("localhost", SOCKET_PORT);

            Menu menu = new Menu();

            ClienteInput client_input = new ClienteInput(clSock,menu,lock, cond);

            ClienteOutput client_output = new ClienteOutput(clSock,menu,lock, cond);
            Thread t_input = new Thread(client_input);
            Thread t_output = new Thread(client_output);

            t_input.start();
            t_output.start();

            t_input.join();
            t_output.join();

            //read_socket.close();

            System.out.println("Exited.\n");
            clSock.close();

        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
        catch(InterruptedException e){
            System.out.println(e.getMessage());
        }
    }
}

