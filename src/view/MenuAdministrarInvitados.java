package src.view;

import java.awt.*;
import javax.swing.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import src.model.Asistente;
import src.model.Evento;
import src.model.ModelosManager;

public class MenuAdministrarInvitados extends JFrame {

    private DefaultListModel<String> asistentesDisponibles;
    private DefaultListModel<String> asistentesAsignados;
    private JList<String> listaDisponibles;
    private JList<String> listaInvitados;
    private JButton btnAsignar;
    private JButton btnRemover;
    private JPanel panelDetallesEvento;
    private TarjetaEventos tarjetaEventoSeleccionado;
    private List<Asistente> asistentes;
    private Set<Asistente> invitadosAlEventoActual;
    private Map<Evento, Set<Asistente>> todasLasInvitaciones;
    private Map<String, Asistente> opcionAsistentes;
    

    public MenuAdministrarInvitados(Evento evento) {
        setTitle("Administrador de Invitados");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        asistentes = ModelosManager.cargarAsistentes();
        todasLasInvitaciones = ModelosManager.leerInvitaciones();
        invitadosAlEventoActual = todasLasInvitaciones.get(evento);

        asistentesDisponibles = new DefaultListModel<>();
        asistentesAsignados = new DefaultListModel<>();

        opcionAsistentes = new HashMap<String, Asistente>();

        poblarListasAsistentes(invitadosAlEventoActual);

        listaDisponibles = new JList<>(asistentesDisponibles);
        listaInvitados = new JList<>(asistentesAsignados);

        btnAsignar = new JButton("Agregar →");
        btnAsignar.addActionListener(e -> agregarInvitado());
        btnRemover = new JButton("← Quitar");
        btnRemover.addActionListener(e -> eliminarInvitado());

        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JPanel panelSeleccionAsistentes = new JPanel(new GridLayout(1, 3, 10, 10));
        panelSeleccionAsistentes.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel panelDisponibles = new JPanel(new BorderLayout());
        panelDisponibles.add(new JLabel("Asistentes Disponibles"), BorderLayout.NORTH);
        panelDisponibles.add(new JScrollPane(listaDisponibles), BorderLayout.CENTER);
        panelDisponibles.add(btnAsignar, BorderLayout.SOUTH);

        tarjetaEventoSeleccionado = new TarjetaEventos();
        panelDetallesEvento = tarjetaEventoSeleccionado.crearTarjeta(evento);
        panelDetallesEvento.setLayout(new BoxLayout(panelDetallesEvento, BoxLayout.Y_AXIS));
        panelDetallesEvento.setBorder(BorderFactory.createEmptyBorder(15, 15, 0, 50));

        JPanel panelAsignados = new JPanel(new BorderLayout());
        panelAsignados.add(new JLabel("Invitados al Evento"), BorderLayout.NORTH);
        panelAsignados.add(new JScrollPane(listaInvitados), BorderLayout.CENTER);
        panelAsignados.add(btnRemover, BorderLayout.SOUTH);

        JPanel botonesConfirmacionPanel = new JPanel(new GridLayout(1,2, 10, 10));
        botonesConfirmacionPanel.setMaximumSize(new Dimension(1000,100));
        botonesConfirmacionPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        JButton btnGuardar = new JButton("Guardar Invitados");
        JButton btnVolverMenuAnterior = new JButton("Volver al Menu Anterior");
        botonesConfirmacionPanel.add(btnVolverMenuAnterior);
        botonesConfirmacionPanel.add(btnGuardar);

        btnVolverMenuAnterior.addActionListener(e -> volverMenuAnterior());
        btnGuardar.addActionListener(e -> guardarAsistentes(evento));

        panelSeleccionAsistentes.add(panelDisponibles);
        panelSeleccionAsistentes.add(panelAsignados);
        panelPrincipal.add(panelDetallesEvento);
        panelPrincipal.add(panelSeleccionAsistentes);
        panelPrincipal.add(botonesConfirmacionPanel);

        add(panelPrincipal);
    }

    public void guardarAsistentes(Evento evento) {

        todasLasInvitaciones.put(evento, invitadosAlEventoActual);

        ModelosManager.guardarAsistentesAEventos(todasLasInvitaciones);
        evento.setAsistentes(invitadosAlEventoActual.size());
        ModelosManager.guardarEventos(MenuPrincipal.eventos);
        JOptionPane.showMessageDialog(this, "Asistentes Guardados!");
        dispose();
    }

    public void volverMenuAnterior() {
        int opcion = JOptionPane.showConfirmDialog(this, "¿Deseas salir? No se han guardado los cambios", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (opcion == JOptionPane.YES_OPTION) {
            dispose();
        }
    }

    public void agregarInvitado() {
        String asistenteSeleccionado = listaDisponibles.getSelectedValue();
        asistentesDisponibles.removeElement(asistenteSeleccionado);
        asistentesAsignados.addElement(asistenteSeleccionado);
        invitadosAlEventoActual.add(opcionAsistentes.get(asistenteSeleccionado));
    }

    public void eliminarInvitado() {
        String asistenteSeleccionado = listaInvitados.getSelectedValue();
        asistentesDisponibles.addElement(asistenteSeleccionado);
        asistentesAsignados.removeElement(asistenteSeleccionado);
        invitadosAlEventoActual.remove(opcionAsistentes.get(asistenteSeleccionado));
    }

    
    public void poblarListasAsistentes(Set<Asistente> invitados) {
        for (Asistente asistente : asistentes) {
            String stringInvitado = asistente.toInvitadoList();
            opcionAsistentes.put(stringInvitado, asistente);
            if (invitados.contains(asistente)) {
                asistentesAsignados.addElement(stringInvitado);
            } else {
                asistentesDisponibles.addElement(stringInvitado);
            }
        }
    }
}
