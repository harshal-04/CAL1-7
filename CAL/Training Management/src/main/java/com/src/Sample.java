package com.src;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Servlet implementation class TM
 */
@WebServlet("/Sample")
public class Sample extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Constructor (calls the parent HttpServlet constructor)
    public Sample() {
        super();
    }

    // Service method will handle all types of requests (GET/POST)
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain"); // Set response type as plain text
        PrintWriter pw = response.getWriter(); // Writer object to send output to client

        // Database connection details
        String url = "jdbc:mysql://localhost:3307/t_management";
        String usr = "root";
        String pass = "";

        // Get 'ch' parameter from the request
        int x = 0;
        try {
            x = Integer.parseInt(request.getParameter("ch")); // Convert 'ch' to int
        } catch (NumberFormatException ex) {
            pw.println("Error: Invalid input for 'ch'. It must be a number.");
            return;
        }

        try {
            // Load JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish DB connection
            try (Connection con = DriverManager.getConnection(url, usr, pass)) {
                System.out.println("Connection established successfully!");

                // Perform operations based on the value of 'ch'
                if (x == 1) {
                    // Insert operation
                    try {
                        int id = Integer.parseInt(request.getParameter("id"));
                        String nm = request.getParameter("name");
                        int cont = Integer.parseInt(request.getParameter("contact"));
                        String city = request.getParameter("city");
                        String domain = request.getParameter("domain");

                        String query = "INSERT INTO employee (id, name, contact, city, domain) VALUES (?, ?, ?, ?, ?)";
                        try (PreparedStatement ps = con.prepareStatement(query)) {
                            ps.setInt(1, id);
                            ps.setString(2, nm);
                            ps.setInt(3, cont);
                            ps.setString(4, city);
                            ps.setString(5, domain);
                            int rw = ps.executeUpdate();
                            pw.println("Number of rows inserted: " + rw);
                        }
                    } catch (NumberFormatException ex) {
                        pw.println("Error: Invalid input. Ensure 'id' and 'contact' are numeric.");
                    }
                } else if (x == 2) {
                    // Update operation (update contact based on id)
                    try {
                        int id = Integer.parseInt(request.getParameter("id"));
                        int cont = Integer.parseInt(request.getParameter("contact"));

                        String query = "UPDATE employee SET contact=? WHERE id=?";
                        try (PreparedStatement ps = con.prepareStatement(query)) {
                            ps.setInt(1, cont);
                            ps.setInt(2, id);
                            int rw = ps.executeUpdate();
                            pw.println("Number of rows updated: " + rw);
                        }
                    } catch (NumberFormatException ex) {
                        pw.println("Error: Invalid input. Ensure 'id' and 'contact' are numeric.");
                    }
                } else if (x == 3) {
                    // Delete operation
                    try {
                        int id = Integer.parseInt(request.getParameter("id"));

                        String query = "DELETE FROM employee WHERE id=?";
                        try (PreparedStatement ps = con.prepareStatement(query)) {
                            ps.setInt(1, id);
                            int rw = ps.executeUpdate();
                            pw.println("Number of rows deleted: " + rw);
                        }
                    } catch (NumberFormatException ex) {
                        pw.println("Error: Invalid input. Ensure 'id' is numeric.");
                    }
                } else if (x == 4) {
                    // Display operation (show all records)
                    String query = "SELECT * FROM employee";
                    try (PreparedStatement ps = con.prepareStatement(query);
                         ResultSet rs = ps.executeQuery()) {

                        while (rs.next()) {
                            pw.println("ID: " + rs.getInt("id"));
                            pw.println("Name: " + rs.getString("name"));
                            pw.println("Contact: " + rs.getInt("contact"));
                            pw.println("City: " + rs.getString("city"));
                            pw.println("Domain: " + rs.getString("domain"));
                            pw.println("------------------------");
                        }
                    }
                } else {
                    pw.println("Error: Invalid choice. Use 1 for Insert, 2 for Update, 3 for Delete, 4 for Display.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            pw.println("Error: " + e.getMessage());
        }
    }
}
