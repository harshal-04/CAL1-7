package com.src;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/Tm")
public class Tm extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public Tm() {
        super();
    }

    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();

        String url = "jdbc:mysql://localhost:3307/t_management";
        String user = "root";
        String pass = "";

        String action = request.getParameter("action");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, user, pass);

            if ("insert".equalsIgnoreCase(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                String name = request.getParameter("name");
                int contact = Integer.parseInt(request.getParameter("contact"));
                String city = request.getParameter("city");
                String domain = request.getParameter("domain");

                String query = "INSERT INTO employee (id, name, contact, city, domain) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement ps = con.prepareStatement(query);
                ps.setInt(1, id);
                ps.setString(2, name);
                ps.setInt(3, contact);
                ps.setString(4, city);
                ps.setString(5, domain);

                int rows = ps.executeUpdate();
                pw.println("<h3>" + rows + " record(s) inserted successfully.</h3>");

            } else if ("update".equalsIgnoreCase(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                int contact = Integer.parseInt(request.getParameter("contact"));

                String query = "UPDATE employee SET contact=? WHERE id=?";
                PreparedStatement ps = con.prepareStatement(query);
                ps.setInt(1, contact);
                ps.setInt(2, id);

                int rows = ps.executeUpdate();
                pw.println("<h3>" + rows + " record(s) updated successfully.</h3>");

            } else if ("delete".equalsIgnoreCase(action)) {
                int id = Integer.parseInt(request.getParameter("id"));

                String query = "DELETE FROM employee WHERE id=?";
                PreparedStatement ps = con.prepareStatement(query);
                ps.setInt(1, id);

                int rows = ps.executeUpdate();
                pw.println("<h3>" + rows + " record(s) deleted successfully.</h3>");

            } else if ("display".equalsIgnoreCase(action)) {
                String query = "SELECT * FROM employee";
                PreparedStatement ps = con.prepareStatement(query);
                ResultSet rs = ps.executeQuery();

                pw.println("<h2>Employee Records:</h2>");
                while (rs.next()) {
                    pw.println("<p>ID: " + rs.getInt("id") + "<br>");
                    pw.println("Name: " + rs.getString("name") + "<br>");
                    pw.println("Contact: " + rs.getInt("contact") + "<br>");
                    pw.println("City: " + rs.getString("city") + "<br>");
                    pw.println("Domain: " + rs.getString("domain") + "</p><hr>");
                }
            } else {
                pw.println("<h3>Invalid action! Use 'insert', 'update', 'delete', or 'display'.</h3>");
            }

            pw.println("<br><a href='index.html'>Back to Home</a>");
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
            pw.println("<h3>Error: " + e.getMessage() + "</h3>");
        }
    }
}
