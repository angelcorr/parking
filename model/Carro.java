package parking.model;

import parking.enums.TipoVehiculo;

public class Carro extends Vehiculo {

    public Carro(String placa, String nombreConductor, String identificacionConductor) {
        super(placa, TipoVehiculo.CARRO, nombreConductor, identificacionConductor);
    }

    @Override
    public String getTipoDescripcion() {
        return "Automovil";
    }
}
