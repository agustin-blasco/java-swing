package src.view;
import java.awt.*;
import java.time.format.DateTimeFormatter;

import javax.swing.*;
import java.util.List;

import src.model.Evento;

public class TarjetaEventos extends JPanel {

    private int cardActual = 1;
    private int numeroDeEventos;
    private JPanel cardPanel;

    public TarjetaEventos() {

        JPanel mainPanel = new JPanel(new GridLayout(2,1,10,10));
        JPanel buttonPanel = new JPanel();
        cardPanel = new JPanel();

        CardLayout layout = new CardLayout();
        cardPanel.setLayout(layout);

        JButton btnEventoProximo = new JButton("→");
        btnEventoProximo.setFont(new Font("Arial", Font.BOLD, 12));
        JButton btnEventoAnterior = new JButton("←");
        btnEventoAnterior.setFont(new Font("Arial", Font.BOLD, 12));

        buttonPanel.add(btnEventoAnterior);
        buttonPanel.add(btnEventoProximo);

        btnEventoProximo.addActionListener(e -> {
            if (cardActual < numeroDeEventos) {
                cardActual += 1;
                layout.show(cardPanel, "" + (cardActual));
            }
        });
        
        btnEventoAnterior.addActionListener(e -> {
            if (cardActual > 1) {
                cardActual -= 1;
                layout.show(cardPanel, "" + (cardActual));
            }
        });

        mainPanel.add(cardPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    public JPanel crearTarjeta(Evento evento) {
        JPanel panelEvento = new JPanel(new GridLayout(5, 1, 10, 10));
        panelEvento.add(new JLabel("Nombre del Evento: " + evento.getNombre()));
        panelEvento.add(new JLabel("Descripción: " + evento.getDescripcion()));
        panelEvento.add(new JLabel("Fecha: " + evento.getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
        panelEvento.add(new JLabel("Ubicación: " + evento.getUbicacion()));
        panelEvento.add(new JLabel("Asistentes: " + evento.getAsistentes()));
        return panelEvento;
    }

    public void crearTarjetas(List<Evento> eventos) {
        numeroDeEventos = eventos.size();
        
        if (numeroDeEventos < 1) {
            JPanel panelEvento = new JPanel(new GridLayout(5, 1, 10, 10));
            JLabel sinEventosLabel = new JLabel("No hay ningún evento próximo :(");
            sinEventosLabel.setHorizontalAlignment(SwingConstants.CENTER);
            panelEvento.add(sinEventosLabel);
            cardPanel.add(panelEvento, BorderLayout.EAST);
        } else {
            int constraint = 1;
            for (Evento evento : eventos) {
                cardPanel.add(crearTarjeta(evento), String.valueOf(constraint));
                constraint += 1;
            }
        }
    }
}