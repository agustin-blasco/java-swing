package src.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import src.model.Asistente;
import src.model.ModelosManager;

public class MenuAdministrarAsistentes extends JFrame {
    
    private MenuPrincipal menuPrincipal;
    private JTextField txtBusquedaAsistente;
    private DefaultTableModel tablaBusqueda;
    private JTable tablaDeAsistentes;

    public MenuAdministrarAsistentes(MenuPrincipal menuPrincipal) {
        this.menuPrincipal = menuPrincipal;
        setTitle("Gestor de Asistentes");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        crearUI();
    }

    public void crearUI() {
        txtBusquedaAsistente = new JTextField(15);
        JButton btnBusquedaAsistente = new JButton("Buscar");
        JButton btnNuevoAsistente= new JButton("Nuevo Asistente");
        JButton btnEditarAsistente = new JButton("Ver/Editar Asistente");
        JButton btnVolverMenu = new JButton("Volver al Inicio");
        tablaBusqueda = new DefaultTableModel(new Object[] {"ID", "Nombre", "Apellido", "Email"}, 0);
        tablaDeAsistentes = new JTable(tablaBusqueda);
        JScrollPane scrollPane = new JScrollPane(tablaDeAsistentes);
        JPanel panelBusquedaAsistentes = new JPanel();
        JPanel panelAdministracionAsistentes = new JPanel();
        
        btnBusquedaAsistente.addActionListener(e -> buscarAsistente());
        btnNuevoAsistente.addActionListener(e -> crearAsistente());
        btnEditarAsistente.addActionListener(e -> editarAsistente());
        btnVolverMenu.addActionListener(e -> volverAlMenuPrincipal());

        panelBusquedaAsistentes.add(new JLabel("Buscar en Asistentes: "));
        panelBusquedaAsistentes.add(txtBusquedaAsistente);
        panelBusquedaAsistentes.add(btnBusquedaAsistente);
        panelAdministracionAsistentes.setPreferredSize(new Dimension(800, 65));
        panelAdministracionAsistentes.add(btnNuevoAsistente);
        panelAdministracionAsistentes.add(btnEditarAsistente);
        panelAdministracionAsistentes.add(btnVolverMenu);
        panelBusquedaAsistentes.add(panelAdministracionAsistentes);
        
        refrescarTablaAsistentes();

        add(panelBusquedaAsistentes, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    public void volverAlMenuPrincipal() {
        dispose();
        menuPrincipal.mostrarMenuPrincipal();
    }

    public void buscarAsistente() {
        List<Asistente> resultados = buscarEnAsistentes(buscarSubstringEnAsistentes());

        if (resultados.isEmpty()) {
            JOptionPane.showMessageDialog(this, "La búsqueda no devolvió ningún Asistente.");
            return;
        }

        mostrarResultados(resultados);
    }

    public String buscarSubstringEnAsistentes() {
        return txtBusquedaAsistente.getText();
    }

    public void mostrarResultados(List<Asistente> asistentes) {
        tablaBusqueda.setRowCount(0);
        for (Asistente asistente : asistentes) {
            Object[] fila = {
                asistente.getId(),
                asistente.getNombre(),
                asistente.getApellido(),
                asistente.getEmail(),
            };
            tablaBusqueda.addRow(fila);
        }
    }

    public List<Asistente> buscarEnAsistentes(String busqueda) {
        List<Asistente> resultados = new ArrayList<>();
        busqueda = busqueda.toLowerCase();
        
        for (Asistente asistente : MenuPrincipal.asistentes) {
            if (Integer.toString(asistente.getId()).contains(busqueda) ||
                asistente.getNombre().toLowerCase().contains(busqueda) ||
                asistente.getApellido().toLowerCase().contains(busqueda) ||
                asistente.getEmail().toLowerCase().contains(busqueda)) {
                    resultados.add(asistente);
            }
        }
        return resultados;
    }

    public void refrescarTablaAsistentes() { 
        mostrarResultados(MenuPrincipal.asistentes);
    }

    public int getUltimoID(List<Asistente> asistentes) {
        return asistentes.get(asistentes.size() - 1).getId() + 1;
    }

    public void crearAsistente() {

        JTextField nombre = new JTextField();
        JTextField apellido = new JTextField();
        JTextField email = new JTextField();

        Object[] camposNuevoAsistente = {
            "Nombre:", nombre,
            "Apellido:", apellido,
            "Email:", email,
        };

        int opcion = JOptionPane.showConfirmDialog(this, camposNuevoAsistente, "Agregar Asistente", JOptionPane.OK_CANCEL_OPTION);
        if (opcion == JOptionPane.OK_OPTION) {

            Asistente asistente = new Asistente(getUltimoID(MenuPrincipal.asistentes), nombre.getText(), apellido.getText(), email.getText());
            MenuPrincipal.asistentes.add(asistente);
            ModelosManager.guardarAsistentes(MenuPrincipal.asistentes);
        }

        refrescarTablaAsistentes();
    }

    public void editarAsistente() {
        int fila = tablaDeAsistentes.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un Asistente!");
            return;
        }
        
        int idAsistente = Integer.parseInt(tablaDeAsistentes.getValueAt(fila, 0).toString()) - 1;
        Asistente asistente = MenuPrincipal.asistentes.get(idAsistente);
        JTextField id = new JTextField(Integer.toString(asistente.getId()));
        JTextField nombre = new JTextField(asistente.getNombre());
        JTextField apellido = new JTextField(asistente.getApellido());
        JTextField email = new JTextField(asistente.getEmail());

        Object[] camposEdicionAsistente = {
            "Nombre:", nombre,
            "Apellido:", apellido,
            "Email:", email
        };
            
        int opcion = JOptionPane.showConfirmDialog(this, camposEdicionAsistente, "Editar Asistente", JOptionPane.OK_CANCEL_OPTION);
        
        if (opcion == JOptionPane.OK_OPTION) {

            asistente.setId(Integer.parseInt(id.getText()));
            asistente.setNombre(nombre.getText());
            asistente.setApellido(apellido.getText());
            asistente.setEmail(email.getText());

            ModelosManager.guardarAsistentes(MenuPrincipal.asistentes);
            refrescarTablaAsistentes();
        }
    }
}
