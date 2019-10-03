package ControllerServlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.ProcessBuilder.Redirect;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.Transaction;
import Entities.Users;
import Services.ValidatePasswordEmail;

@WebServlet("/SignupServlet")
public class SignupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction transaction = null;
		try {
			session = Entities.HButil.getSessionFactory().openSession();
			transaction=session.getTransaction();
			if(!transaction.isActive()) 
				transaction.begin();

			Users user=new Users();

			user.setName(request.getParameter("name"));
			user.setEmail(request.getParameter("email"));
			user.setPassword(request.getParameter("psw"));
			PrintWriter out = response.getWriter();  

			String password=request.getParameter("psw");

			try {
				Class.forName("com.mysql.jdbc.Driver");
				Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/TutoPointDB", "root", "root");
				PreparedStatement ps = c.prepareStatement("select * from User where Email=?");
				ps.setString(1, request.getParameter("email"));
				ResultSet rs = ps.executeQuery();
				Boolean status = rs.next();
				if(status)
				{
				RequestDispatcher rd=request.getRequestDispatcher("register.html");  
				rd.include(request,response);  
				out.print("<center><b><p style=\"color:red\">Email Already exist</p></b></center>");  
				
				}
				else {
					if(ValidatePasswordEmail.validationpass(password))
					{
						session.save(user);
						transaction.commit();

						RequestDispatcher rd=request.getRequestDispatcher("login.html");  
						rd.include(request,response);  

					}

					else
					{    RequestDispatcher rd=request.getRequestDispatcher("register.html");  
					rd.include(request,response);  
					out.print("<center><b><p style=\"color:red\">PLEASE MATCH PASSWORD PATTERN</p></b></center>");  


					}
					}
				}

			catch (Exception e) {
				// TODO: handle exception
				System.out.println(e);
			} }
		catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		} 
			finally {
			if (session != null) {
				session.close();
			}
		}


		
	


		
	}}