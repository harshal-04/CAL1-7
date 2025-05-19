package com.bookshop;

import java.util.*;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;

public class BookDAO {

    static SessionFactory factory = new Configuration().configure().buildSessionFactory();

    public void addBook(Book book) {
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();
        session.save(book);
        tx.commit();
        session.close();
    }

    public List<Book> listBooks() {
        Session session = factory.openSession();
        List<Book> books = session.createQuery("from Book", Book.class).list();
        session.close();
        return books;
    }

    public void deleteBook(int id) {
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();
        Book b = session.get(Book.class, id);
        if (b != null) session.delete(b);
        tx.commit();
        session.close();
    }

    public void updateBook(int id, double newPrice) {
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();
        Book b = session.get(Book.class, id);
        if (b != null) b.setPrice(newPrice);
        session.update(b);
        tx.commit();
        session.close();
    }
}
