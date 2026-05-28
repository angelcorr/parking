package parking.model;

import parkUQ.enums.TipoVehiculo;

public class Bicicleta extends Vehiculo {

    public Bicicleta(String placa, String nombreConductor, String identificacionConductor) {
        super(placa, TipoVehiculo.BICICLETA, nombreConductor, identificacionConductor);
    }

    @Override
    public String getTipoDescripcion() {
        return "Bicicleta";
    }
}
