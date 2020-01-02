package Servidor;

import java.lang.Thread;
import java.io.*;
import java.net.*;
import java.util.Collections;
import java.util.ArrayList;

public class ServerRead implements Runnable{
    private BufferedReader read_socket;
    private SoundCloud sc;
    private Utilizador user;
    private ServerMessage sm;

    public ServerRead(BufferedReader r, SoundCloud s, ServerMessage m){
        this.read_socket = r;
        this.sc = s;
        this.sm = m;
        this.user = null;
    }

    public void run(){
        try{
            String input;
            while((input = read_socket.readLine()) != null){
                if(input.equals("login")){
                    String username = read_socket.readLine();
                    String pass = read_socket.readLine();

                    try{
                        this.user = sc.login(username,pass,sm);
                        sm.setMessage("Logged in.",null);
                    }
                    catch(Exception e){
                        sm.setMessage(e.getMessage(),null);
                    }
                }
                else if(input.equals("create_user")){
                    String username = read_socket.readLine();
                    String pass = read_socket.readLine();
                    String name = read_socket.readLine();
                    try{
                        sc.createUser(username,pass,name,sm);
                        sm.setMessage("Created new user.",null);
                    }
                    catch(Exception e){
                        sm.setMessage(e.getMessage(),null);
                    }
                }
                else if(input.equals("upload")){
                    String title = read_socket.readLine();
                    String path = read_socket.readLine();
                    String artist = read_socket.readLine();
                    String ano = read_socket.readLine();
                    String labels = read_socket.readLine();

                    ArrayList<String> separated_labels = new ArrayList<>();
                    Collections.addAll(separated_labels, labels.split(" "));
                    File file = new File(path);

                    FileInputStream fis = new FileInputStream(file);
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    byte[] buf = new byte[10000];
                    int size = 0;
                    try {
                        for (int readNum; (readNum = fis.read(buf)) != -1;) {
                            bos.write(buf, 0, readNum);
                            size += readNum;
                        }
                        System.out.println("Size total: "+size);
                    } catch (IOException ex) {
                        sm.setMessage(ex.getMessage(), null);
                    }

                    byte[] bytes = bos.toByteArray();

                    Ficheiro f = new Ficheiro(-1, title, artist, separated_labels, Integer.parseInt(ano));
                    f = sc.upload(f,bytes);

                    sm.setMessage("Uploaded Music file: "+f.toString(), null);
                }
                else if(input.equals("download")){
                    String id_s = read_socket.readLine();
                    Integer id = Integer.parseInt(id_s);

                    try{
                        byte[] bytes = sc.download(id);

                        sm.setMessage("Downloaded.____"+new String(bytes, "UTF-8"), null);
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
                    this.user = null;
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

