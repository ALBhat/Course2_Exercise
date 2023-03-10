package com.simplilearn;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

/**
 * Servlet implementation class SuccessServlet
 */
public class SuccessServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		out.print("<html><body>");
		out.print("<h4> Welcome '" + request.getParameter("username") +
				"'. Login Successful at " + new Date() + "</h4>");
		out.print("<a href= \"logout\">Logout</a>");
		out.print("</html></body>");
		out.close();
		
	}
	

}
