<%@ page import="java.util.Set" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Optimized Graph Visualization</title>
    <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/vis/4.21.0/vis.min.js"></script>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/vis/4.21.0/vis.min.css" rel="stylesheet">
    <style>
        #mynetwork {
            width: 90vw;
            height: 80vh;
            border: 1px solid lightgray;
            box-sizing: border-box;
        }
    </style>
</head>
<body>

<h2>Edge Colored Graph</h2>
<h3>Number of Colors Used: ${colors}</h3>
<div id="mynetwork"></div>

<script type="text/javascript">
    const edgeColors = [
        '#FF0000', '#00FF00', '#0000FF', '#FFFF00', '#FF00FF', '#00FFFF',
        '#FFA500', '#800080', '#008000', '#FFC0CB', '#A52A2A', '#808080',
        '#000000', '#FFD700', '#00CED1', '#DC143C', '#8A2BE2', '#5F9EA0',
        '#ADFF2F', '#FF4500', '#DA70D6', '#32CD32', '#6495ED', '#9932CC',
        '#FF1493', '#4682B4', '#D2691E', '#B22222', '#2E8B57', '#FF6347',
        '#9400D3', '#7B68EE', '#4169E1', '#F4A460', '#BA55D3', '#87CEFA'
    ];

    // Define nodes and edges arrays
    var nodes = new vis.DataSet([
        <% for (String node : (Set<String>) session.getAttribute("nodes")) { %>
        {id: <%= node %>, label: 'Node <%= node %>', size: 5},  // Reduce node size
        <% } %>
    ]);

    var edges = new vis.DataSet([
        <% for (String edge : (List<String>) session.getAttribute("edges")) {
            String[] edgeParts = edge.split(",");
            String node1 = edgeParts[0];
            String node2 = edgeParts[1];
            String color = edgeParts[2];
        %>
        {
            from: <%= node1 %>,
            to: <%= node2 %>,
            color: {color: edgeColors[<%= color %>]},
            label: 'Color <%= color %>',
            smooth: false
        },
        <% } %>
    ]);


    var container = document.getElementById('mynetwork');
    var data = {
        nodes: nodes,
        edges: edges
    };

    var options = {
        layout: {
            improvedLayout: false
        },
        physics: {
            enabled: true,
            solver: 'forceAtlas2Based',
            forceAtlas2Based: {
                gravitationalConstant: -100,
                springLength: 150,
                springConstant: 0.08,
                avoidOverlap: 1
            },
            timestep: 0.35,
            maxVelocity: 100,
            stabilization: {
                enabled: true,
                iterations: 100
            }
        },
        interaction: {
            dragNodes: true,
            zoomView: true,
            dragView: true
        }
    };

    var network = new vis.Network(container, data, options);

    setTimeout(function () {
        network.setOptions({ physics: false }); // Freeze the graph
    }, 5000);
</script>

<br>

<ul>
    <%
        List<String> shuffledLines = (List<String>) session.getAttribute("linesColoring");

        if (shuffledLines != null) {

            for (String line : shuffledLines) {
                out.println("<li>" + line + "</li>");
            }

        } else {
            out.println("<li>No file uploaded.</li>");
        }
    %>
</ul>

</body>
</html>
