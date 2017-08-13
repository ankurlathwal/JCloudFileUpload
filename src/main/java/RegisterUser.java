/*
 * @AnkurLathwal
 * Binfer Project
 * This Servlet allows the user to create an account.
 * It stores the user credentials (from the form input) in the database.
*/

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;


public class RegisterUser extends HttpServlet {
    private static final long serialVersionUID = 11L;

    // Register JDBC Driver
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DatabaseURL = "jdbc:mysql://localhost/JCLOUDFileUpload";

    // Database username and password
    static final String USERNAME = "root";
    static final String PASSWORD = "january31";



    public RegisterUser() {
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

            String username = request.getParameter("username");
            String email = request.getParameter("email");
            String password = request.getParameter("password");


            query = "insert into users values ('" + username + "','" + email + "','" + password + "')";
            if(statement.executeUpdate(query)==0){
                request.setAttribute("message", "User account couldn't be created. Please try again.");
                redirect = "/signup.jsp";

            }

            else{

                redirect = "/index.jsp";

            }


            statement.close();
            connection.close();



        }

        catch(SQLException ex){

            ex.printStackTrace();
            request.setAttribute("message", "Account couldn't be created due to following error" + ex);
        }

        catch(Exception ex){
            ex.printStackTrace();
            request.setAttribute("message", "Account couldn't be created due to following error" + ex);
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