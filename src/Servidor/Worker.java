package Servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Worker implements Runnable {
    private Socket clSock;
    private SoundCloud sc;
    private int id;

    public Worker(SoundCloud sc, Socket clSock) {
        this.sc = sc;
        this.clSock = clSock;
        this.id = -1;
    }

    public String checkMessage(String msg) {
        String response = "Invalid command\n";
        String[] parsedmsg = msg.split(" ");

        if (msg.toLowerCase().equals("help")){
            response = "List of commands:\n"
                    +  "create [musico/fan] [username] [password] [nome]\n"
                    +  "login [username] [password]\n"
                    +  "logout\n"
                    +  "close\n"
                    +  "exit\n";
        }

        if (parsedmsg[0].toLowerCase().equals("create") && parsedmsg.length > 1){
            String musico_or_fan = parsedmsg[1];
            String username = parsedmsg[2];
            String password = parsedmsg[3];
            String nome = parsedmsg[4];
            boolean isMusico = parsedmsg[1].equals("musico");

            boolean r = sc.createUser(username, password, nome, isMusico);
            response = r ? "User created. Type: " + musico_or_fan + ".\nUsername: \"" + username +"\". Password: \"" + password + "\".\nNome: \""+ nome+"\".\n" : "Failed!\n";
        }

        if (parsedmsg[0].toLowerCase().equals("login") && parsedmsg.length == 3){
            try {
                Utilizador u = sc.login(parsedmsg[1], parsedmsg[2]);
                response = "Logged in to: " + u.toString() + ".\n";
            } catch (UsernameInexistenteException | PasswordIncorretaException e) {
                response = e.toString();
            }
        }

        if (parsedmsg[0].toLowerCase().equals("logout") && parsedmsg.length == 2){
            /*
            id = -1;
            response = "Successfully logged out. Please log in into another account or create new account.\n";
            */
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

