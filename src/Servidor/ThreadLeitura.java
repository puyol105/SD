import java.lang.Thread;
import java.io.*;
import java.net.*;

public class ThreadLeitura extends Thread{
    private BufferedReader read_socket;
    private SoundCloud sc;
    private Utilizador u;

    public void run(){
        try{
            String input;
            while((input = read_socket.readLine()) != null){ 

                if(input.equals("login")){

                } else if(input.equals("signup_musico")){

                } else if(input.equals("signup_fa")){

                } else if(input.equals("upload")){
                    
                } else if(input.equals("download")){
                    
                } else if(input.equals("search")){
                    
                } else if(input.equals("logout")){
                    
                }

            }
            read_socket.close();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

}