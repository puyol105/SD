package Servidor;

import java.io.File;

public class Ficheiro{
    //private Caminho pra musica

    private File file;
    private int id;
    private String nome;
    private Musico musico;
    private int ano;

    public Ficheiro(Ficheiro f){
        this.id=f.getId();
        this.nome=f.getNome();
        this.musico=f.getMusico();
        this.ano=f.getAno();
    }

    public Ficheiro(int id, String nome, Musico m, int a){
        this.id=id;
        this.nome=nome;
        this.musico=m;
        this.ano=a;
    }

    public int getId(){
        return this.id;
    }

    public String getNome(){
        return this.nome;
    }

    public Musico getMusico(){
        return this.musico;
    }

    public int getAno(){
        return this.ano;
    }

    public void setId(int id){
        this.id=id;
    }

    public void setNome(String nome){
        this.nome=nome;
    }
    
    public void setMusico(Musico m){
        this.musico=m;
    }

    public void setAno(int ano){
        this.ano=ano;
    }

    public String toString(){
        StringBuilder string;
        string = new StringBuilder();
        string.append("Path: ");
        //string.append(this.path+'\n');
        string.append("Título: ");
        string.append(this.nome+'\n');
        string.append("Músico: ");
        string.append(this.musico.getNome()+'\n');
        string.append("Ano: ");
        string.append(this.ano+'\n');
        return string.toString();
    }
/*
    public boolean equals(Object obj){
        if(obj == this){
            return true;
        }
        if(obj == null || obj.getClass() != this.getClass()){
            return false;
        }
        Ficheiro f = (Ficheiro) obj;
        return f.getID().equals(this.id);
    }
    */
}

