package Servidor;

import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

public class SoundCloud {
    private HashMap<String, Utilizador> users;
    private HashMap<Integer, Ficheiro> musicas;
    private HashMap<String, ServerMessage> user_messages;
    private ReentrantLock lockSC;
    private ReentrantLock lockUsers;
    private ReentrantLock lockMsgs;

    public SoundCloud() {
        this.users = new HashMap<>();
        this.musicas = new HashMap<>();
        this.user_messages = new HashMap<>();
        this.lockSC = new ReentrantLock();
        this.lockUsers = new ReentrantLock();
        this.lockMsgs = new ReentrantLock();
    }

    public boolean createUser(String username, String pass, ServerMessage sm) {
        this.lockUsers.lock();

        try{
            // Verificar se username já esta ocupado
            boolean f = users.containsKey(username);
            if (!f) {
                Utilizador u = new Utilizador(username, pass);
                users.put(username, u);

                this.lockMsgs.lock();
                user_messages.put(username, sm);
                this.lockMsgs.unlock();
            }
            return !f;
        }
        finally{
            this.lockUsers.unlock();
        }
    }

    public Utilizador login(String username, String password, ServerMessage sm) throws UsernameInexistenteException, PasswordIncorretaException {
        this.lockSC.lock();
        try{
            Utilizador u = null;

            if (!users.containsKey(username)) {
                throw new UsernameInexistenteException("Invalid Username.\n");
            } else {
                u = users.get(username);

                if (!u.getPassword().equals(password)) {
                    throw new PasswordIncorretaException("Incorrect password.\n");
                }
            }

            this.lockMsgs.lock();

            if (this.user_messages.containsKey(username)) {
                ServerMessage m = this.user_messages.get(username);

                String linha;
                while ((linha = m.getMessage()) != null) {
                    sm.setMessage(linha, null);
                }

                this.user_messages.put(username, sm);
            }

            this.lockMsgs.unlock();
            return u;
        }
        finally{
            this.lockSC.unlock();
        }
    }

    // Adicionar música
    public Ficheiro upload(Ficheiro f, Socket socket, int filesize) {
        this.lockSC.lock();
        
        try {
            int id = this.musicas.size();
            f.setId(id);
            try {
                DataInputStream dis = new DataInputStream(socket.getInputStream());
                FileOutputStream fos;
    
                fos = new FileOutputStream("../MusicFiles/" + f.getId() + "_" + f.getNome() + ".mp3");
                byte[] buffer = new byte[4096];
        
                int read = 0;
                int remaining = filesize;
                while((read = dis.read(buffer, 0, Math.min(buffer.length, remaining))) > 0) {
                    remaining -= read;
                    fos.write(buffer, 0, read);
                }
                
                fos.flush();
                fos.close();       
            } catch (Exception e) {
                e.printStackTrace();
            }            
            this.musicas.put(id, f);

            return f;
        }
        finally{
            this.lockSC.unlock();
        }
    }

    //Descarregar música
    public Ficheiro download(int id){
        this.lockSC.lock();

        try{
            Ficheiro f = null;
            try {
                f = this.musicas.get(id);
                f.incTimesPlayed();
                this.musicas.put(id, f);
            }
            catch (Exception e) {
                System.out.println("Exception: " + e);
            }
            return f;
        }
        finally{
            this.lockSC.lock();
        }
    }

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

