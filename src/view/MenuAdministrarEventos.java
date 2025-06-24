package src.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import src.model.Evento;
import src.model.ModelosManager;

public class MenuAdministrarEventos extends JFrame {
    
    private MenuPrincipal menuPrincipal;
    private JTextField txtBusquedaEvento;
    private DefaultTableModel tablaBusqueda;
    private JTable tablaDeEventos;

    public MenuAdministrarEventos(MenuPrincipal menuPrincipal) {
        this.menuPrincipal = menuPrincipal;
        setTitle("Gestor de Eventos");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        crearUI();
    }

    public void crearUI() {
        txtBusquedaEvento = new JTextField(15);
        JButton btnBusquedaEvento = new JButton("Buscar");
        JButton btnNuevoEvento = new JButton("Nuevo Evento");
        JButton btnEditarEvento = new JButton("Ver o Editar Evento");
        JButton btnInvitaciones = new JButton("Agregar o Quitar Invitados");
        JButton btnVolverMenu = new JButton("Volver al Inicio");
        tablaBusqueda = new DefaultTableModel(new Object[] {"ID", "Nombre", "Descripción", "Invitados", "Ubicación", "Fecha"}, 0);
        tablaDeEventos = new JTable(tablaBusqueda);
        JScrollPane scrollPane = new JScrollPane(tablaDeEventos);
        JPanel panelBusquedaEventos = new JPanel();
        JPanel panelAdministracionEventos = new JPanel();
        
        btnBusquedaEvento.addActionListener(e -> buscarEvento());
        btnNuevoEvento.addActionListener(e -> crearEvento());
        btnEditarEvento.addActionListener(e -> editarEvento());
        btnInvitaciones.addActionListener(e -> abrirMenuInvitados());
        btnVolverMenu.addActionListener(e -> volverAlMenuPrincipal());

        panelBusquedaEventos.add(new JLabel("Buscar en Evento: "));
        panelBusquedaEventos.add(txtBusquedaEvento);
        panelBusquedaEventos.add(btnBusquedaEvento);
        panelAdministracionEventos.setPreferredSize(new Dimension(800, 65));
        panelAdministracionEventos.add(btnNuevoEvento);
        panelAdministracionEventos.add(btnEditarEvento);
        panelAdministracionEventos.add(btnInvitaciones);
        panelAdministracionEventos.add(btnVolverMenu);
        panelBusquedaEventos.add(panelAdministracionEventos);
        
        refrescarTablaEventos();

        setLayout(new BorderLayout());
        add(panelBusquedaEventos, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    public void refrescarTablaEventos() { 
        mostrarResultados(MenuPrincipal.eventos);
    }

    public void volverAlMenuPrincipal() {
        dispose();
        menuPrincipal.mostrarMenuPrincipal();
    }

    public void buscarEvento() {
        List<Evento> resultados = buscarEnEventos(buscarSubstringEnEventos());

        if (resultados.isEmpty()) {
            JOptionPane.showMessageDialog(this, "La búsqueda no devolvió ningún Evento.");
            return;
        }
        mostrarResultados(resultados);
    }

    public String buscarSubstringEnEventos() {
        return txtBusquedaEvento.getText();
    }

    public void mostrarResultados(List<Evento> eventos) {
        tablaBusqueda.setRowCount(0);
        for (Evento evento : eventos) {
            Object[] fila = {
                evento.getId(),
                evento.getNombre(),
                evento.getDescripcion(),
                evento.getAsistentes(),
                evento.getUbicacion(),
                evento.getFecha(),
            };
            tablaBusqueda.addRow(fila);
        }
    }

    public List<Evento> buscarEnEventos(String busqueda) {
        List<Evento> resultados = new ArrayList<>();
        busqueda = busqueda.toLowerCase();
        
        for (Evento evento : MenuPrincipal.eventos) {
            if (Integer.toString(evento.getId()).contains(busqueda) ||
                evento.getNombre().toLowerCase().contains(busqueda) ||
                evento.getDescripcion().toLowerCase().contains(busqueda) ||
                Integer.toString(evento.getAsistentes()).contains(busqueda) ||
                evento.getUbicacion().toLowerCase().contains(busqueda) ||
                evento.getFecha().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).contains(busqueda)) {
                    resultados.add(evento);
            }
        }
        return resultados;
    }

    public int getUltimoID(List<Evento> eventos) {
        return eventos.get(eventos.size() - 1).getId() + 1;
    }

    public boolean esFechaValida(String fecha) {
        LocalDate fechaParsed;
        try {
            fechaParsed = LocalDate.parse(fecha, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            if (fechaParsed.isBefore(LocalDate.now())) {
                JOptionPane.showMessageDialog(this, "La fecha no puede ser anterior al dia de hoy!");
            }
            return !fechaParsed.isBefore(LocalDate.now());
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "La fecha es inválida!");
            return false;
        }
    }

    public void crearEvento() {

        JTextField nombre = new JTextField();
        JTextArea descripcion = new JTextArea();
        descripcion.setRows(3);
        JTextField ubicacion = new JTextField();
        JTextField fecha = new JTextField();

        Object[] camposNuevoEvento = {
            "Nombre del Evento:", nombre,
            "Descripción:", descripcion,
            "Ubicación:", ubicacion,
            "Fecha (aaaa-mm-dd):", fecha,
        };

        int opcion = JOptionPane.showConfirmDialog(this, camposNuevoEvento, "Agregar Evento", JOptionPane.OK_CANCEL_OPTION);
        if (opcion == JOptionPane.OK_OPTION) {
            LocalDate nuevaFecha;

            if (esFechaValida(fecha.getText())) {
                nuevaFecha = LocalDate.parse(fecha.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                Evento evento = new Evento(getUltimoID(MenuPrincipal.eventos), nombre.getText(), descripcion.getText(), 0, ubicacion.getText(), nuevaFecha);
                MenuPrincipal.eventos.add(evento);
                ModelosManager.guardarEventos(MenuPrincipal.eventos);
            } else {
                return;
            }
            refrescarTablaEventos();
        }
    }

    public boolean validarFilaSeleccionada(int fila) {
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un Evento!");
        }
        return fila == -1;
    }

    public void abrirMenuInvitados() {
        int fila = tablaDeEventos.getSelectedRow();
        if (validarFilaSeleccionada(fila)) {
            return;
        }
        
        int idEvento = Integer.parseInt(tablaDeEventos.getValueAt(fila, 0).toString()) - 1;
        Evento evento = MenuPrincipal.eventos.get(idEvento);

        MenuAdministrarInvitados menuInvitados = new MenuAdministrarInvitados(evento, MenuPrincipal.asistentes, this);
        menuInvitados.setVisible(true);
    }

    public void editarEvento() {
        int fila = tablaDeEventos.getSelectedRow();
        if (validarFilaSeleccionada(fila)) {
            return;
        }
        
        int idEvento = Integer.parseInt(tablaDeEventos.getValueAt(fila, 0).toString()) - 1;
        Evento evento = MenuPrincipal.eventos.get(idEvento);
        JTextField id = new JTextField(Integer.toString(evento.getId()));
        JTextField nombre = new JTextField(evento.getNombre());
        JTextArea descripcion = new JTextArea(evento.getDescripcion().replace("\\n", System.lineSeparator()));
        descripcion.setRows(3);
        JTextField asistentes = new JTextField(Integer.toString(evento.getAsistentes()));
        asistentes.setEnabled(false);
        JTextField ubicacion = new JTextField(evento.getUbicacion());
        JTextField fecha = new JTextField(evento.getFecha().toString());

        Object[] camposEdicionEvento = {
            "Nombre del Evento:", nombre,
            "Descripcion:", descripcion,
            "Asistentes:", asistentes,
            "Ubicacion:", ubicacion,
            "Fecha (aaaa-mm-dd):", fecha,
        };
            
        int opcion = JOptionPane.showConfirmDialog(this, camposEdicionEvento, "Editar Evento", JOptionPane.OK_CANCEL_OPTION);
        
        if (opcion == JOptionPane.OK_OPTION) {
            LocalDate nuevaFecha;

            evento.setId(Integer.parseInt(id.getText()));
            evento.setNombre(nombre.getText());
            evento.setDescripcion(descripcion.getText().replace("\n", "\\n").replace("\r", "\\r"));
            evento.setAsistentes(Integer.parseInt(asistentes.getText()));
            evento.setUbicacion(ubicacion.getText());

            if (esFechaValida(fecha.getText())) {
                nuevaFecha = LocalDate.parse(fecha.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            } else {
                return;
            }
            
            evento.setFecha(nuevaFecha);

            ModelosManager.guardarEventos(MenuPrincipal.eventos);
            refrescarTablaEventos();
        }
    }
}
