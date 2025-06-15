package src.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Evento {

    private String nombre;
    private String descripcion;
    private int asistentes;
    private String ubicacion;
    private LocalDate fecha;

    public Evento(String nombre, String descripcion, int asistentes, String ubicacion, LocalDate fecha) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.asistentes = asistentes;
        this.ubicacion = ubicacion;
        this.fecha = fecha;
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
        return nombre + "," + descripcion + "," + asistentes + "," + ubicacion + "," + fecha;
    }

    public static Evento fromString(String linea) {
        String[] partes = linea.split(",");
        if (partes.length != 5) return null;
        return new Evento(partes[0], partes[1], Integer.parseInt(partes[2]), partes[3], LocalDate.parse(partes[4], DateTimeFormatter.ofPattern("dd/MM/yyyy")));
    }

}
