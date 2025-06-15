package com.example.booksearch;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class SearchView extends JFrame {
    private JTextField searchField;
    private JTable resultTable;
    private DefaultTableModel tableModel;
    private JButton searchButton;
    private JButton backButton;
    
    public SearchView() {
        setTitle("Search Books");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        
        // Create components
        searchField = new JTextField(20);
        searchButton = new JButton("Search");
        backButton = new JButton("Back to Main Menu");
        
        // Create table
        String[] columns = {"Title", "Author", "ISBN"};
        tableModel = new DefaultTableModel(columns, 0);
        resultTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(resultTable);
        
        // Layout
        JPanel searchPanel = new JPanel();
        searchPanel.add(new JLabel("Search: "));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(backButton);
        
        setLayout(new BorderLayout());
        add(searchPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    public String getSearchQuery() {
        return searchField.getText();
    }
    
    public void setSearchButtonListener(java.awt.event.ActionListener listener) {
        searchButton.addActionListener(listener);
    }
    
    public void setBackButtonListener(java.awt.event.ActionListener listener) {
        backButton.addActionListener(listener);
    }
    
    public void displayResults(List<Book> books) {
        tableModel.setRowCount(0);
        for (Book book : books) {
            Object[] row = {
                book.getTitle(),
                book.getAuthor(),
                book.getIsbn()
            };
            tableModel.addRow(row);
        }
    }

    // Add this getter method
    public JTable getResultTable() {
        return resultTable;
    }
}