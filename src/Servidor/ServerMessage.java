package Servidor;

import java.lang.Thread;
import java.io.*;
import java.net.*;
import java.util.ResourceBundle;
import java.util.concurrent.locks.*;
import java.util.List;
import java.util.ArrayList;

public class ServerMessage {
    private ArrayList<String> message;
    private Condition c;
    private ReentrantLock lock;
    private int index;

    public ServerMessage(Condition c, ReentrantLock lock){
        this.message = new ArrayList<>();
        this.c = c;
        this.lock = lock;
        this.index = 0;
    }

    public String getMessage(){
        String r = null;
        this.lock.lock();

        if(index != message.size())
            r = this.message.get((index++));

        this.lock.unlock();
        return r;
    }

    public Condition getCondition(){
        return this.c;
    }

    public ReentrantLock getLock(){
        return this.lock;
    }

    public void setMessage(String msg, ArrayList<String> msg_list){
        this.lock.lock();

        if(msg_list == null)
            this.message.add(msg);
        else {
            for(String s : msg_list)
                this.message.add(s);
        }
        c.signal();

        this.lock.unlock();
    }
}

