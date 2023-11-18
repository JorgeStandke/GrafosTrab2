import java.util.List;
import java.util.ArrayList;

public class Vertice {
    private String nome = "";
    private String pai = "";
    private List<Aresta> arestasDoVertice = new ArrayList<Aresta>();
    
    public Vertice(String nome, String pai, List<Aresta> arestasDoVertice) {
        this.nome = nome;
        this.pai = pai;
        this.arestasDoVertice = arestasDoVertice;
    }

    public String getPai() {
        return pai;
    }

    public void setPai(String pai) {
        this.pai = pai;
    }

    public List<Aresta> getArestasDoVertice() {
        return arestasDoVertice;
    }

    public void setArestasDoVertice(List<Aresta> arestasDoVertice) {
        this.arestasDoVertice = arestasDoVertice;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
