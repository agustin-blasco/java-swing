// Main.java
package com.example.booksearch;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BookModel model = new BookModel();
            MainView mainView = new MainView();
            MainController controller = new MainController(model, mainView);
            
            mainView.setVisible(true);
        });
    }
}