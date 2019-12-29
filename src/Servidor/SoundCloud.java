package Servidor;

import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

public class SoundCloud{

    private HashMap<String, Utilizador> users;
    private HashMap<Integer, Ficheiro> musicas;
    //private HashMap<String, Pedido> pedidos;
    private ReentrantLock lockSC;

    public SoundCloud(){
        this.users = new HashMap<>();
        this.musicas = new HashMap<>();
        //this.pedidos = new HashMap<>();
        this.lockSC = new ReentrantLock(); 
    }


    public boolean createUser(String username, String pass, String nome, boolean isMusico){
        this.lockSC.lock();

        //Verificar se username já esta ocupado
        boolean f = users.containsKey(username);
        if (!f){

            Utilizador u = new Utilizador(username, pass, nome, isMusico);
            users.put(username, u); 
        }

        this.lockSC.unlock();
        return !f;
    }

    public Utilizador login(String username, String password) throws UsernameInexistenteException, PasswordIncorretaException{
        this.lockSC.lock();
        
        Utilizador u = null;
        
        if(!users.containsKey(username)){
            throw new UsernameInexistenteException("Username não existe.\n");
        } else{
            u = users.get(username);
            
            if (!u.getPassword().equals(password)) {
                throw new PasswordIncorretaException("A password está incorreta.\n");
            }
        }
        
        this.lockSC.unlock();
        return u;
    }

    //Adicionar música
    public Ficheiro upload(String nome, int ano, Utilizador musico){
        this.lockSC.lock();

        int next = this.musicas.size();
        Ficheiro f = new Ficheiro(next,nome,musico,ano);
        this.musicas.put(next,f);
        
        this.lockSC.unlock();
        return f;        
    }

    //Descarregar música

    //Pesquisar música
}

