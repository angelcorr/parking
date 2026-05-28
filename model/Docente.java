package parking.model;

import parking.enums.TipoUsuario;

public class Docente extends Usuario {
    public static final double DESCUENTO = 0.30;

    public Docente(String nombre, String identificacion, TipoUsuario tipoUsuario) {
        super(nombre, identificacion, TipoUsuario.DOCENTE);
    }

    @Override
    public double getDescuento() {
        return DESCUENTO;
    }
}
