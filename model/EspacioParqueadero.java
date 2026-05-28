package parking.model;

import parkUQ.enums.EstadoEspacio;
import parkUQ.enums.TipoEspacio;

public class EspacioParqueadero {
    private String codigo;
    private TipoEspacio tipoEspacio;
    private EstadoEspacio estado;
    private Vehiculo vehiculoAsignado;

    public EspacioParqueadero(String codigo, TipoEspacio tipoEspacio) {
        this.codigo = codigo;
        this.tipoEspacio = tipoEspacio;
        this.estado = EstadoEspacio.DISPONIBLE;
        this.vehiculoAsignado = null;
    }

    public void asignarVehiculo(Vehiculo vehiculo) {
        this.vehiculoAsignado = vehiculo;
        this.estado = EstadoEspacio.OCUPADO;
    }

    public void liberarEspacio () {
        this.vehiculoAsignado = null;
        this.estado = EstadoEspacio.DISPONIBLE;
    }
}
