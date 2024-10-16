<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>File Upload with CAPTCHA</title>
</head>
<body>

<h2>Upload a Text File</h2>

<form action="file-upload" method="post" enctype="multipart/form-data">
    <%--@declare id="captcha"--%>
    <label for="file">Choose a file:</label>
    <input type="file" id="file" name="file" accept=".txt" required>
    <br><br>


    <img src="captcha" alt="CAPTCHA Image">
    <br><br>


    <label for="captcha">Enter the CAPTCHA:</label>
    <input type="text" id="captchaInput" name="captchaInput" required>
    <br><br>

    <input type="submit" value="Upload">

</form>

</body>
</html>