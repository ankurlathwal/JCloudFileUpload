/*
 * @AnkurLathwal
 * Binfer Project
 * This Servlet allows the user to login.
 * It checks the user credentials (from the form input) in the database.
 * If username/password are correct - redirect to account page.
 * If not, redirect again to login page with error message
*/

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;


public class Authenticate extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Register JDBC Driver
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DatabaseURL = "jdbc:mysql://localhost/JCLOUDFileUpload";

    // Database username and password
    static final String USERNAME = "root";
    static final String PASSWORD = "january31";



    public Authenticate() {
        super();
        // TODO Auto-generated constructor stub
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Connection connection = null;
        Statement statement = null;
        String redirect = "";

        try{
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DatabaseURL,USERNAME,PASSWORD);
            statement = connection.createStatement();
            String query;

            // get username/password entered by user in login form
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            // check if user exists in table
            query = "SELECT COUNT(*) FROM USERS WHERE username = '" + username + "' and password = '" + password +"'";
            ResultSet result = statement.executeQuery(query);
            result.next();
            int count = result.getInt(1);

            // redirect accordingly
            if(count>0){
                request.getSession(true).setAttribute("username",username);
                request.setAttribute("username",username );
                redirect = "/index.jsp";
            }
            else{
                redirect = "/login.jsp";
                request.setAttribute("message", "Incorrect Username/Password");

            }


            statement.close();
            connection.close();



        }

        catch(SQLException ex){

            ex.printStackTrace();
            request.setAttribute("message", "Couldn't login due to following error" + ex);
        }

        catch(Exception ex){
            ex.printStackTrace();
            request.setAttribute("message", "Couldn't login due to following error" + ex);
        }

        finally {
            try{
                if(statement != null)
                    statement.close();

            }
            catch(SQLException ex){
                ex.printStackTrace();
            }




        }

        request.getRequestDispatcher(redirect).forward(request, response);
    }
}
