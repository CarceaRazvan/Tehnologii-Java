<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <meta charset="UTF-8">
    <title>Shuffled File Content</title>
</head>

<body>

<h2>Shuffled File Content</h2>

<ul>
    <%
        List<String> shuffledLines = (List<String>) session.getAttribute("shuffledLines");

        if (shuffledLines != null) {

            for (String line : shuffledLines) {
                out.println("<li>" + line + "</li>");
            }

        } else {
            out.println("<li>No file uploaded.</li>");
        }
    %>
</ul>

<a href="input.jsp">Upload Another File</a>
<br><br>

</body>

</html>
