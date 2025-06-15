package src.view;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import src.model.Evento;

public class MenuBuscarEventos extends JFrame {
    
    private JTextField busquedaEvento;
    public JButton btnBusquedaEvento;
    public JButton btnVolverMenu;
    private DefaultTableModel tabla;
    private JTable tablaDeEventos;

    public MenuBuscarEventos() {
        setTitle("Gestor de Eventos");
        setSize(640, 480);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        busquedaEvento = new JTextField(20);
        btnBusquedaEvento = new JButton("Buscar");
        btnVolverMenu = new JButton("Volver al Menu Principal");
        
        String[] columns = {"Titulo", "Descripcion", "Invitados", "Ubicacion", "Fecha"};
        tabla = new DefaultTableModel(columns, 0);
        tablaDeEventos = new JTable(tabla);
        JScrollPane scrollPane = new JScrollPane(tablaDeEventos);

        JPanel panelBusquedaEventos = new JPanel();
        panelBusquedaEventos.add(new JLabel("Buscar Evento: "));
        panelBusquedaEventos.add(busquedaEvento);
        panelBusquedaEventos.add(btnBusquedaEvento);
        panelBusquedaEventos.add(btnVolverMenu);
        
        setLayout(new BorderLayout());
        add(panelBusquedaEventos, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    public String buscarEnEventos() {
        return busquedaEvento.getText();
    }

    public void mostrarResultados(List<Evento> eventos) {
        tabla.setRowCount(0);
        for (Evento evento : eventos) {
            Object[] fila = {
                evento.getNombre(),
                evento.getDescripcion(),
                evento.getAsistentes(),
                evento.getUbicacion(),
                evento.getFecha(),
            };
            tabla.addRow(fila);
        }
    }

}
