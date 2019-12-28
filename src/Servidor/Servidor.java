import java.io.*;
import java.net.*;

public class Servidor{

    static public void main (String[] args){
        ServerSocket s;
        Socket c = null;
        SoundCloud sc;

        try{
            s = new ServerSocket(8080);
            
            while((c=s.accept()) != null){
                BufferedReader read_socket = new BufferedReader(new InputStreamReader(c.getInputStream()));
                PrintWriter write_socket = new PrintWriter(c.getOutputStream(),true);

                
            }
            s.close();
        }
        catch(IOException e){
            System.out.println(e.getMessage()); 
        }

    }
}