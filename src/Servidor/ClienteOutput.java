package Servidor;

import java.io.*;
import java.util.concurrent.locks.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;

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
                System.out.println("||"+linha.length()+"||");
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
                    System.out.println("yasidbasid");
                    String wtf = "";
                    String aux;
                    int i = 0;
                    while(((aux = read_socket.readLine()) != null)){
                        System.out.println(i);
                        wtf += aux;
                        i++;
                    }

                    System.out.println("sizeeee="+wtf.length());
                
                    byte[] bytes = wtf.getBytes(StandardCharsets.UTF_8);
                    Files.write(Paths.get("./DownloadedMusic/"+"2.mp3"), bytes);
                    
                    /*
                    String new_path = "./DownloadedMusic/"+"1.mp3";
                    File musica = new File(new_path);
                    FileOutputStream fos = new FileOutputStream(musica);
                    fos.write(bytes);
                    fos.flush();
                    fos.close();
                    */
                    menu.setOption(1);
                    this.lock.lock();
                    cond.signal();
                    this.lock.unlock();
                }
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}

