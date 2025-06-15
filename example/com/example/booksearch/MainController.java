package com.example.booksearch;

import java.util.List;

public class MainController {
    private BookModel model;
    private MainView mainView;
    private SearchView searchView;
    private BookFormView bookFormView;
    
    public MainController(BookModel model, MainView mainView) {
        this.model = model;
        this.mainView = mainView;
        
        // Set up main menu button listeners
        mainView.setSearchButtonListener(e -> showSearchView());
        mainView.setNewBookButtonListener(e -> showNewBookForm());
        mainView.setEditBookButtonListener(e -> showEditBookForm());
    }
    
    private void showSearchView() {
        searchView = new SearchView();
        searchView.setSearchButtonListener(e -> {
            String query = searchView.getSearchQuery();
            List<Book> results = model.searchBooks(query);
            searchView.displayResults(results);
        });
        searchView.setBackButtonListener(e -> {
            searchView.dispose();
            mainView.setVisible(true);
        });
        mainView.setVisible(false);
        searchView.setVisible(true);
    }
    
    private void showNewBookForm() {
        bookFormView = new BookFormView("Add New Book");
        bookFormView.setSaveButtonListener(e -> {
            Book newBook = bookFormView.getBookData();
            model.addBook(newBook);
            bookFormView.dispose();
            mainView.setVisible(true);
        });
        bookFormView.setCancelButtonListener(e -> {
            bookFormView.dispose();
            mainView.setVisible(true);
        });
        mainView.setVisible(false);
        bookFormView.setVisible(true);
    }
    
    private void showEditBookForm() {
        // Show search view first to select a book to edit
        showSearchView();
        // Add edit functionality to the search view
        searchView.getResultTable().addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) { // Double click
                    int row = searchView.getResultTable().getSelectedRow();
                    if (row != -1) {
                        Book selectedBook = new Book(
                            (String)searchView.getResultTable().getValueAt(row, 0),
                            (String)searchView.getResultTable().getValueAt(row, 1),
                            (String)searchView.getResultTable().getValueAt(row, 2)
                        );
                        showEditForm(selectedBook);
                    }
                }
            }
        });
    }
    
    private void showEditForm(Book book) {
        bookFormView = new BookFormView("Edit Book");
        bookFormView.setBookData(book);
        bookFormView.setSaveButtonListener(e -> {
            Book updatedBook = bookFormView.getBookData();
            model.updateBook(book, updatedBook);
            bookFormView.dispose();
            mainView.setVisible(true);
        });
        bookFormView.setCancelButtonListener(e -> {
            bookFormView.dispose();
            mainView.setVisible(true);
        });
        searchView.dispose();
        bookFormView.setVisible(true);
    }
}