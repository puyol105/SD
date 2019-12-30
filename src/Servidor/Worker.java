package Servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Worker implements Runnable {
    private Socket clSock;
    private SoundCloud sc;
    private int id;
    private Utilizador loggedUser;

    public Worker(SoundCloud sc, Socket clSock) {
        this.sc = sc;
        this.clSock = clSock;
        this.id = -1;
        this.loggedUser = null;
    }

    public String checkMessage(String msg) {
        String response = "Invalid command\n";
        String[] parsedmsg = msg.split(" ");

        if (msg.toLowerCase().equals("help")){
            response = "List of commands:\n"
                     + "create [username] [password] [nome]\n"
                     + "login [username] [password]\n"
                     + "search [nome/artista/ano]\n"
                     + "download [id]\n"
                     + "upload [path]\n"
                     + "logout\n"
                     + "close\n"
                     + "exit\n";
        }

        if (parsedmsg[0].toLowerCase().equals("create") && parsedmsg.length == 4){
            String username = parsedmsg[1];
            String password = parsedmsg[2];
            String nome = parsedmsg[3];

            boolean r = sc.createUser(username, password, nome);
            response = r ? "User created.\nUsername: \"" + username +"\". Password: \"" + password + "\". Nome: \""+ nome+"\".\n" : "Failed!\n";
        }

        if (parsedmsg[0].toLowerCase().equals("login") && parsedmsg.length == 3){
            try {
                loggedUser = sc.login(parsedmsg[1], parsedmsg[2]);
                response = "Logged in to: " + loggedUser.toString() + ".\n";
            } catch (UsernameInexistenteException | PasswordIncorretaException e) {
                response = e.toString();
            }
        }

        if (parsedmsg[0].toLowerCase().equals("search") && parsedmsg.length == 2){
            ArrayList<Ficheiro> lista = sc.search(parsedmsg[1]);
            response = "";

            for(Ficheiro f : lista){
                response = response + f.toString() + "\n";
            }
        }

        if (parsedmsg[0].toLowerCase().equals("download") && parsedmsg.length == 2){

        }

        if (parsedmsg[0].toLowerCase().equals("upload") && parsedmsg.length == 2){
            String path = parsedmsg[1];
            System.out.println("Path =\""+path+"\".");
        }

        if (parsedmsg[0].toLowerCase().equals("logout") && parsedmsg.length == 1){
            this.loggedUser = null;
        }

        if (parsedmsg[0].toLowerCase().equals("close") && parsedmsg.length == 2){
            /*
            try {
                bank.closeAccount(id);
                id = -1;
                response = "Closed account: " + parsedmsg[1] + ".\n";
            } catch (InvalidAccount invalidAccount) {
                response = invalidAccount.getErrorString();
            }
            */
        }

        return response;
    }

    public void run() {
        String s;
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(clSock.getInputStream()));
            PrintWriter out = new PrintWriter(clSock.getOutputStream());

            while ((s = in.readLine()) != null) {
                if (id != -1){
                    s = s+" "+id;
                }
                System.out.println("Got a message (\"" + s + "\").");

                if (s.contains("exit")){
                    break;
                }

                String response = checkMessage(s);
                out.print(response);
                out.flush();
            }

            clSock.shutdownOutput();
            clSock.shutdownInput();
            clSock.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

