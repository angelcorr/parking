package parking.model;

import parkUQ.enums.TipoVehiculo;

public class Motocicleta extends Vehiculo {

    private int cilindraje;

    public Motocicleta(String placa, String nombreConductor, String identificacionConductor) {
        super(placa, TipoVehiculo.MOTO, nombreConductor, identificacionConductor);
        this.cilindraje = cilindraje;
    }
    public int getCilindraje() {
        return cilindraje;
    }
    public void setCilindraje(int cilindraje) {
        this.cilindraje = cilindraje;
    }

    @Override
    public String getTipoDescripcion() {
        return "Motocicleta";
    }
}
