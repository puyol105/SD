package Servidor;

public class Fan extends Utilizador{
    private String nome;
    
    public Fan(String username, String password, String nome){
        super(username, password);
        this.nome = nome;
    }

    public String getNome(){
        return this.nome;
    }

}
