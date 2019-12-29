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
        this.musicas = new HashMap<>();
        this.pedidos = new HashMap<>();
        this.lockSC = new ReentrantLock(); 
    }


    public boolean createMusico(String username, String pass, String nome){
        this.lockSC.lock();

        //Verificar se username já esta ocupado
        boolean f = this.musicos.containsKey(username) || this.fans.containsKey(username);

        if (!f){
            Musico m = new Musico(username, pass, nome);
            this.musicos.put(username, m);
        }

        this.lockSC.unlock();
        return f;
    }


    public boolean createFan(String username, String pass, String nome){
        this.lockSC.lock();

        //Verificar se username já esta ocupado
        boolean f = this.fans.containsKey(username) || this.musicos.containsKey(username);

        if (!f){
            Fan fan = new Fan(username, pass, nome);
            this.fans.put(username, fan);
        }

        this.lockSC.unlock();
        return f;
    }


    public Utilizador login(String username, String pass) throws UsernameInexistenteException, PasswordIncorretaException{
        this.lockSC.lock();
        try{
            if(!this.fans.containsKey(username) || !this.musicos.containsKey(username)){
                throw new UsernameInexistenteException("Username não existe!!!");
            }
            else if(!this.fans.get(username).getPassword().equals(password) || !this.musicos.get(username).getPassword().equals(password)){
                throw new PasswordIncorretaException("A password está incorreta!!!");
            } else{
                if(this.musicos.containsKey(username)) return this.musicos.get(username);
                if(this.fans.containsKey(username)) return this.fans.get(username);
            }
        }
        finally{
            this.lockSC.unlock();
        }
    }

    //Adicionar música
    public Ficheiro upload(String nome, int ano, Musico musico){
        this.lockSC.lock();
        try{
            int next = this.musicas.size();
            Ficheiro f = new Ficheiro(next,nome,musico,ano);
            this.musicas.put(next,f);
            return f;
        }
        finally{
            this.lockSC.unlock();
        }
    }

    //Descarregar música

    //Pesquisar música
}

