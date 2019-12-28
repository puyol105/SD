package Servidor;

import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

public class SoundCloud{
    private HashMap<String, Fan> fans;
    private HashMap<String, Musico> musicos;

    private HashMap<Integer, Ficheiro> musicas;

    private HashMap<String, Pedido> pedidos;
    private ReentrantLock lockSC;

    public SoundCloud(){
        this.fans = new HashMap<>();
        this.musicos = new HashMap<>();
        this.pedidos = new HashMap<>();
        this.lockSC = new ReentrantLock(); 
    }


    public boolean createMusico(String username, String pass, String nome){
        lockSC.lock();

        //Verificar se username já esta ocupado
        boolean f = musicos.containsKey(username) ? true : false;

        if (!f){
            Musico m = new Musico(username, pass, nome);
            musicos.put(username, m);
        }

        lockSC.unlock();
        return f;
    }


    public boolean createFan(String username, String pass, String nome){
        lockSC.lock();

        //Verificar se username já esta ocupado
        boolean f = fans.containsKey(username) ? true : false;

        if (!f){
            Fan fan = new Fan(username, pass, nome);
            fans.put(username, fan);
        }

        lockSC.unlock();
        return f;
    }
}

