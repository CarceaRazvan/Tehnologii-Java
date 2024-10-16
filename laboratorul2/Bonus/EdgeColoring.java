package org.example.laboratorul2.bonus;

import org.graph4j.Edge;
import org.graph4j.Graph;
import org.graph4j.GraphBuilder;

import java.util.*;

public class EdgeColoring {

    private List<String> fileLines;
    private Graph graph = null;
    public int totalColors = 0;

    public EdgeColoring(List<String> fileLines) {

        this.fileLines = fileLines;
    }

    public Graph loadGraphFromDIMACS() {

        int nrVertices = 0;

        for (String fileLine : fileLines) {

            if (fileLine.startsWith("c")) {

                continue;
            }

            if (fileLine.startsWith("p")) {

                String[] splitedLine = fileLine.split(" ");

                nrVertices = Integer.parseInt(splitedLine[2]);

                graph = GraphBuilder.numVertices(nrVertices).buildGraph();
            }

            if (graph != null) {

                if (fileLine.startsWith("e")) {

                    String[] splitedLine = fileLine.split(" ");

                    int source = Integer.parseInt(splitedLine[1]);
                    int target = Integer.parseInt(splitedLine[2]);

                    graph.addEdge(new Edge(source - 1, target - 1));
                }
            }

        }

        return graph;
    }

    public Map<Edge, Integer> greedyColorEdges(Graph graph) {

        Map<Edge, Integer> edgeColors = new HashMap<>();
        Set<Integer> usedColors = new HashSet<>();

        for (Edge edge : graph.edges()) {

            usedColors.clear();

            for (Edge adjacent : getNeighborsEdge(edge)) {
                if (edgeColors.containsKey(adjacent)) {
                    usedColors.add(edgeColors.get(adjacent));
                }
            }

            int color = 0;
            while (usedColors.contains(color)) {
                color++;
            }

            if (totalColors < color) {
                totalColors = color;
            }

            edgeColors.put(edge, color);

        }

        return edgeColors;

    }

    private List<Edge> getNeighborsEdge(Edge edge) {

        List<Edge> neighbors = new ArrayList<>();

        int source = edge.source();
        int target = edge.target();

        int[] verticesNeighboursSource = graph.neighbors(source);
        int[] verticesNeighboursTarget = graph.neighbors(target);

        for (int neighBor : verticesNeighboursSource) {

            if (neighBor != target)
                neighbors.add(new Edge(source, neighBor));
        }


        for (int neighBor : verticesNeighboursTarget) {
            if (neighBor != source)
                neighbors.add(new Edge(target, neighBor));
        }

        return neighbors;
    }
}
