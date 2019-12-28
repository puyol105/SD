import java.lang.Thread;
import java.io.*;
import java.net.*;

public class ThreadServidorRead extends Thread{
    private BufferedReader read_socket;
    private GestorLeiloes g;
    private Utilizador u;
    private MensagemServidor ms;

    public ThreadServidorRead(BufferedReader read_socket, GestorLeiloes g, MensagemServidor ms){
        this.read_socket = read_socket;
        this.g = g;
        this.u = null;
        this.ms = ms;
    }
    
    public void run(){
        try{
            String input;
            while((input = read_socket.readLine()) != null){ 

                if(input.equals("iniciar_sessao")){                     
                    String user,pass;
                    user = read_socket.readLine();
                    pass = read_socket.readLine();
                    
                    try{
                        this.u = g.iniciarSessao(user,pass,ms);
                        if(u.getClass().getName().equals("Comprador"))
                            ms.setMsg("Iniciou sessão como Comprador!",null);
                        else ms.setMsg("Iniciou sessão como Vendedor!",null);
                    }
                    catch(Exception e){
                        ms.setMsg(e.getMessage(),null);
                    }
                }
                else if(input.equals("registar_comprador")){                    
                    String user,pass;
                    user = read_socket.readLine();
                    pass = read_socket.readLine();

                    try{
                        g.registarUtilizador(user,pass,0,ms);
                        ms.setMsg("Registado",null);
                    }
                    catch(Exception e){
                        ms.setMsg(e.getMessage(),null);
                    }
                }
                else if(input.equals("registar_vendedor")){                 
                    String user,pass;
                    user = read_socket.readLine();
                    pass = read_socket.readLine();

                    try{
                        g.registarUtilizador(user,pass,1,ms);
                        ms.setMsg("Registado",null);
                    }
                    catch(Exception e){
                        ms.setMsg(e.getMessage(),null);
                    }
                }
                else if(input.equals("licitar")){                   
                    String idLeilao,valor;
                    idLeilao = read_socket.readLine();
                    valor = read_socket.readLine();
                    Double val = Double.parseDouble(valor);
                    try{
                        g.licitarLeilao(idLeilao,u,val);
                        ms.setMsg("Licitou",null);
                    }
                    catch(Exception e){
                        ms.setMsg(e.getMessage(),null);
                    }
                }
                else if(input.equals("iniciar_leilao")){                    
                    String descricao;
                    descricao = read_socket.readLine();
                    Leilao leilao = new Leilao(descricao,u);
                    try{
                        g.adicionarLeilao(leilao,u);
                        ms.setMsg("Leilao Iniciado",null);
                    }
                    catch(Exception e){
                        ms.setMsg(e.getMessage(),null);
                    }
                }
                else if(input.equals("consultar_leilao")){                  
                    try{
                        ms.setMsg(null,g.consultarLeiloes(u));  
                    }
                    catch(Exception e){
                        ms.setMsg(e.getMessage(),null);
                    }
                }
                else if(input.equals("encerrar_leilao")){                   
                    String idLeilao;
                    idLeilao = read_socket.readLine();
                    try{
                        ms.setMsg(g.encerrarLeilao(idLeilao,u),null);
                    }
                    catch(Exception e){
                        ms.setMsg(e.getMessage(),null);
                    }
                }
                else if(input.equals("terminar_sessao")){                   
                    this.u = null;
                    ms.setMsg("Terminou sessão",null);
                }

                
            }
            read_socket.close();
            ms.setMsg("Sair",null);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}