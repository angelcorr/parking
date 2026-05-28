package parking.model;

import parking.enums.TipoUsuario;

public class Visitante extends Administrativo {
    public static final double DESCUENTO = 0.0;

    public Visitante(String nombre, String identificacion, TipoUsuario tipoUsuario) {
        super(nombre, identificacion, TipoUsuario.VISITANTE);
    }

    @Override
    public double getDescuento() {
        return DESCUENTO;
    }
}
