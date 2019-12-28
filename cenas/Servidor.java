import java.io.*;
import java.net.*;
import java.util.ResourceBundle;
import java.util.concurrent.locks.*;

public class Servidor{

    static public void main (String[] args){
        ServerSocket s;
        Socket c = null;
        int i=0;
        //GestorLeiloes g = new GestorLeiloes(); -- Mudar para o nosso gestor de user e musicas (Local onde vai ter os métodos)
        ReentrantLock lock = new ReentrantLock();
        
        try{
            s = new ServerSocket(8080);
            
            while((c=s.accept()) != null){ 
                BufferedReader read_socket = new BufferedReader(new InputStreamReader(c.getInputStream()));
                PrintWriter write_socket = new PrintWriter(c.getOutputStream(),true);
                
                Condition cond = lock.newCondition();
                MensagemServidor ms = new MensagemServidor(cond,lock);
                
                ThreadServidorRead tsr = new ThreadServidorRead(read_socket,g,ms);
                ThreadServidorWrite tsw = new ThreadServidorWrite(write_socket,ms);
                tsr.start();
                tsw.start();
            }
            s.close();
        }
        catch(IOException e){
            System.out.println(e.getMessage()); 
        }
    }
}