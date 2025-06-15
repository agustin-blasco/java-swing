package com.example.booksearch;

import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {
    private JButton searchButton;
    private JButton newBookButton;
    private JButton editBookButton;
    
    public MainView() {
        setTitle("Book Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        
        // Create buttons
        searchButton = new JButton("Search Books");
        newBookButton = new JButton("Add New Book");
        editBookButton = new JButton("Edit Books");
        
        // Layout
        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.add(searchButton);
        panel.add(newBookButton);
        panel.add(editBookButton);
        
        add(panel);
    }
    
    public void setSearchButtonListener(java.awt.event.ActionListener listener) {
        searchButton.addActionListener(listener);
    }
    
    public void setNewBookButtonListener(java.awt.event.ActionListener listener) {
        newBookButton.addActionListener(listener);
    }
    
    public void setEditBookButtonListener(java.awt.event.ActionListener listener) {
        editBookButton.addActionListener(listener);
    }
}