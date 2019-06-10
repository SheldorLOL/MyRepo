package net.antra;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FirstServlet extends HttpServlet {

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userName = request.getParameter("username");
		String passWord = request.getParameter("password");
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/my_schema", "root", "Southsea930626");
			con.setAutoCommit(false);
			String sql = "select password from User where username = ?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, userName);
			ResultSet rs = pstmt.executeQuery();
			
			PrintWriter out = response.getWriter();

			if (rs.next()) {
				String pwd = rs.getString("password");
					
				if (pwd.equals(passWord)) {
					out.print("<body>");
					out.print("<h1>Welcome!!!<h1>");
					out.print("User: " + userName);
					out.print("<br><a href='/MyServlet/login.html'>click here to logout</a></body>");
					out.print("<br><a href='/MyServlet/department.html'>Add Department</a></body>");
					out.print("<br><a href='/MyServlet/employee.html'>Add Employee</a></body>");

				} else {
					out.print("<body>");
					out.print("<h1>Login Failure! Wrong password!!!<h1>");
					out.print("<a href='/MyServlet/login.html'>Try again</a></body>");

				}
			} else {
				out.print("<body>");
				out.print("<h1>Login Failure! Invalid Username!!!<h1>");
				out.print("<a href='/MyServlet/login.html'>Try again</a></body>");
			}
//			pstmt.addBatch();
//			con.commit();
//			con.rollback();
			
			pstmt.close();
			con.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
