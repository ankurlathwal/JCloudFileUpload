/*
 * @AnkurLathwal
 * Binfer Project
 * This Servlet is uses the JCloud library to upload files to the local filesystem as blobs (using blobstore).
It also connects to the database and store the link to the file corresponding to the user who uploads the file
This Servlet is called when user clicks the upload button after selecting the file
*/

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Properties;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.Charsets;
import org.jclouds.ContextBuilder;
import org.jclouds.blobstore.BlobStore;
import org.jclouds.blobstore.BlobStoreContext;
import org.jclouds.blobstore.domain.Blob;
import org.jclouds.filesystem.reference.FilesystemConstants;
import org.jclouds.io.Payload;
import org.jclouds.io.Payloads;

import com.google.common.io.ByteSource;
import com.google.common.io.Files;
import java.sql.*;


public class JCloudServlet extends HttpServlet {

    // Register JDBC Driver
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DatabaseURL = "jdbc:mysql://localhost/JCLOUDFileUpload";

    // Database username and password
    static final String USERNAME = "root";
    static final String PASSWORD = "january31";

    // Set properties for Jcloud storage
    Properties properties = new Properties();

    // create a container to store the blob
    String containerName = "files";

    // set the upload directory to default Tomcat directory
    String uploadDirectory = System.getProperty("catalina.base") + "\\wtpwebapps\\JCloudFileUpload";
    String name = ""; //name of the file


    public JCloudServlet(){

    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // connection to database
        Connection connection = null;
        Statement statement = null;
        String redirect = "/result.jsp";  // the page where user will be redirected
        String fieldname ="";             // to get some fields from request
        String fieldvalue = "";

        properties.setProperty(FilesystemConstants.PROPERTY_BASEDIR, uploadDirectory);

        // create context for blob storage - on top of filesystem
        BlobStoreContext context = ContextBuilder.newBuilder("filesystem")
                .overrides(properties)
                .buildView(BlobStoreContext.class);

        BlobStore blobStore = context.getBlobStore();
        blobStore.createContainerInLocation(null, containerName);



        //if the form is multipart, read the file uploaded from it..
        if(ServletFileUpload.isMultipartContent(request)){
            try {


                List<FileItem> multiparts = new ServletFileUpload(
                        new DiskFileItemFactory()).parseRequest(request);


                for(FileItem item : multiparts){
                    if(!item.isFormField()){

                        // get the file and its name (qith extention)
                        name = new File(item.getName()).getName();

                        // store it to native File object (from FileItem)
                        File file = new File(name);
                        item.write(file);

                        // create a blob from the uploaded file
                        Blob blob = blobStore.blobBuilder(name)
                                .payload(file)
                                .contentLength(file.length())
                                .build();
                        blobStore.putBlob(containerName, blob);
                        // store the blob (uploaded file) in the container
                    }

                    else{
                        // get the name of the user (since not using cookies as yet)
                        fieldname = item.getFieldName();
                        fieldvalue = item.getString();

                    }

                }

                //If File is uploaded successfully
                // connect to the database to store the link (for later retrieval)
                Class.forName(JDBC_DRIVER);
                connection = DriverManager.getConnection(DatabaseURL,USERNAME,PASSWORD);
                statement = connection.createStatement();
                String query="";
                String username ="";
                if (fieldname.equals("username")){
                    username = fieldvalue;
                }
                String link = "/JCloudFileUpload/" + containerName + "/" + name;

                query = "insert into files values ('" + username + "','" + link + "')";
                if(statement.executeUpdate(query)==0){
                    request.setAttribute("message", "File couldn't be uploaded. Please try again.");
                    request.setAttribute("username",username );
                    redirect = "/index.jsp";
                }
                statement.close();
                connection.close();

                // send message and link to result page
                request.setAttribute("message", "File Uploaded Successfully. Below is the download link (copy and paste in new tab/browser).");
                request.setAttribute("link","http:/localhost:8080/JCloudFileUpload" + "/" +containerName + "/" + name);
            }
            catch(SQLException ex){

                ex.printStackTrace();
                request.setAttribute("message", "Couldn't login due to following error" + ex);
            }

            catch (Exception ex) {
                request.setAttribute("message", "File Upload Failed due to " + ex);
            }

        }

        else{
            request.setAttribute("message",
                    "Sorry this Servlet only handles file upload request");
        }
        context.close();
        // send user to appropriate page
        request.getRequestDispatcher(redirect).forward(request, response);

    }

}