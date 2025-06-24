package src.view;

import src.model.Asistente;
import src.model.Evento;
import src.model.ModelosManager;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MenuPrincipal extends JFrame {
    
    public static List<Evento> eventos;
    public static List<Asistente> asistentes;
    private TarjetaEventos tarjetaEventos;

    public MenuPrincipal() {
        setTitle("Menú Principal");
        setSize(550, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        eventos = ModelosManager.cargarEventos();
        asistentes = ModelosManager.cargarAsistentes();

        tarjetaEventos = new TarjetaEventos();

        JPanel panelBotones = new JPanel();
        JPanel eventosCardsPanel = new JPanel();
        eventosCardsPanel.setLayout(new BoxLayout(eventosCardsPanel, BoxLayout.Y_AXIS));
        eventosCardsPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));

        JLabel lblTituloEventos = new JLabel("Próximos Eventos");
        lblTituloEventos.setFont(new Font("Calibri", Font.BOLD, 25));
        lblTituloEventos.setAlignmentX(Component.CENTER_ALIGNMENT);
        eventosCardsPanel.add(lblTituloEventos);
        eventosCardsPanel.add(tarjetaEventos);
        
        JButton btnAdministrarEventos = new JButton("Buscar/Administrar Eventos");
        JButton btnAdministrarAsistentes = new JButton("Buscar/Administrar Asistentes");

        btnAdministrarEventos.addActionListener(e -> mostrarMenuBusquedaEventos());
        btnAdministrarAsistentes.addActionListener(e -> mostrarMenuAdminAsistentes());

        panelBotones.add(btnAdministrarEventos);
        panelBotones.add(btnAdministrarAsistentes);

        add(eventosCardsPanel, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        mostrarMenuPrincipal();
    }

    public void mostrarMenuPrincipal() {
        setVisible(true);
        tarjetaEventos.crearTarjetas(ModelosManager.cargarEventosFuturosOrdenados(eventos));
    }

    public void mostrarMenuBusquedaEventos() {
        new MenuAdministrarEventos(this);
        setVisible(false);
    }

    public void mostrarMenuAdminAsistentes() {
        new MenuAdministrarAsistentes(this);
        setVisible(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MenuPrincipal());
    }
}
