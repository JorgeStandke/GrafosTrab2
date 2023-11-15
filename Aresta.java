public class Aresta {
    private int u;
    private int w;
    private int peso;

    public Aresta(final int u, final int w, final int nome, final int peso) {
        this.setU(u);
        this.setW(w);
        this.setPeso(peso);
    }

    public int getU() {
        return u;
    }

    public int getW() {
        return w;
    }

    public int getPeso() {
        return peso;
    }

    public void setU(final int u) {
        this.u = u;
    }

    public void setW(final int w) {
        this.w = w;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

}
