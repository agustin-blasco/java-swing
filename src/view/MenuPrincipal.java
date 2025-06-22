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
    private JButton btnAdministraroEventos;
    private JButton btnAdministrarAsistentes;
    private JPanel btnPanel;
    private JPanel eventosCardsPanel;
    private JLabel lblTituloEventos;
    private TarjetaEventos tarjetaEventos;

    public MenuPrincipal() {
        setTitle("Menú Principal");
        setSize(550, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        eventos = ModelosManager.cargarEventos();
        asistentes = ModelosManager.cargarAsistentes();

        tarjetaEventos = new TarjetaEventos();

        btnPanel = new JPanel();
        eventosCardsPanel = new JPanel();
        eventosCardsPanel.setLayout(new BoxLayout(eventosCardsPanel, BoxLayout.Y_AXIS));
        eventosCardsPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));

        lblTituloEventos = new JLabel("Próximos Eventos");
        lblTituloEventos.setFont(new Font("Calibri", Font.BOLD, 25));
        lblTituloEventos.setAlignmentX(Component.CENTER_ALIGNMENT);
        eventosCardsPanel.add(lblTituloEventos);
        eventosCardsPanel.add(tarjetaEventos);
        
        // Botones Menu Principal
        btnAdministraroEventos = new JButton("Buscar/Administrar Eventos");
        btnAdministrarAsistentes = new JButton("Buscar/Administrar Asistentes");

        btnAdministraroEventos.addActionListener(e -> mostrarMenuBusquedaEventos());
        btnAdministrarAsistentes.addActionListener(e -> mostrarMenuAdminAsistentes());

        btnPanel.add(btnAdministraroEventos);
        btnPanel.add(btnAdministrarAsistentes);

        add(eventosCardsPanel, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);

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
