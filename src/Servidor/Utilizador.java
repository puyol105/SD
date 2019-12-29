package Servidor;

import java.util.concurrent.locks.ReentrantLock;

public class Utilizador{
    private String username;
    private String password;
    private String name;
    private boolean isMusico;
    private ReentrantLock lock;

    public Utilizador(){
        this.username = "n/a";
        this.password = "n/a";
        this.name = "n/a";
        this.isMusico = false; 
        this.lock = new ReentrantLock();
    }

    public Utilizador(Utilizador c) {
        this.username = c.getUsername();
        this.password = c.getPassword();
        this.name = c.getName();
        this.isMusico = c.getIsMusico();
        this.lock = new ReentrantLock();
    }

    public Utilizador(String username, String password, String name, boolean isMusico){
        this.username = username;
        this.password = password;
        this.name = name;
        this.isMusico = isMusico;
        this.lock = new ReentrantLock();
    }

    public String getUsername(){
        return this.username;
    }

    public String getPassword(){
        return this.password;
    }
   
    public String getName(){
        return this.name;
    }

    public boolean getIsMusico(){
        return this.isMusico;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setIsMusico(boolean isMusico){
        this.isMusico = isMusico;
    }
    
    public void lock(){
        this.lock.lock();
    }

    public void unlock(){
        this.lock.unlock();
    }

    public String toString(){
        return "Utilizador: { username = \"" + username + "\""
                         + ", password = \"" + password + "\""
                         + ", name = \"" + name + "\""
                         + ", isMusico = \"" + isMusico + "\""
                         + "}";
    }
}

