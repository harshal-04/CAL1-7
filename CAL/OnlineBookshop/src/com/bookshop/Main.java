package com.bookshop;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        BookDAO dao = new BookDAO();

        // Add book
        Book b1 = new Book();
        b1.setTitle("Java Basics");
        b1.setAuthor("James");
        b1.setPrice(399.0);
        dao.addBook(b1);

        // List all books
        List<Book> books = dao.listBooks();
        for (Book b : books) {
            System.out.println(b.getId() + " " + b.getTitle() + " " + b.getAuthor() + " " + b.getPrice());
        }

        // Update book price
        dao.updateBook(1, 450.0);

        // Delete book
        // dao.deleteBook(1);
    }
}
