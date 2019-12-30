package Servidor;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

public class SoundCloud{
    private HashMap<String, Utilizador> users;
    private HashMap<Integer, Ficheiro> musicas;
    private ReentrantLock lockSC;

    public SoundCloud(){
        this.users = new HashMap<>();
        this.musicas = new HashMap<>();
        this.lockSC = new ReentrantLock();
    }


    public boolean createUser(String username, String pass, String nome){
        this.lockSC.lock();

        //Verificar se username já esta ocupado
        boolean f = users.containsKey(username);
        if (!f){
            Utilizador u = new Utilizador(username, pass, nome);
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
    public Ficheiro upload(Ficheiro file){
        this.lockSC.lock();

        int id = this.musicas.size();
        file.setId(id);
        this.musicas.put(id,file);

        this.lockSC.unlock();
        return file;
    }

    //Descarregar música

    //Pesquisar música
    public ArrayList<Ficheiro> search(String label){
        this.lockSC.lock();

        ArrayList<Ficheiro> lista = new ArrayList<Ficheiro>();
        for (Ficheiro f : this.musicas.values()) {
            if (f.getLabels().contains(label)) {
                lista.add(f);
            }
        }

        this.lockSC.unlock();
        return lista;
    }
}

