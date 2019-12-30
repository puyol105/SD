package Servidor;
import java.util.ArrayList;

public class Ficheiro{
    private int id;
    private String path;
    private String nome;
    private String artista;
    private ArrayList<String> labels;
    private int ano;
    private int times_played;

    public Ficheiro(){
        id = -1;
        path = "";
        nome = "";
        artista = "";
        labels = new ArrayList<String>();
        ano = -1;
        times_played = 0;
    }

    public Ficheiro(int id, String path, String nome, String artista, ArrayList<String> labels, int ano){
        this.id=id;
        this.path=path;
        this.nome=nome;
        this.artista=artista;
        this.labels=labels;
        this.ano=ano;
        this.times_played=0;
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

    public String getArtista(){
        return this.artista;
    }

    public ArrayList<String> getLabels(){
        return this.labels;
    }

    public int getAno(){
        return this.ano;
    }

    public int getTimes_played(){
        return this.times_played;
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

    public void setArtista(String artista){
        this.artista=artista;
    }

    public void setAno(int ano){
        this.ano=ano;
    }

    public void setTimes_played(int times_played){
        this.times_played = times_played;
    }

    public int incTimesPlayed(){
        return ++this.times_played;
    }

    public String toString(){
        StringBuilder string;
        string = new StringBuilder();
        string.append("Ficheiro: { ");
        string.append("id = \"" + this.id + "\"");
        string.append(", path = \"" + this.path + "\"");
        string.append(", nome = \"" + this.nome + "\"");
        string.append(", artista = \"" + this.artista + "\"");
        string.append(", ano = \"" + this.ano + "\"");
        string.append(", labels = \"" + this.labels + "\"");
        string.append(", times_played = \"" + this.times_played + "\"");
        string.append(" }");
        return string.toString();
    }
}

