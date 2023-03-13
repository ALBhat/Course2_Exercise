package com.simpli;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JDBCSearchIDDemo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		out.write("<html>");

		//Step1: Register the JDBC Driver
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		//Step2: Load the properties file and Connect to the database;
		InputStream in = getServletContext().getResourceAsStream("/WEB-INF/databaseproperties");
		Properties props = new Properties();
		props.load(in);
		String url = props.getProperty("url");
		String userid = props.getProperty("userid");
		String password = props.getProperty("password");
		
		//Step3: connect to the database
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(url, userid, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// STEP 4 Create Statement object;
		Statement stmt = null;
		try {
			stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = null;
			String sId = request.getParameter("productid");
			
			rs = stmt.executeQuery("Select * from EPRODUCT where id=" + sId);
			rs.last();
			int rowCount = rs.getRow();
			rs.beforeFirst();
			if (rowCount == 0)
			{
				RequestDispatcher rd = request.getRequestDispatcher("search.html");
				rd.include(request, response);
				out.write("<center><span style='color:red'>NO MATCHING PRODUCT FOUND!! Try Again...</span></center>");
			}
			else
			{
				out.write("<table border=2>");
				while (rs.next()) {
					String ID = rs.getString("id");
					String name = rs.getString("name");
					float price = rs.getFloat("price");
					String date_added = rs.getString("date");
					System.out.println(ID + name + price);
					// Display the info on the webpage
					out.write("<tr>" + "<td>" + ID + "<td>" + name + "<td>" + price + "<td>" + date_added);
				}
				out.write("</table>");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// close the DB connection and the writer
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		out.write("</html>");
		out.close();
	}
}
