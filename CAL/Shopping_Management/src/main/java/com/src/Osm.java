package com.src;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/Osm")
public class Osm extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");
        PrintWriter pw = response.getWriter();

        String url = "jdbc:mysql://localhost:3307/online_shop";
        String usr = "root";
        String pass = "";

        int choice = 0;
        try {
            choice = Integer.parseInt(request.getParameter("action"));
        } catch (NumberFormatException ex) {
            pw.println("❌ Invalid 'action' parameter. Use 1=Add, 2=Update, 3=Delete, 4=View.");
            return;
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection con = DriverManager.getConnection(url, usr, pass)) {
                if (choice == 1) {
                    // Add
                    try {
                        int id = Integer.parseInt(request.getParameter("id"));
                        String name = request.getParameter("name");
                        double price = Double.parseDouble(request.getParameter("price"));
                        int quantity = Integer.parseInt(request.getParameter("quantity"));

                        String query = "INSERT INTO product (id, name, price, quantity) VALUES (?, ?, ?, ?)";
                        try (PreparedStatement ps = con.prepareStatement(query)) {
                            ps.setInt(1, id);
                            ps.setString(2, name);
                            ps.setDouble(3, price);
                            ps.setInt(4, quantity);
                            int rows = ps.executeUpdate();
                            pw.println("✅ Product added successfully. Rows inserted: " + rows);
                        }
                    } catch (Exception ex) {
                        pw.println("❌ Error adding product: " + ex.getMessage());
                    }

                } else if (choice == 2) {
                    // Update
                    try {
                        int id = Integer.parseInt(request.getParameter("id"));
                        int quantity = Integer.parseInt(request.getParameter("quantity"));

                        String query = "UPDATE product SET quantity=? WHERE id=?";
                        try (PreparedStatement ps = con.prepareStatement(query)) {
                            ps.setInt(1, quantity);
                            ps.setInt(2, id);
                            int rows = ps.executeUpdate();
                            pw.println("✅ Product quantity updated. Rows affected: " + rows);
                        }
                    } catch (Exception ex) {
                        pw.println("❌ Error updating product: " + ex.getMessage());
                    }

                } else if (choice == 3) {
                    // Delete
                    try {
                        int id = Integer.parseInt(request.getParameter("id"));

                        String query = "DELETE FROM product WHERE id=?";
                        try (PreparedStatement ps = con.prepareStatement(query)) {
                            ps.setInt(1, id);
                            int rows = ps.executeUpdate();
                            pw.println("✅ Product deleted. Rows affected: " + rows);
                        }
                    } catch (Exception ex) {
                        pw.println("❌ Error deleting product: " + ex.getMessage());
                    }

                } else if (choice == 4) {
                    // View
                    String query = "SELECT * FROM product";
                    try (PreparedStatement ps = con.prepareStatement(query);
                         ResultSet rs = ps.executeQuery()) {
                        while (rs.next()) {
                            pw.println("🛒 ID: " + rs.getInt("id"));
                            pw.println("Name: " + rs.getString("name"));
                            pw.println("Price: ₹" + rs.getDouble("price"));
                            pw.println("Quantity: " + rs.getInt("quantity"));
                            pw.println("------------------------------");
                        }
                    }
                } else {
                    pw.println("❌ Invalid action number.");
                }

            }
        } catch (Exception e) {
            pw.println("❌ Error: " + e.getMessage());
        }
    }
}
