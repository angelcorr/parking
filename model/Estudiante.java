package trabajoFinal.model;

import trabajoFinal.enums.TipoUsuario;
import trabajoFinal.enums.TipoVehiculo;

public class Estudiante extends Usuario {
    public static final double DESCUENTO = 0.20;

    public Estudiante(String nombre, String identificacion, TipoUsuario tipoUsuario) {
        super(nombre, identificacion, TipoUsuario.ESTUDIANTE);
    }

    @Override
    public double getDescuento() {
        return DESCUENTO;
    }
}
