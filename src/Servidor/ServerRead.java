package Servidor;

import java.io.*;
import java.net.Socket;
import java.util.Collections;
import java.util.ArrayList;

public class ServerRead implements Runnable {
    private Socket socket;
    private BufferedReader read_socket;
    private SoundCloud sc;
    private ServerMessage sm;

    public ServerRead(Socket clSock, SoundCloud s, ServerMessage m) {
        this.socket = clSock;
        try {
            this.read_socket = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.sc = s;
        this.sm = m;
    }

    public void run(){
        try{
            String input;
            while((input = read_socket.readLine()) != null){
                if(input.equals("login")){
                    String username = read_socket.readLine();
                    String pass = read_socket.readLine();

                    try{
                        sc.login(username,pass,sm);
                        sm.setMessage("Logged in.",null);
                    }
                    catch(Exception e){
                        sm.setMessage(e.getMessage(),null);
                    }
                }
                else if(input.equals("create_user")){
                    String username = read_socket.readLine();
                    String pass = read_socket.readLine();
                    try{
                        sc.createUser(username,pass,sm);
                        sm.setMessage("Created new user.",null);
                    }
                    catch(Exception e){
                        sm.setMessage(e.getMessage(),null);
                    }
                }
                else if(input.equals("upload")){
                    String title = read_socket.readLine();
                    String artist = read_socket.readLine();
                    String ano = read_socket.readLine();
                    String labels = read_socket.readLine();
                    int filesize = Integer.parseInt(read_socket.readLine()); 

                    ArrayList<String> separated_labels = new ArrayList<>();
                    Collections.addAll(separated_labels, labels.split(" "));
                    Ficheiro f = new Ficheiro(-1, title, artist, separated_labels, Integer.parseInt(ano));
                    f = sc.upload(f);
                    
                    DataInputStream dis = new DataInputStream(socket.getInputStream());
                    FileOutputStream fos = new FileOutputStream("../MusicFiles/" + f.getId() + "_" + title + ".mp3");
                    byte[] buffer = new byte[4096];
                    
                    int read = 0;
                    int remaining = filesize;
                    while((read = dis.read(buffer, 0, Math.min(buffer.length, remaining))) > 0) {
                        remaining -= read;
                        fos.write(buffer, 0, read);
                    }
                    
                    fos.close();                
                    sm.setMessage("Uploaded Music file: "+f.toString(), null);
                }
                else if(input.equals("download")){
                    String id_s = read_socket.readLine();
                    Integer id = Integer.parseInt(id_s);
        
                    try{
                        Ficheiro f = sc.download(id);
                        String path = "../MusicFiles/"+ f.getId() + "_" + f.getNome() + ".mp3";
                        File file = new File(path);
                        sm.setMessage("Downloading. " + file.length() +" " + f.getNome(), null);

                        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                        FileInputStream fis = new FileInputStream(file);
                        byte[] buffer = new byte[4096];
                        
                        while (fis.read(buffer) > 0) {
                            dos.write(buffer);
                        }
                        
                        fis.close();
                    }
                    catch(Exception e){
                        sm.setMessage(e.getMessage(),null);
                    }
                }
                else if(input.equals("search")){
                    String labels = read_socket.readLine();

                    try{
                        ArrayList<Ficheiro> filtered_files = new ArrayList<>();
                        filtered_files = sc.search(labels);
                        ArrayList<String> result = new ArrayList<>();

                        for (Ficheiro f : filtered_files) {
                            result.add(f.toFancyString());
                        }
                        result.add("Search complete.");

                        sm.setMessage("", result);
                    }
                    catch(Exception e){
                        sm.setMessage(e.getMessage(),null);
                    }
                }
                else if(input.equals("logout")){
                    sm.setMessage("Logged out.",null);
                }
            }
            read_socket.close();
            sm.setMessage("Exit.",null);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}

