package Servidor;

import java.util.concurrent.locks.ReentrantLock;

public abstract class Utilizador{
    private String username;
    private String password;
    private ReentrantLock lock;

    public Utilizador(){
        this.username = "n/a";
        this.password = "n/a";
        this.lock = new ReentrantLock();
    }

    public Utilizador(Utilizador c) {
        this.username = c.getUsername();
        this.password = c.getPassword();
        this.lock = new ReentrantLock();
    }

    public Utilizador(String username, String password){
        this.username = username;
        this.password = password;
        this.lock = new ReentrantLock();
    }

    public String getUsername(){
        return this.username;
    }

    public String getPassword(){
        return this.password;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void setPassword(String password){
        this.password = password;
    }
    
    public void lock(){
        this.lock.lock();
    }

    public void unlock(){
        this.lock.unlock();
    }

    public String toString(){
        StringBuilder string;
        string = new StringBuilder();
        string.append("Username: ");
        string.append(this.username+'\n');
        return string.toString();
    }

    public boolean equals(Object obj){
        if(obj == this){
            return true;
        }
        if(obj == null || obj.getClass() != this.getClass()){
            return false;
        }
        Utilizador u = (Utilizador) obj;
        return u.getUsername().equals(this.username);

    }

    //public abstract Utilizador clone();
}
