package org.example.compulsory.bonus;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.graph4j.Edge;
import org.graph4j.Graph;
import org.graph4j.GraphBuilder;
import org.graph4j.spanning.WeightedSpanningTreeIterator;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

//ex2
@WebServlet(name = "bonusServlet", value = "/bonus-servlet")
public class Bonus extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        int order = Integer.parseInt(request.getParameter("order"));
        int k = Integer.parseInt(request.getParameter("k"));

        if (order <= 0) {
            response.getWriter().write("The number of vertices (order) must be greater than 0.");
            return;
        }
        if (k <= 0) {
            response.getWriter().write("The number of spanning trees (k) must be greater than 0.");
            return;
        }

        long maxSpanningTrees = (long) Math.pow(order, order - 2);

        if (k > maxSpanningTrees) {
            response.getWriter().write("The value of k cannot exceed the number of possible spanning trees.");
            return;
        }

        //ex1
        Map<Collection<Edge>, Integer> sortedMap = getAllSpanningTreesOrderedByWeight(order, k);

        //ex2
        String userAgent = request.getHeader("User-Agent");

        if (isDesktopClient(userAgent)) {

            //ex3
            response.setContentType("text/plain");
            PrintWriter out = response.getWriter();

            for (Map.Entry<Collection<Edge>, Integer> entry : sortedMap.entrySet()) {

                out.print(entry.getKey() + " " + entry.getValue());
                out.println();
            }

        }

        else {

            response.setContentType("text/html");
            PrintWriter out = response.getWriter();

            out.println("<html>");
            out.println("<head><title>Spanning Trees</title></head>");
            out.println("<body>");
            out.println("<h1>Spanning Trees and their Weights</h1>");

            out.println("<table border='1'>");
            out.println("<tr><th>Spanning Tree</th><th>Weight</th></tr>");


            for (Map.Entry<Collection<Edge>, Integer> entry : sortedMap.entrySet()) {

                out.println("<tr>");
                out.println("<td>" + entry.getKey() + "</td>");
                out.println("<td>" + entry.getValue() + "</td>");
                out.println("</tr>");
            }

            out.println("</table>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    //ex1
    private Map<Collection<Edge>, Integer>  getAllSpanningTreesOrderedByWeight(int order, int k) {

        int count = 0;
        Graph graph = GraphBuilder.numVertices(order).buildGraph();

        for (int i = 0; i < order; i++) {
            for (int j = 0; j < order; j++) {

                if (i != j) {
                    int weight = i + j;

                    graph.addEdge(new Edge(i, j, weight));
                }
            }
        }

        Map<Collection<Edge>, Integer> spanningTreesMap = new LinkedHashMap<>();
        WeightedSpanningTreeIterator spanningTreeIterator = new WeightedSpanningTreeIterator(graph);

        while (spanningTreeIterator.hasNext() && count < k) {

            Collection<Edge> spanningTreeEdges = spanningTreeIterator.next();
            spanningTreesMap.put(spanningTreeEdges.stream().toList(), calculateTreeWeight(spanningTreeEdges));

            count++;
        }


        return spanningTreesMap;
    }

    private int calculateTreeWeight(Collection<Edge> edges) {

        int totalWeight = 0;
        for (Edge edge : edges) {

            totalWeight += edge.source() + edge.target();
        }
        return totalWeight;
    }

    private boolean isDesktopClient(String userAgent) {
        if (userAgent == null) {
            return true;
        }

        String[] browserIdentifiers = { "Mozilla", "Chrome", "Safari", "Firefox", "Edge", "Edg" };

        for (String identifier : browserIdentifiers) {
            if (userAgent.contains(identifier)) {
                return false;
            }
        }

        return true;
    }
}
