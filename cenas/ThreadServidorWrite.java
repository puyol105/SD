import java.lang.Thread;
import java.io.*;
import java.net.*;
import java.util.ResourceBundle;
import java.util.concurrent.locks.*;

public class ThreadServidorWrite extends Thread{
    private PrintWriter write_socket;
    private Condition c;
    private MensagemServidor ms;
    private ReentrantLock lock;

    public ThreadServidorWrite(PrintWriter write_socket, MensagemServidor ms){
        this.write_socket = write_socket;
        this.c = ms.getCondition();
        this.ms = ms;
        this.lock = ms.getLock();
    }
    
    public void run(){
        this.lock.lock();
        try{
            String linha;
            while(true){
                while((linha = ms.getMsg())==null)
                    c.await();
                if(linha.equals("Sair"))
                    break;
                this.write_socket.println(linha);
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        finally{
            this.lock.unlock();
        }
    }
}