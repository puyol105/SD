package Servidor;

//import java.io.File;

public class Ficheiro{ 
    //private File file;
    private int id;
    private String path;
    private String nome;
    private Utilizador musico;
    private int ano;

    public Ficheiro(Ficheiro f){
        this.id=f.getId();
        this.path=f.getPath();
        this.nome=f.getNome();
        this.musico=f.getMusico();
        this.ano=f.getAno();
    }

    public Ficheiro(int id, String path, String nome, Utilizador m, int a){
        this.id=id;
        this.path=path;
        this.nome=nome;
        this.musico=m;
        this.ano=a;
    }

    public int getId(){
        return this.id;
    }

    public String getPath(){
        return this.path;
    }

    public String getNome(){
        return this.nome;
    }

    public Utilizador getMusico(){
        return this.musico;
    }

    public int getAno(){
        return this.ano;
    }

    public void setId(int id){
        this.id=id;
    }

    public void setPath(String path){
        this.path = path;
    }

    public void setNome(String nome){
        this.nome=nome;
    }
    
    public void setMusico(Utilizador m){
        this.musico=m;
    }

    public void setAno(int ano){
        this.ano=ano;
    }

    public String toString(){
        StringBuilder string;
        string = new StringBuilder();
        string.append("ID: ");
        string.append(this.id+'\n');
        string.append("Path: ");
        string.append(this.path+'\n');
        string.append("Título: ");
        string.append(this.nome+'\n');
        string.append("Músico: ");
        string.append(this.musico.getName()+'\n');
        string.append("Ano: ");
        string.append(this.ano+'\n');
        return string.toString();
    }
}

