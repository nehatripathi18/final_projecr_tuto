package Services;

import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.io.*;
import java.io.InputStream;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.*;

public class DBtoPDF {
    
    Connection connection;
     Statement statement;
     ResultSet rs;
    PreparedStatement pst;
    Blob blob;
     InputStream in;
    InputStream input;
    OutputStream output;

   /* public DBtoPDF() {
        
        // database connectivity
        try {
            
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/tutopointdb","root","root");

        } catch (ClassNotFoundException cnf)
        {
            cnf.printStackTrace();
        } 
        catch (java.sql.SQLException sql) 
        {
            sql.printStackTrace();
        }
        
    }*/

    public  void fetchFile() {
        try {
        	 System.out.println("db to pdf enter1");
            statement = connection.createStatement();
            
            //query to fetch file from database
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
        } catch (Exception e) {
            System.out.println(e);
        }

    }

   /* public static void main(String args[]) {
        
        // create instance of class
        DBtoPDF obj = new DBtoPDF();
        
        // call to method
        obj.fetchFile();
        
        // message after successful execution of program
        System.out.println("Data extracted successfully!!!");
        
    }*/
}