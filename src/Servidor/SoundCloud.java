package Servidor;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SoundCloud{
    private HashMap<String, Utilizador> users;
    private HashMap<Integer, Ficheiro> musicas;
    private HashMap<String, ServerMessage> user_messages;
    private ReentrantLock lockSC;
    private ReentrantLock lockUsers;
    private ReentrantLock lockMsgs;

    public SoundCloud(){
        this.users = new HashMap<>();
        this.musicas = new HashMap<>();
        this.user_messages = new HashMap<>();
        this.lockSC = new ReentrantLock();
        this.lockUsers = new ReentrantLock();
        this.lockMsgs = new ReentrantLock();
    }


    public boolean createUser(String username, String pass, String nome, ServerMessage sm){
        this.lockUsers.lock();

        //Verificar se username já esta ocupado
        boolean f = users.containsKey(username);
        if (!f){
            Utilizador u = new Utilizador(username, pass, nome);
            users.put(username, u);

            this.lockMsgs.lock();
            user_messages.put(username, sm);
            this.lockMsgs.unlock();
        }


        this.lockUsers.unlock();
        return !f;
    }

    public Utilizador login(String username, String password, ServerMessage sm) throws UsernameInexistenteException, PasswordIncorretaException{
        this.lockSC.lock();

        Utilizador u = null;

        if(!users.containsKey(username)){
            throw new UsernameInexistenteException("Invalid Username.\n");
        } else{
            u = users.get(username);

            if (!u.getPassword().equals(password)) {
                throw new PasswordIncorretaException("Incorrect password.\n");
            }
        }

        this.lockMsgs.lock();
        if(this.user_messages.containsKey(username)){
            ServerMessage m = this.user_messages.get(username);

            String linha;
            while((linha = m.getMessage())!=null){
                sm.setMessage(linha, null);
            }

            this.user_messages.put(username,sm);
        }
        this.lockMsgs.unlock();

        this.lockSC.unlock();
        return u;
    }

    //Adicionar música
    public Ficheiro upload(Ficheiro file){
        this.lockSC.lock();

        int id = this.musicas.size();
        file.setId(id);

        ProcessBuilder pbb = new ProcessBuilder("cp", "-a", file.getPath(), "../../MusicFiles");
        try {
            pbb.start();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        this.musicas.put(id,file);

        this.lockSC.unlock();
        return file;
    }

    //Descarregar música

    //Pesquisar música
    public ArrayList<Ficheiro> search(String label){
        this.lockSC.lock();
        String[] separated_labels = label.split(" "); 

        ArrayList<Ficheiro> lista = new ArrayList<Ficheiro>();
        for (Ficheiro f : this.musicas.values()) {
            for (String l : separated_labels) {
                if (f.getLabels().contains(l)) 
                    lista.add(f);
            }
        }

        this.lockSC.unlock();
        return lista;
    }
}

