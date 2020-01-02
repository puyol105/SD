package Servidor;

import java.io.*;
import java.net.*;
import java.util.ResourceBundle;
import java.util.concurrent.locks.*;
import java.nio.file.*;

public class ClienteInput implements Runnable{
    private BufferedReader cl_input;
    private PrintWriter write_socket;
    private Socket socket;
    private Menu menu;
    private ReentrantLock lock;
    private Condition c;

    public ClienteInput(Socket socket, Menu menu, ReentrantLock l, Condition c){
        try{
            this.cl_input = new BufferedReader(new InputStreamReader(System.in));
            this.write_socket = new PrintWriter(socket.getOutputStream(),true);
            this.socket = socket;
            this.menu = menu;
            this.lock = l;
            this.c = c;
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    public void run(){
        String input = null;

        try{
            menu.show();
            while((input = cl_input.readLine())!= null){
                if(menu.getOption() == 0){
                    if(input.equals("1")){
                        write_socket.println("login");
                        System.out.print("Username: ");
                        input = cl_input.readLine();
                        write_socket.println(input);

                        System.out.print("Password: ");
                        input = cl_input.readLine();
                        write_socket.println(input);

                        this.lock.lock();
                        c.await();
                        this.lock.unlock();
                        input="1";
                    }
                    else if(input.equals("2")){
                        write_socket.println("create_user");
                        System.out.print("Username: ");
                        input = cl_input.readLine();
                        write_socket.println(input);

                        System.out.print("Password: ");
                        input = cl_input.readLine();
                        write_socket.println(input);

                        System.out.print("Name: ");
                        input = cl_input.readLine();
                        write_socket.println(input);
                        input="2";
                    }
                    else if(input.equals("0")){
                        break;
                    }
                    if(input.equals("1") || input.equals("2") || input.equals("0")){
                        menu.show();
                    }
                    else System.out.println("Invalid option.");
                }
                else if(menu.getOption() == 1){
                    if(input.equals("1")){
                        write_socket.println("upload");
                        System.out.print("Title: ");
                        input = cl_input.readLine();
                        write_socket.println(input);

                        System.out.print("Path to file: ");
                        input = cl_input.readLine();
                        write_socket.println(input);

                        System.out.print("Artist/Band: ");
                        input = cl_input.readLine();
                        write_socket.println(input);

                        System.out.print("Year of release: ");
                        input = cl_input.readLine();
                        write_socket.println(input);

                        System.out.print("Labels (separated by spaces): ");
                        input = cl_input.readLine();
                        write_socket.println(input);

                        input="2";
                    }
                    else if(input.equals("2")){
                        write_socket.println("download");
                    }
                    else if(input.equals("3")){
                        write_socket.println("search");
                        System.out.print("Labels (separated by spaces): ");
                        input = cl_input.readLine();
                        write_socket.println(input);
                        input="2";
                    }
                    else if(input.equals("0")){
                        write_socket.println("logout");
                        break;
                    }
                    if(input.equals("2") || input.equals("0")){
                        menu.show();
                    }
                    else System.out.println("Invalid option.");
                }
            }
            socket.shutdownOutput();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }

    }
}

