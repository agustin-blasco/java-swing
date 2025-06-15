// BookModel.java
package com.example.booksearch;

import java.util.ArrayList;
import java.util.List;

import java.util.ArrayList;
import java.util.List;

public class BookModel {
    private List<Book> books;
    
    public BookModel() {
        books = new ArrayList<>();
        // Add sample books
        books.add(new Book("The Great Gatsby", "F. Scott Fitzgerald", "978-0743273565"));
        books.add(new Book("1984", "George Orwell", "978-0451524935"));
    }
    
    public List<Book> searchBooks(String query) {
        List<Book> results = new ArrayList<>();
        query = query.toLowerCase();
        
        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(query) ||
                book.getAuthor().toLowerCase().contains(query) ||
                book.getIsbn().toLowerCase().contains(query)) {
                results.add(book);
            }
        }
        return results;
    }
    
    public void addBook(Book book) {
        books.add(book);
    }
    
    public void updateBook(Book oldBook, Book newBook) {
        int index = books.indexOf(oldBook);
        if (index != -1) {
            books.set(index, newBook);
        }
    }
    
    public List<Book> getAllBooks() {
        return new ArrayList<>(books);
    }
}