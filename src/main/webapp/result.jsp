<%@page isELIgnored="false" contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Upload | JCloud File Sharing</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">
</head>

<body>
<%
    if ((session.getAttribute("username") == null) || (session.getAttribute("username") == "")) {
        response.sendRedirect("login.jsp");
    }
    else {
%>

<div class="container">
    <div class="row">
        <h3>${requestScope["message"]}</h3>
        <ul class="list-group">
            <li class='list-group-item'><a href='${requestScope["link"]}'>${requestScope["link"]}</a></li>
        </ul>
    </div>
    </br>
    <div>
        <a href="index.jsp">Click here to go to your account page</a>

    </div>
</div>



<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
</body>
</html>

<%
    }
%>