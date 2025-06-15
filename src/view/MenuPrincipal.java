
package src.view;

import src.model.Evento;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MenuPrincipal extends JFrame {
    
    public static List<Evento> eventos;
    public JButton btnBuscarEventos;
    public JButton btnNuevoEvento;

    public MenuPrincipal() {
        setTitle("Menu Principal");
        setSize(640, 480);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4,1,10,10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        btnBuscarEventos = new JButton("Buscar Eventos");
        btnNuevoEvento = new JButton("Nuevo Evento");

        panel.add(btnBuscarEventos);
        panel.add(btnNuevoEvento);

        add(panel);
    }
}
