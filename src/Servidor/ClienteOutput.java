package Servidor;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.locks.*;

public class ClienteOutput implements Runnable {
    private Socket socket;
    private BufferedReader read_socket;
    private Menu menu;
    private ReentrantLock lock;
    private Condition cond;

    public ClienteOutput(Socket clSock, Menu menu, ReentrantLock l, Condition c) {
        this.socket = clSock;
        try {
            this.read_socket = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.menu = menu;
        this.lock=l;
        this.cond=c;
    }

    public void run(){
        try{
            String linha;
            while((linha = read_socket.readLine())!=null){
                if(linha.equals("Logged in.")){
                    System.out.println("||"+linha+"||");
                    menu.setOption(1);
                    this.lock.lock();
                    cond.signal();
                    this.lock.unlock();
                }
                else if(linha.equals("Logged out.") || linha.equals("Invalid Username.") || linha.equals("Incorrect Password.")){
                    System.out.println("||"+linha+"||");
                    menu.setOption(0);
                    this.lock.lock();
                    cond.signal();
                    this.lock.unlock();
                }
                else if(linha.contains("Downloading.")){
                    System.out.println("||"+linha+"||");
                    //TIRAR O FILESIZE
                    String aux = linha;
                    String[] sep_aux = aux.split(" ");
                    int filesize = Integer.parseInt(sep_aux[1]);
                    String nome = sep_aux[2];

                    DataInputStream dis = new DataInputStream(socket.getInputStream());
                    FileOutputStream fos = new FileOutputStream("DownloadedMusic/" + nome+ ".mp3");
                    byte[] buffer = new byte[4096];
                    
                    int read = 0;
                    int remaining = filesize;
                    while((read = dis.read(buffer, 0, Math.min(buffer.length, remaining))) > 0) {
                        remaining -= read;
                        fos.write(buffer, 0, read);
                    }

                    // dis.reset();
                    fos.flush();
                    fos.close();
                    menu.setOption(1);

                    this.lock.lock();
                    cond.signal();
                    this.lock.unlock();
                }
                else if(linha.contains("Error")){
                    System.out.println("||"+linha+"||");
                    if(linha.contains("username") || linha.contains("password")){
                        menu.setOption(0);
                    }
                    else{
                        menu.setOption(1);
                    }
                    this.lock.lock();
                    cond.signal();
                    this.lock.unlock();
                }
                else if(linha.contains("ID") || linha.contains("Search")){
                    System.out.println("||"+linha+"||");
                }
                //read_socket.reset();
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public BufferedReader getReadSocket(){
        return this.read_socket;
    }
}
