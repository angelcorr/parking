package parking.model;

import parking.enums.TipoVehiculo;

public class Motocicleta extends Vehiculo {

    private int cilindraje;

    public Motocicleta(String placa, String nombreConductor, String identificacionConductor, int cilindraje) {
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
