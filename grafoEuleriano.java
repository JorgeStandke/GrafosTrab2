import java.util.ArrayList;
import java.util.List;

public class grafoEuleriano {

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
        new grafoEuleriano();
    }

    private grafoEuleriano() {
        for(Aresta aresta: arestas){
            System.out.println(aresta.toString());
        }

        System.out.println(dijkstra());
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

    private String dijkstra(){
        int v = grafo[0].length;
        int pivo = 0;
        int valCaminho = 0;
        ArrayList<Vertice> vertices = new ArrayList<>();

        // Criação dos Vértices com suas respectivas arestas;
        for (int i = 0; i < v; i++) {
            char letra = (char) ('A' + i);
            Vertice vertice = new Vertice(Character.toString(letra), null, new ArrayList<>());
            vertices.add(vertice);
        }

        for (int i = 0; i < v; i++) {
            // Criar uma nova lista de arestas para cada vértice
            List<Aresta> arestasDoVertice = new ArrayList<>();
        
            for (int j = 0; j < v; j++) {
                if (arestas.get(j).getU() == i) {
                    arestasDoVertice.add(arestas.get(j));
                }
        
                if (arestas.get(j).getW() == i) {
                    // Adiciona a aresta ao vértice de destino também
                    arestasDoVertice.add(arestas.get(j));
                }
            }
        
            vertices.get(i).setArestasDoVertice(arestasDoVertice);
        }        

        int[][] matrizCusto = {
                //    A  B  C  D  E  F  G  H  I  J  K  L  M
                    { 0, 999, 999, 999, 999, 999, 999, 999, 999, 999, 999, 999, 999},  // A
                    { 999, 0, 999, 999, 999, 999, 999, 999, 999, 999, 999, 999, 999},  // B
                    { 999, 999, 0, 999, 999, 999, 999, 999, 999, 999, 999, 999, 999},  // C
                    { 999, 999, 999, 0, 999, 999, 999, 999, 999, 999, 999, 999, 999},  // D
                    { 999, 999, 999, 999, 0, 999, 999, 999, 999, 999, 999, 999, 999},  // E
                    { 999, 999, 999, 999, 999, 0, 999, 999, 999, 999, 999, 999, 999},  // F
                    { 999, 999, 999, 999, 999, 999, 0, 999, 999, 999, 999, 999, 999},  // G
                    { 999, 999, 999, 999, 999, 999, 999, 0, 999, 999, 999, 999, 999},  // H
                    { 999, 999, 999, 999, 999, 999, 999, 999, 0, 999, 999, 999, 999},  // I
                    { 999, 999, 999, 999, 999, 999, 999, 999, 999, 0, 999, 999, 999},  // J
                    { 999, 999, 999, 999, 999, 999, 999, 999, 999, 999, 0, 999, 999},  // K
                    { 999, 999, 999, 999, 999, 999, 999, 999, 999, 999, 999, 0, 999},  // L
                    { 999, 999, 999, 999, 999, 999, 999, 999, 999, 999, 999, 999, 0}   // M
                };

        String[][] matrizD = {
            { "999", "999", "999", "999", "999", "999", "999", "999", "999", "999", "999", "999", "999"}, //Valores com o menor caminho
            { "", "", "", "", "", "", "", "", "", "", "", "", ""}, // Pai
            { "", "", "", "", "", "", "", "", "", "", "", "", ""} // Fila (verificar por qual vértice Dijkstra já passou)
        };

        //Tentativa de Dijkstra
        for(int k = 0; k <= matrizCusto.length; k++){
            //Zerando a matriz D
            String[][] matrizD2 = matrizD.clone();

            //Definindo qual o vértice de origem daquele dijkstra
                matrizD2[0][k] = String.valueOf(0);
                matrizD2[1][k] = "nil";
                pivo = Integer.parseInt(matrizD2[0][k]);

            for(int i = 0; i <= matrizD[0].length - 1; i++){
                ArrayList<Vertice> verticesLigados = new ArrayList<>();
                //Mudando o pivô
                pivo = Integer.parseInt(matrizD2[0][i]);

                for(int j = 0; j <= vertices.get(i).getArestasDoVertice().size()-1; j++){

                    //Verifica se está olhando vértices já visitados
                    if(matrizD2[2][i] == ""){

                        //Adicionando a um ArrayList(Fiz mais para acompanhar o processo) os vértices ligados ao pivo no momento
                        verticesLigados.add(vertices.get(vertices.get(i).getArestasDoVertice().get(j).getW()));

                        //Salvando o valor do caminho entre o vértice salvo, e, o pivô
                        valCaminho = verticesLigados.get(j).getArestasDoVertice().get(j).getPeso();

                        //Fazendo a verificação do Dijkstra e se der true substituindo
                        if(Integer.parseInt(matrizD2[0][vertices.get(i).getArestasDoVertice().get(j).getW()]) > pivo + valCaminho){
                            matrizD2[0][j] = String.valueOf(pivo + valCaminho);
                            matrizD2[1][i] = vertices.get(j).getNome();
                        }

                    }else{
                        break;
                    }

                }

                //Marcando que o vértice já foi verificado
                matrizD2[2][i] = "X";
            }

            System.out.println(matrizD2.toString());
        }

        return "AOBA";
    }

}
