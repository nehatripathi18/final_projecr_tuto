package ControllerServlets;


import java.io.IOException;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.Connection;


/**
 * Servlet implementation class DeleteCourseServlet
 */
@WebServlet("/DeleteCourseServlet")
public class DeleteCourseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/*protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}*/

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
	       
			Class.forName("com.mysql.jdbc.Driver");
			java.sql.Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/TutoPointDB", "root", "root");
			 String id= request.getParameter("id");
			 Statement st=c.createStatement();
			 String sql = "delete from coursemain where CourseId ='"+ id+"'";
		     st.executeUpdate(sql);
		     request.getRequestDispatcher("CourseUpload.jsp").forward(request, response);
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		
		
	}

}