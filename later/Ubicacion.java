public class Ubicacion {
    private String nombre;
    private int capacidad;
    private boolean estaOcupado;


    public Ubicacion(String nombre, int capacidad, boolean estaOcupado) {
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.estaOcupado = false;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCapacidad() {
        return this.capacidad;
    }

    public void setNombre(int capacidad) {
        this.capacidad = capacidad;
    }

    public boolean getEstado() {
        return this.estaOcupado;
    }

    // public int ocuparSalon() {
    //     return this.capacidad;
    // }

    // public void setNombre(int capacidad) {
    //     this.capacidad = capacidad;
    // }

    
}
