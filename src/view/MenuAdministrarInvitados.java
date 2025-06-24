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
    private JList<String> listaAsistentesDisponibles;
    private JList<String> listaInvitados;
    private List<Asistente> asistentes;
    private Map<Evento, Set<Asistente>> todasLasInvitaciones;
    private Set<Asistente> invitadosAlEventoActual;
    private Map<String, Asistente> opcionAsistentes;
    private MenuAdministrarEventos menuAdministrarEventos;
    
    public MenuAdministrarInvitados(Evento evento, List<Asistente> asistentes, MenuAdministrarEventos menuAdministrarEventos) {
        this.asistentes = asistentes;
        this.menuAdministrarEventos = menuAdministrarEventos;
        todasLasInvitaciones = ModelosManager.leerInvitaciones();
        invitadosAlEventoActual = todasLasInvitaciones.get(evento);
        opcionAsistentes = new HashMap<String, Asistente>();

        setTitle("Administrador de Invitados");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        crearUI(evento);
    }

    public void crearUI(Evento evento) {

        asistentesDisponibles = new DefaultListModel<>();
        asistentesAsignados = new DefaultListModel<>();

        poblarListasAsistentes(invitadosAlEventoActual);

        listaAsistentesDisponibles = new JList<>(asistentesDisponibles);
        listaInvitados = new JList<>(asistentesAsignados);

        JButton btnAsignar = new JButton("Agregar →");
        btnAsignar.addActionListener(e -> agregarInvitado());
        JButton btnRemover = new JButton("← Quitar");
        btnRemover.addActionListener(e -> eliminarInvitado());

        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JPanel panelSeleccionAsistentes = new JPanel(new GridLayout(1, 3, 10, 10));
        panelSeleccionAsistentes.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        TarjetaEventos tarjetaEventoSeleccionado = new TarjetaEventos();
        JPanel panelDetallesEvento = tarjetaEventoSeleccionado.crearTarjeta(evento);
        panelDetallesEvento.setLayout(new BoxLayout(panelDetallesEvento, BoxLayout.Y_AXIS));
        panelDetallesEvento.setBorder(BorderFactory.createEmptyBorder(15, 15, 0, 50));

        JPanel panelAsistentesDisponibles = new JPanel(new BorderLayout());
        panelAsistentesDisponibles.add(new JLabel("Asistentes Disponibles"), BorderLayout.NORTH);
        panelAsistentesDisponibles.add(new JScrollPane(listaAsistentesDisponibles), BorderLayout.CENTER);
        panelAsistentesDisponibles.add(btnAsignar, BorderLayout.SOUTH);

        JPanel panelAsistentesAsignados = new JPanel(new BorderLayout());
        panelAsistentesAsignados.add(new JLabel("Invitados al Evento"), BorderLayout.NORTH);
        panelAsistentesAsignados.add(new JScrollPane(listaInvitados), BorderLayout.CENTER);
        panelAsistentesAsignados.add(btnRemover, BorderLayout.SOUTH);

        JPanel botonesConfirmacionPanel = new JPanel(new GridLayout(1,2, 10, 10));
        botonesConfirmacionPanel.setMaximumSize(new Dimension(1000,100));
        botonesConfirmacionPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        JButton btnGuardar = new JButton("Guardar Invitados");
        JButton btnVolverMenuAnterior = new JButton("Volver al Menu Anterior");
        botonesConfirmacionPanel.add(btnVolverMenuAnterior);
        botonesConfirmacionPanel.add(btnGuardar);

        btnVolverMenuAnterior.addActionListener(e -> volverMenuAnterior());
        btnGuardar.addActionListener(e -> guardarAsistentes(evento));

        panelSeleccionAsistentes.add(panelAsistentesDisponibles);
        panelSeleccionAsistentes.add(panelAsistentesAsignados);
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
        menuAdministrarEventos.refrescarTablaEventos();
        dispose();
    }

    public void volverMenuAnterior() {
        int opcion = JOptionPane.showConfirmDialog(this, "¿Deseas salir? No se han guardado los cambios", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (opcion == JOptionPane.YES_OPTION) {
            dispose();
        }
    }

    public void agregarInvitado() {
        String asistenteSeleccionado = listaAsistentesDisponibles.getSelectedValue();
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
