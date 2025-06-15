package src.controller;

import java.util.List;

import src.model.EventoManager;
import src.model.Evento;
import src.view.MenuBuscarEventos;
import src.view.MenuPrincipal;

public class MenuPrincipalController {
    
    private MenuPrincipal vistaMenuPrincipal;
    private EventoManager modeloEventos;
    private MenuBuscarEventos menuBusquedaEventos;

    public MenuPrincipalController(MenuPrincipal vistaMenuPrincipal, EventoManager modeloEventos) {
        this.vistaMenuPrincipal = vistaMenuPrincipal;
        this.modeloEventos = modeloEventos;

        this.vistaMenuPrincipal.btnBuscarEventos.addActionListener(e -> mostrarMenuBusquedaEventos());
    }

    private void mostrarMenuBusquedaEventos() {
        menuBusquedaEventos = new MenuBuscarEventos();
        menuBusquedaEventos.btnBusquedaEvento.addActionListener(e -> {
            String busqueda = menuBusquedaEventos.buscarEnEventos();
            List<Evento> eventos = modeloEventos.buscarEnEventos(busqueda);
            menuBusquedaEventos.mostrarResultados(eventos);
        });
        
        vistaMenuPrincipal.setVisible(false);
        menuBusquedaEventos.setVisible(true);

        menuBusquedaEventos.btnVolverMenu.addActionListener(e -> {
            menuBusquedaEventos.dispose();
            vistaMenuPrincipal.setVisible(true);
        });
    }

}
