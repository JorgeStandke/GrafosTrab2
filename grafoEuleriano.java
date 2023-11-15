import java.util.ArrayList;

public class GrafoEuleriano {

    private int[][] grafo = {
        //    A  B  C  D  E  F  G  H  I  J  K  L  M
            { 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},  // A
            { 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0},  // B
            { 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0},  // C
            { 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0},  // D
            { 0, 1, 1, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0},  // E
            { 0, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0},  // F
            { 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0},  // G
            { 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 1, 0},  // H
            { 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1},  // I
            { 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 1, 0, 0},  // J
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0},  // K
            { 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 1},  // L
            { 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0}   // M
        };
    private int[] peso = {3, 1, 1, 3, 1, 1, 3, 1, 2, 4, 2, 2, 1, 3, 1, 2, 1, 2, 1};

    private ArrayList<Aresta> arestas = getArestasDoGrafo();

    public static void main(String[] args) {
        new GrafoEuleriano();
    }

    private GrafoEuleriano() {
        for(Aresta aresta: arestas){
            System.out.println(aresta.toString());
        }
    }

    private ArrayList<Aresta> getArestasDoGrafo() {
        ArrayList<Aresta> arestas = new ArrayList<>();
        int pesoCount = 0;

        for(int i = 0; i < grafo.length;i ++) {
            for(int j = 0; j <= i; j++) {
                if(grafo[i][j] == 1) {
                    arestas.add(new Aresta(j, i, peso[pesoCount]));
                    pesoCount++;
                }
            }
        }

        return arestas;
    }

}
