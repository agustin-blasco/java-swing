package src.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Evento implements Comparable<Evento> {

    private int id;
    private String nombre;
    private String descripcion;
    private int asistentes;
    private String ubicacion;
    private LocalDate fecha;

    public Evento(int id, String nombre, String descripcion, int asistentes, String ubicacion, LocalDate fecha) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.asistentes = asistentes;
        this.ubicacion = ubicacion;
        this.fecha = fecha;
    }

    public int getId() {
        return id;
    }
    public String getNombre() {
        return nombre;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public int getAsistentes() {
        return asistentes;
    }
    public String getUbicacion() {
        return ubicacion;
    }
    public LocalDate getFecha() {
        return fecha;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public void setAsistentes(int asistentes) {
        this.asistentes = asistentes;
    }
    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return id + "," + nombre + "," + descripcion + "," + asistentes + "," + ubicacion + "," + fecha;
    }

    @Override
    public int compareTo(Evento otroEvento) {
        return this.fecha.compareTo(otroEvento.fecha);
    }

    public static Evento fromString(String linea) {
        String[] partes = linea.split(",");
        if (partes.length != 6) return null;
        return new Evento(Integer.parseInt(partes[0]), partes[1], partes[2], Integer.parseInt(partes[3]), partes[4], LocalDate.parse(partes[5], DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    }

}
