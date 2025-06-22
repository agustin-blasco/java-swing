package src.model;

import java.util.Objects;

public class Asistente {

    private int id;
    private String nombre;
    private String apellido;
    private String email;

    public Asistente(int id, String nombre, String apellido, String email) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String toInvitadoList() {
        return nombre + " " + apellido + " (" + email + ")";
    }

    @Override
    public String toString() {
        return id + "," + nombre + "," + apellido + "," + email;
    }

    // Added to compare the Set with the List in Invitados.
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        Asistente asistente = (Asistente) obj;
        
        return Objects.equals(id, asistente.id) && 
               Objects.equals(nombre, asistente.nombre) &&
               Objects.equals(apellido, asistente.apellido) &&
               Objects.equals(email, asistente.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, apellido, email);
    }

    public static Asistente fromString(String linea) {
        String[] partes = linea.split(",");
        if (partes.length != 4) return null;
        return new Asistente(Integer.parseInt(partes[0]), partes[1], partes[2], partes[3]);
    }
}
