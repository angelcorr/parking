package trabajoFinal.model;

import trabajoFinal.enums.TipoUsuario;

public class Administrativo extends Usuario {
    public static final double DESCUENTO = 0.25;

    public Administrativo(String nombre, String identificacion, TipoUsuario tipoUsuario) {
        super(nombre, identificacion, TipoUsuario.ADMINISTRATIVO);
    }

    @Override
    public double getDescuento() {
        return DESCUENTO;
    }
}
