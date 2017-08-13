<%@page isELIgnored="false" contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Account | JCloud File Sharing</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">
</head>

<body>
<div class="container">
    <div class="row">
        <h2>Welcome to our JCLOUD File Sharing Service. This is your account page.</h2><a role="button" class="btn btn-danger" href="logout.jsp">Logout</a>
    </div>
    <div class="row">
        <div class="col-xs-12 col-sm-12 col-md-6 col-md-offset-3 col-lg-4 col-lg-offset-4">
            <h3>JCloud File Sharing <small></small></h3>
            <hr>
            <h3> Choose File to Upload </h3>
            <form action="upload" method="post" enctype="multipart/form-data">
                <div class="form-group">
                    <input type="file" name="file" />
                </div>
                <button type="submit" class="btn btn-primary btn-block">Upload</button>
                <input type="hidden" name="username" value=${requestScope["username"]} />
            </form>
            <hr>
        </div>
    </div>
    <div class="row">

        <%@page import="java.sql.*"%>
        <%
            if((session.getAttribute("username") == null) || (session.getAttribute("username") == "")){

                response.sendRedirect("login.jsp");


            }

            else {


                // Register JDBC Driver
                String JDBC_DRIVER = "com.mysql.jdbc.Driver";
                String DatabaseURL = "jdbc:mysql://localhost/JCLOUDFileUpload";

                // Database username and password
                String USERNAME = "root";
                String PASSWORD = "january31";

                Connection connection = null;
                Statement statement = null;

                Class.forName(JDBC_DRIVER);
                connection = DriverManager.getConnection(DatabaseURL, USERNAME, PASSWORD);
                statement = connection.createStatement();
                String query;
                String user = request.getParameter("username");

                query = "SELECT link FROM FILES WHERE username = '" + session.getAttribute("username") + "'";
                ResultSet result = statement.executeQuery(query);
                out.println("<h3>Hi " + request.getSession(false).getAttribute("username") + "! Here is the list of your previously uploaded files:</h3>");
                out.println("<ul class='list-group'>");
                while (result.next()) {
                    String link = result.getString("link");
                    out.println("<li class='list-group-item'><a href='" + link + "'>" + link + "</a></li>");


                }
                out.println("</ul>");

                statement.close();
                connection.close();
            }
        %>
    </div>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
</body>
</html>

