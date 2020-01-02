package Servidor;
import java.util.ArrayList;

public class Ficheiro{
    private int id;
    private String nome;
    private String artista;
    private ArrayList<String> labels;
    private int ano;
    private int times_downloaded;

    public Ficheiro(){
        id = -1;
        nome = "";
        artista = "";
        labels = new ArrayList<String>();
        ano = -1;
        times_downloaded = 0;
    }

    public Ficheiro(int id, String nome, String artista, ArrayList<String> labels, int ano){
        this.id=id;
        this.nome=nome;
        this.artista=artista;
        this.labels=labels;
        this.ano=ano;
        this.times_downloaded=0;
    }

    public int getId(){
        return this.id;
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

    public int getTimes_downloaded(){
        return this.times_downloaded;
    }

    public void setId(int id){
        this.id=id;
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

    public void setTimes_downloaded(int times_downloaded){
        this.times_downloaded = times_downloaded;
    }

    public int incTimesPlayed(){
        return ++this.times_downloaded;
    }

    public String toString(){
        StringBuilder string;
        string = new StringBuilder();
        string.append("Ficheiro: { ");
        string.append("id = \"" + this.id + "\"");
        string.append(", nome = \"" + this.nome + "\"");
        string.append(", artista = \"" + this.artista + "\"");
        string.append(", ano = \"" + this.ano + "\"");
        string.append(", labels = \"" + this.labels + "\"");
        string.append(", times_downloaded = \"" + this.times_downloaded + "\"");
        string.append(" }");
        return string.toString();
    }
    
    public String toFancyString(){
        StringBuilder string;
        string = new StringBuilder();
        string.append("ID = \"" + this.id + "\"");
        string.append(", Title = \"" + this.nome + "\"");
        string.append(", Artist/Band = \"" + this.artista + "\"");
        string.append(", Year of Release = \"" + this.ano + "\"");
        string.append(", Labels = \"" + this.labels + "\"");
        string.append(", Times Downloaded = \"" + this.times_downloaded + "\".");
        return string.toString();
    }
}

