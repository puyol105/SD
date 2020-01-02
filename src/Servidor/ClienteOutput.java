package Servidor;

import java.lang.Thread;
import java.io.*;
import java.net.*;
import java.util.ResourceBundle;
import java.util.concurrent.locks.*;

public class ClienteOutput implements Runnable{
    private BufferedReader read_socket;
    private Menu menu;
    private ReentrantLock lock;
    private Condition cond;

    public ClienteOutput(BufferedReader read_socket, Menu menu, ReentrantLock l, Condition c){
        this.read_socket = read_socket;
        this.menu = menu;
        this.lock=l;
        this.cond=c;
    }

    public void run(){
        try{
            String linha;
            while((linha = read_socket.readLine())!=null){
                if(linha.equals("Logged in.")){
                    menu.setOption(1);
                    this.lock.lock();
                    cond.signal();
                    this.lock.unlock();
                }
                else if(linha.equals("Logged out.") || linha.equals("Invalid Username.") || linha.equals("Incorrect Password.")){
                    menu.setOption(0);
                    this.lock.lock();
                    cond.signal();
                    this.lock.unlock();
                }
                else if(linha.contains("Downloaded.")){
                    String[] separated = linha.split("____");
                    System.out.println("size = "+separated.length);
                    byte[] bytes = separated[1].getBytes("UTF-8");
                    String new_path = "./DownloadedMusic/"+"1.mp3";
                    File musica = new File(new_path);
                    FileOutputStream fos = new FileOutputStream(musica);
                    fos.write(bytes);
                    fos.flush();
                    fos.close();
                    menu.setOption(1);
                    this.lock.lock();
                    cond.signal();
                    this.lock.unlock();
                }
                System.out.println("\n"+linha+"\n");
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}

