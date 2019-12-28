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
                    +  "create [initialMoney]\n"
                    +  "login [id]\n"
                    +  "logout\n"
                    +  "close\n"
                    +  "exit\n";
        }

        if (parsedmsg[0].toLowerCase().equals("create") && parsedmsg.length > 1){
            /*
            int id = bank.createAccount(Float.parseFloat(parsedmsg[1]));
            response = "Account created. ID: " + id + "\nType \"login [ID]\" to login.\n";
            */
        }

        if (parsedmsg[0].toLowerCase().equals("login") && parsedmsg.length == 2){
            /*
            try {
                bank.check(id);
                id = Integer.parseInt(parsedmsg[1]);
                response = "Logged in to: " + parsedmsg[1] + ".\n";
            } catch (InvalidAccount invalidAccount) {
                id = -1;
                response = invalidAccount.getErrorString();
            }
            */
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
