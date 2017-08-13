<%--
  Created by IntelliJ IDEA.
  User: alath
  Date: 4/23/2017
  Time: 9:25 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Logout | JCloud File Sharing</title>
</head>
<body>

<%
    if((session.getAttribute("username") == null) || (session.getAttribute("username") == "")){
        session.setAttribute("username", null);
        session.invalidate();
        response.sendRedirect("login.jsp");

    }

    else{
        session.setAttribute("username", null);
        session.invalidate();



%>

<h3>You have successfully logged out. <a href="login.jsp">Click here to login again</h3>

<% } %>


</body>
</html>
