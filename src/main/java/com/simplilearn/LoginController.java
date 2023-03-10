package com.simplilearn;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet implementation class LoginController
 */
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	String uName = null;
	String pWord = null;
	
	public void init(ServletConfig config)
	{
		uName = config.getInitParameter("USERNAME");
		pWord = config.getInitParameter("PASSWORD");
	}
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		RequestDispatcher rd = null;
		if(username.equalsIgnoreCase(uName) && password.equals(pWord))
		{
			rd = request.getRequestDispatcher("SuccessServlet");
			rd.forward(request, response);
		}
		else
		{
			rd = request.getRequestDispatcher("index.html");
			PrintWriter out = response.getWriter();
			rd.include(request, response);
			out.println("<center><span style='color:red'>Sorry! Invalid Credentials!! Try Again...</span></center>");
			out.close();
		}
	}

}
