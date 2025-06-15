package com.example.booksearch;

import javax.swing.*;
import java.awt.*;

public class BookFormView extends JFrame {
    private JTextField titleField;
    private JTextField authorField;
    private JTextField isbnField;
    private JButton saveButton;
    private JButton cancelButton;
    
    public BookFormView(String title) {
        setTitle(title);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        
        // Create components
        titleField = new JTextField(20);
        authorField = new JTextField(20);
        isbnField = new JTextField(20);
        saveButton = new JButton("Save");
        cancelButton = new JButton("Cancel");
        
        // Layout
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        panel.add(new JLabel("Title:"));
        panel.add(titleField);
        panel.add(new JLabel("Author:"));
        panel.add(authorField);
        panel.add(new JLabel("ISBN:"));
        panel.add(isbnField);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        panel.add(buttonPanel);
        
        add(panel);
    }
    
    public void setBookData(Book book) {
        titleField.setText(book.getTitle());
        authorField.setText(book.getAuthor());
        isbnField.setText(book.getIsbn());
    }
    
    public Book getBookData() {
        return new Book(
            titleField.getText(),
            authorField.getText(),
            isbnField.getText()
        );
    }
    
    public void setSaveButtonListener(java.awt.event.ActionListener listener) {
        saveButton.addActionListener(listener);
    }
    
    public void setCancelButtonListener(java.awt.event.ActionListener listener) {
        cancelButton.addActionListener(listener);
    }
}