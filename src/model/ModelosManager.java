package src.model;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

import src.view.MenuPrincipal;

public class ModelosManager {
    
    public static final String ARCHIVO_EVENTOS = "src/data/eventos.txt";
    public static final String ARCHIVO_ASISTENTES = "src/data/asistentes.txt";
    public static final String ARCHIVO_ASISTENTES_TO_EVENTOS = "src/data/asistentesToEventos.txt";

    public static List<Evento> cargarEventos() {
        List<Evento> eventos = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO_EVENTOS))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                Evento evento = Evento.fromString(linea);
                if (evento != null) eventos.add(evento);
            }
        } catch (IOException e) {
            System.out.println("Archivo no encontrado.");
        }
        return eventos;
    }

    public static List<Asistente> cargarAsistentes() {
        List<Asistente> asistentes = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO_ASISTENTES))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                Asistente asistente = Asistente.fromString(linea);
                if (asistente != null) asistentes.add(asistente);
            }
        } catch (IOException e) {
            System.out.println("Archivo no encontrado.");
        }
        return asistentes;
    }

    public static Map<Integer, Asistente> cargarAsistentesHM() {
        Map<Integer, Asistente> asistentes = new HashMap<Integer, Asistente>();
        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO_ASISTENTES))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                Asistente asistente = Asistente.fromString(linea);
                if (asistente != null) asistentes.put(asistente.getId(), asistente);
            }
        } catch (IOException e) {
            System.out.println("Archivo no encontrado.");
        }
        return asistentes;
    }

    public static void guardarAsistentes(List<Asistente> asistentes) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO_ASISTENTES))) {
            for (Asistente asistente : asistentes) {
                writer.write(asistente.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al guardar los asistentes: " + e.getMessage());
        }
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

    public static List<Evento> cargarEventosFuturosOrdenados(List<Evento> eventos) {
        List<Evento> eventosFuturos = new ArrayList<>();
        for (Evento evento : eventos) {
            if (evento.getFecha().isAfter(LocalDate.now())) {
                eventosFuturos.add(evento);
            }
        }
        Collections.sort(eventosFuturos);
        return eventosFuturos;
    }

    public static Map<Evento, Set<Asistente>> leerInvitaciones() {
        Map<Evento, Set<Asistente>> asistentesInvitados = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO_ASISTENTES_TO_EVENTOS))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                int idEvento = Integer.parseInt(linea.split(",")[0]) - 1;
                int idAsistente = Integer.parseInt(linea.split(",")[1]) - 1;
                asistentesInvitados.putIfAbsent(MenuPrincipal.eventos.get(idEvento), new HashSet<>());
                asistentesInvitados.get(MenuPrincipal.eventos.get(idEvento)).add(MenuPrincipal.asistentes.get(idAsistente));
            }
        } catch (IOException e) {
            System.out.println("Archivo no encontrado.");
        }
        return asistentesInvitados;
    }

    public static void guardarAsistentesAEventos(Map<Evento, Set<Asistente>> invitaciones) {
        List<String> eventosToAsistentes = new ArrayList();
        for (Evento evento : invitaciones.keySet()) {
            for (Asistente asistente : invitaciones.get(evento)) {
                eventosToAsistentes.add(evento.getId() + "," + asistente.getId());
            }
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO_ASISTENTES_TO_EVENTOS))) {
            for (String entrada : eventosToAsistentes) {
                writer.write(entrada);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al guardar los eventos: " + e.getMessage());
        }
    }
}
