<%@ page isELIgnored="false" language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Sign Up | JCLOUD File Sharing </title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">
</head>


<body>
<div class="container">
    <div class="row">
        <h2>Hi! Welcome to our JCLOUD File Sharing Service. Please singup to start uploading files</h2>
    </div>
    <div class="row" id="signup-row">
        <div class="col-xs-12 col-sm-12 col-md-6 col-md-offset-3 col-lg-4 col-lg-offset-4">
            <h3>JCloud File Sharing - Signup <small></small></h3>
            <hr>

            <form action="register" method="post" id="signup-form">
                <div class="form-group">
                    <label for="username">Enter a username:</label>
                    <input type="text" class="form-control" name="username" />
                </div>
                <div class="form-group">
                    <label for="email">Enter your e-mail address:</label>
                    <input type="email" class="form-control" name="email" />
                </div>
                <div class="form-group">
                    <label for="password">Choose a password:</label>
                    <input type="password" class="form-control" name="password" id="password-value" />
                </div>
                <div class="form-group">
                    <label for="password2">Confirm password:</label>
                    <input type="password" class="form-control" name="password2" id="password-value2" />
                </div>
                <button type="submit" class="btn btn-primary btn-block">Create Account</button>

            </form>
            <p>${requestScope["message"]}</p>
        </div>
    </div>
</div>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
        <!-- Include all compiled plugins (below), or include individual files as needed -->
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/crypto-js/3.1.9-1/crypto-js.min.js"></script>

        <script>

            $('#signup-form').submit(function() {

                var pass1 = $("#password-value").val();
                var pass2 = $("#password-value2").val();

                if( pass1 == pass2 ) {
                    var encrypted = CryptoJS.SHA256($("#password-value").val());
                    $("#password-value").val(encrypted);

                    var encrypted2 = CryptoJS.SHA256($("#password-value2").val());
                    $("#password-value2").val(encrypted2);

                    return true;
                }

                else{

                    $("#signup-row").before("<div class='row'>" +
                        "<div class='col-xs-12 col-sm-12 col-md-6 col-md-offset-3 col-lg-4 col-lg-offset-4'>" +
                        "<div class='alert alert-danger'><strong>Error!</strong> The passwords you entered do not match." +
                    "</div>" +
                        "<div>" +
                        "</div> ");

                    return false;

                }
            });

        </script>
</body>
</html>
