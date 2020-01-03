package Servidor;

import java.io.*;
import java.util.concurrent.locks.*;

public class ServerWrite implements Runnable{
    private BufferedOutputStream file_socket;
    private OutputStream socket;
    private Condition c;
    private ServerMessage sm;
    private ReentrantLock lock;

    public ServerWrite(OutputStream socket, ServerMessage sm){
        this.socket = socket;
        this.file_socket = new BufferedOutputStream(socket);
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

                //TODO: mudar tudo para escrever bytes. converter strings antes de mandar

                if(linha.equals("DOWNLOAD-FILE")){
                    byte [] file = sm.getDownload();
                    String fim = "Acabou";
                    byte[] bytes = linha.toByteArray() + file + fim.toByteArray();
                    //mandar o fichiero aos pedaços
                    this.file_socket.write(bytes,0, bytes.length);
                }else{
                    byte [] bytes = linha.toByteArray(); 
                    this.file_socket.write(bytes,0,bytes.length); 
                }
                
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }

        this.lock.unlock();
    }
}

