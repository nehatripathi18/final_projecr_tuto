package ControllerServlets;

import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import Services.DBtoPDF;

/**
 * Servlet implementation class upload1
 */
@WebServlet("/upload1")
@MultipartConfig
public class upload1 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 protected void doPost(HttpServletRequest request,  HttpServletResponse response)       throws ServletException, IOException {
	        response.setContentType("text/html;charset=UTF-8");
      
	  final  	Part filePart=request.getPart("file");
			String coursetitle=request.getParameter("course");
			String courseDesc=request.getParameter("text");
	        
			  Connection connection;
			     Statement statement;
			     ResultSet rs;
			    PreparedStatement pst;
			    Blob blob;
			     InputStream in;
			    InputStream input;
			    OutputStream output;

   
    InputStream pdfFileBytes = null;
    final PrintWriter writer = response.getWriter();

    try {

      if (!filePart.getContentType().equals("application/pdf"))
        {
                   writer.println("<br/> Invalid File");
                   return;
        }

       else if (filePart.getSize()>1048576 ) { //2mb
           {
          writer.println("<br/> File size too big");
          return;
           }
       }

        pdfFileBytes = filePart.getInputStream();  // to get the body of the request as binary data

        final byte[] bytes = new byte[pdfFileBytes.available()];
         pdfFileBytes.read(bytes);  //Storing the binary data in bytes array.

        Connection  con=null;
         Statement stmt=null;

           try {
                 Class.forName("com.mysql.jdbc.Driver");
                 con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tutopointdb","root","root");
              } catch (Exception e) {
                    System.out.println(e);
                    System.exit(0);
                          }


            int success=0;
            PreparedStatement pstmt = con.prepareStatement("insert into coursemain(CourseName,CourseDesc,CourseFile) values(?,?,?)");
            pstmt.setString(1, coursetitle);
            pstmt.setString(2, courseDesc);

            pstmt.setBytes(3,bytes);    //Storing binary data in blob field.
            success = pstmt.executeUpdate();
            if(success>=1)  System.out.println("file Stored");
            //storing file in pdf form


             writer.println("<br/> file Successfully Stored");
            /* DBtoPDF convert=new DBtoPDF();
             convert.fetchFile();
*/				        
             statement = con.createStatement();

             rs = statement.executeQuery("select * from coursemain");
             in = null;
             while (rs.next()) {
             	String id=rs.getString(1);
                 in = rs.getBinaryStream(4);
             
                 System.out.println("db to pdf enter2");
             int available1 = in.available();
             byte[] bt = new byte[available1];
             in.read(bt);

             // new pdf file to store extracted data
             System.out.println("db to pdf enter3");
             FileOutputStream fout = new FileOutputStream("C://Users//Training//VartikaWeb//TutoPoint//WebContent//Courses-PDF//"+id+".pdf");
             DataOutputStream dout = new DataOutputStream(fout);
             dout.write(bt, 0, bt.length);
             System.out.println("db to pdf out");
             fout.close();
             }
         
             con.close(); 

    } catch (FileNotFoundException fnf) {
        writer.println("You  did not specify a file to upload");
        writer.println("<br/> ERROR: " + fnf.getMessage());

    } catch (SQLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    } finally {

        if (pdfFileBytes != null) {
            pdfFileBytes.close();
        }
        if (writer != null) {
            writer.close();
        }
    }

}

}