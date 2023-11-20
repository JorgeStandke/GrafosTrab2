//João Vitor Schmidt e Jorge Henrique Rigo Standke

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Stack;

public class grafoEuleriano {

    private int[][] grafo = {
        // Matriz de adjacência
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
    private int custoTotal = 0;

    public static void main(String[] args) {
        new grafoEuleriano();
    }

    private grafoEuleriano() {
        // Etapa 1: Imprimir as arestas
        System.out.println("Arestas do Grafo:");
        for (Aresta aresta : arestas) {
            System.out.println(aresta.toString());
        }
    
        // Etapa 2: Aplicar o algoritmo de Dijkstra para cada vértice ímpar
        System.out.println("\nResultados do Algoritmo de Dijkstra:");
    
        int v = grafo.length;
        int[][] matrizCustoTotal = new int[v][v]; // Adicione esta linha
    
        for (int i = 0; i < v; i++) {
            if (grauVerticeImpar(i)) {
                System.out.println("\nDijkstra a partir do vértice " + (char) ('A' + i) + ":");
                int[][] matrizCustoParcial = dijkstra(i);
    
                for (int j = 0; j < v; j++) {
                    if (i != j && matrizCustoParcial[i][j] != Integer.MAX_VALUE) {
                        matrizCustoTotal[i][j] = matrizCustoParcial[i][j];
                        System.out.println((char) ('A' + i) + " para " + (char) ('A' + j) + ": " + matrizCustoParcial[i][j]);
                    }
                }
            }
        }
    
        // Etapa 3: Exibir a matriz de custo
        System.out.println("\nMatriz de Custo:");
        exibirMatriz(matrizCustoTotal);
    
        // Etapa 4: Eulerizar o grafo
        eulerizarGrafo();
    }

    private void exibirMatriz(int[][] matriz) {
        int v = matriz.length;
    
        // Imprimir cabeçalho
        System.out.print("\t");
        for (int i = 0; i < v; i++) {
            System.out.print((char) ('A' + i) + "\t");
        }
        System.out.println();
    
        // Imprimir linhas
        for (int i = 0; i < v; i++) {
            System.out.print((char) ('A' + i) + "\t");
            for (int j = 0; j < v; j++) {
                System.out.print(matriz[i][j] + "\t");
            }
            System.out.println();
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

    private void eulerizarGrafo() {
        // Encontrar o emparelhamento mínimo
        int[][] emparelhamento = encontrarEmparelhamentoMinimo();

        // Adicionar as arestas do emparelhamento ao grafo
        for (int i = 0; i < grafo.length; i++) {
            for (int j = 0; j < grafo.length; j++) {
                if (emparelhamento[i][j] == 1) {
                    grafo[i][j] = 1;
                    grafo[j][i] = 1; // Arestas não dirigidas
                }
            }
        }

        // Encontrar ciclo euleriano no grafo eulerizado
        List<Integer> cicloEuleriano = hierholzerAlgorithm();

        // Imprimir o ciclo euleriano e seu custo total
        System.out.println("\nCiclo Euleriano:");
        int custoTotal = 0;
        for (int i = 0; i < cicloEuleriano.size() - 1; i++) {
            int origem = cicloEuleriano.get(i);
            int destino = cicloEuleriano.get(i + 1);
            System.out.println((char) ('A' + origem) + " -> " + (char) ('A' + destino) + ", Peso: " + grafo[origem][destino]);
            custoTotal += grafo[origem][destino];
        }

        // Adicionar a aresta de retorno ao início do ciclo
        int origem = cicloEuleriano.get(cicloEuleriano.size() - 1);
        int destino = cicloEuleriano.get(0);
        System.out.println((char) ('A' + origem) + " -> " + (char) ('A' + destino) + ", Peso: " + grafo[origem][destino]);
        custoTotal += grafo[origem][destino];

        System.out.println("Custo Total do Ciclo Euleriano: " + custoTotal);
    }

    private int[][] encontrarEmparelhamentoMinimo() {
    int[][] emparelhamento = new int[grafo.length][grafo.length];
    boolean[] visitados = new boolean[grafo.length];
    
    for (int i = 0; i < grafo.length; i++) {
        if (grauVerticeImpar(i)) {
            Arrays.fill(visitados, false);
            aumentarCaminho(i, emparelhamento, visitados);
        }
    }

    return emparelhamento;
    }

    private boolean aumentarCaminho(int vertice, int[][] emparelhamento, boolean[] visitados) {
        for (int v = 0; v < grafo.length; v++) {
            if (grafo[vertice][v] == 1 && !visitados[v]) {
                visitados[v] = true;
    
                if (emparelhamento[vertice][v] == 0 || aumentarCaminho(v, emparelhamento, visitados)) {
                    emparelhamento[vertice][v] = 1;
                    emparelhamento[v][vertice] = 1; // Atualização para um grafo não dirigido
                    return true;
                }
            }
        }
    
        return false;
    }

    private int[][] dijkstra(int origem) {
        int v = grafo.length;
        int[][] matrizCusto = new int[v][v];
    
        // Inicializar matriz de custo com valores infinitos
        for (int i = 0; i < v; i++) {
            Arrays.fill(matrizCusto[i], Integer.MAX_VALUE);
        }
    
        matrizCusto[origem][origem] = 0;
    
        PriorityQueue<Integer> filaPrioridade = new PriorityQueue<>();
        filaPrioridade.add(origem);
    
        while (!filaPrioridade.isEmpty()) {
            int u = filaPrioridade.poll();
    
            for (int w = 0; w < v; w++) {
                if (grafo[u][w] == 1) {
                    int pesoUW = peso[getIndexAresta(u, w)];
                    if (matrizCusto[origem][w] > matrizCusto[origem][u] + pesoUW) {
                        matrizCusto[origem][w] = matrizCusto[origem][u] + pesoUW;
                        filaPrioridade.add(w);
                    }
                }
            }
        }
    
        // Retornar a matriz de custo
        return matrizCusto;
    }

    private List<Integer> hierholzerAlgorithm() {
        List<Integer> cicloEuleriano = new ArrayList<>();
        Stack<Integer> pilha = new Stack<>();
        int verticeAtual = encontrarVerticeInicial();
    
        pilha.push(verticeAtual);
    
        while (!pilha.isEmpty()) {
            if (grauVertice(verticeAtual) > 0) {
                pilha.push(verticeAtual);
                int proximoVertice = encontrarProximoVertice(verticeAtual, grafo);
                
                // Adicione o peso da aresta ao custo total
                int pesoAresta = grafo[verticeAtual][proximoVertice];
                custoTotal += pesoAresta;
    
                removerAresta(verticeAtual, proximoVertice);
                verticeAtual = proximoVertice;
            } else {
                cicloEuleriano.add(verticeAtual);
                verticeAtual = pilha.pop();
            }
        }
    
        return cicloEuleriano;
    }
    
    private void removerAresta(int u, int v) {
        // Reduzir o peso da aresta
        int pesoAresta = grafo[u][v];
        grafo[u][v] = 0;
        grafo[v][u] = 0;
    
        arestas.removeIf(aresta -> (aresta.getU() == u && aresta.getW() == v && grafo[u][v] == 0) ||
                           (aresta.getU() == v && aresta.getW() == u && grafo[v][u] == 0) ||
                           (aresta.getU() == u && aresta.getW() == v && grafo[u][v] < pesoAresta) ||
                           (aresta.getU() == v && aresta.getW() == u && grafo[v][u] < pesoAresta));
    
        custoTotal -= pesoAresta;
    }
    
    private int encontrarProximoVertice(int vertice, int[][] grafoCopia) {
        for (int v = 0; v < grafoCopia.length; v++) {
            if (grafoCopia[vertice][v] > 0) {
                // Reduzir o peso da aresta
                grafoCopia[vertice][v]--;
                grafoCopia[v][vertice]--;
    
                // Remover completamente a aresta se o peso se tornar zero
                if (grafoCopia[vertice][v] == 0) {
                    removerAresta(vertice, v);
                }
    
                return v;
            }
        }
        return -1;
    }
           

    private int encontrarVerticeInicial() {
        for (int i = 0; i < grafo.length; i++) {
            if (grauVerticeImpar(i)) {
                return i;
            }
        }
        return -1;
    }

    private int getIndexAresta(int u, int v) {
        for (int i = 0; i < arestas.size(); i++) {
            Aresta aresta = arestas.get(i);
            if ((aresta.getU() == u && aresta.getW() == v) ||
                (aresta.getU() == v && aresta.getW() == u)) {
                return i;
            }
        }
        return -1;
    }

    private boolean grauVerticeImpar(int vertice) {
        // Verificar se o grau do vértice é ímpar
        int grau = 0;
        for (int i = 0; i < grafo[vertice].length; i++) {
            if (grafo[vertice][i] == 1) {
                grau++;
            }
        }
        return grau % 2 != 0;
    }

    private int grauVertice(int vertice) {
        // Verificar o grau do vértice
        int grau = 0;
        for (int i = 0; i < grafo[vertice].length; i++) {
            if (grafo[vertice][i] == 1) {
                grau++;
            }
        }
        return grau;
    }
}