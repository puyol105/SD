import java.lang.Thread;
import java.io.*;
import java.net.*;
import java.util.ResourceBundle;
import java.util.concurrent.locks.*;

public class ThreadClienteOutput extends Thread{
    private BufferedReader ler_socket;
    private Menu menu;
    private ReentrantLock lock; 
    private Condition cond;

    public ThreadClienteOutput(BufferedReader ler_socket, Menu menu, ReentrantLock l, Condition c){
        this.ler_socket = ler_socket;
        this.menu = menu;
        this.lock=l;
        this.cond=c;
    }
    
    public void run(){
        try{
            String linha;                               
            while((linha = ler_socket.readLine())!=null){
                if(linha.equals("Iniciou sessão como Comprador!")){
                    menu.setOp(1);
                    this.lock.lock();
                    cond.signal();
                    this.lock.unlock();
                }
                else if(linha.equals("Iniciou sessão como Vendedor!")){
                    menu.setOp(2);
                    this.lock.lock();
                    cond.signal();
                    this.lock.unlock();
                }
                else if(linha.equals("Terminou sessão") || linha.equals("Username inexistente!") || linha.equals("A password está incorreta!")){
                    menu.setOp(0);
                    this.lock.lock();
                    cond.signal();
                    this.lock.unlock();
                }
                else if(linha.equals("Consultar")){
                    String s = ler_socket.readLine();
                    if(s.length()==0)
                        linha = "Nenhum leilão aberto.";
                    else linha = s.replaceAll("(?i)[_]+", "\n");
                }
                System.out.println("\n"+linha+"\n");
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}