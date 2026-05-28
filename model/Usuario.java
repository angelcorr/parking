package parking.model;

import parkUQ.enums.TipoUsuario;
import parkUQ.enums.TipoVehiculo;

public abstract class Usuario {
    private String nombre;
    private String identificacion;
    private TipoUsuario tipoUsuario;

    public Usuario(String nombre, String identificacion, TipoUsuario tipoUsuario) {
        this.nombre = nombre;
        this.identificacion = identificacion;
        this.tipoUsuario = tipoUsuario;
    }

    public abstract double getDescuento();

    public String getNombre() {
        return nombre;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    @Override
    public String toString() {
        return tipoUsuario + " | " + nombre + " | ID: " + identificacion
                + " | Descuento: " + (int)(getDescuento() * 100) + "%";
    }
}
