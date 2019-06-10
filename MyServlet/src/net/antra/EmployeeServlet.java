package net.antra;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EmployeeServlet extends HttpServlet {

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String empfName = request.getParameter("efname");
		String emplName = request.getParameter("elname");
		String emp_dep = request.getParameter("edepartment");
		boolean flag = false;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/my_schema", "root", "Southsea930626");
			String insert = "insert into Employee(firstName, lastName, work_for) values(?, ?, ?)";
			PreparedStatement pstmt = con.prepareStatement(insert);
			pstmt.setString(1, empfName);
			pstmt.setString(2, emplName);
			pstmt.setString(3, emp_dep);
			pstmt.execute();
			
			pstmt.close();
			con.close();
			
			flag = true;
			PrintWriter out = response.getWriter();
			out.print("<body>");
			out.print("<h1>Insert Successfull!!!<h1>");
			out.print("<a href='/MyServlet/employee.html'>add another employeet</a>");
			out.print("<br><a href='/MyServlet/login.html'>logout</a></body>");
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (!flag) {
				PrintWriter out = response.getWriter();
				out.print("<body>");
				out.print("<h1>Insert Errors!!!<h1>");
				out.print("<a href='/MyServlet/employee.html'>Try again</a>");
				out.print("<br><a href='/MyServlet/login.html'>logout</a></body>");
			}
		}
	}
	
}
