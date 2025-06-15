package src.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class EventoManager {
    
    private static final String ARCHIVO_EVENTOS = "src/data/eventos.txt";

    public static List<Evento> cargarEventos() {
        List<Evento> eventos = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO_EVENTOS))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                Evento evento = Evento.fromString(linea);
                if (evento != null) eventos.add(evento);
            }
        } catch (IOException e) {
            System.out.println("Archivo no encontrado. Se creará uno nuevo.");
        }
        return eventos;
    }

    public static void guardarEventos(List<Evento> eventos) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO_EVENTOS))) {
            for (Evento evento : eventos) {
                writer.write(evento.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al guardar los eventos: " + e.getMessage());
        }
    }

    public List<Evento> buscarEnEventos(String busqueda) {
        List<Evento> resultados = new ArrayList<>();
        busqueda = busqueda.toLowerCase();
        
        for (Evento evento : cargarEventos()) {
            if (evento.getNombre().toLowerCase().contains(busqueda) ||
                evento.getDescripcion().toLowerCase().contains(busqueda) ||
                Integer.toString(evento.getAsistentes()).contains(busqueda) ||
                evento.getUbicacion().toLowerCase().contains(busqueda) ||
                evento.getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")).contains(busqueda)) {
                resultados.add(evento);
            }
        }
        return resultados;
    }

}
