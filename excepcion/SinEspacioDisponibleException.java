package parking.excepcion;

public class SinEspacioDisponibleException extends ParkUQException {

    public SinEspacioDisponibleException(String tipoVehiculo) {
        super("No hay espacios disponibles para el tipo de vehiculo: " + tipoVehiculo);
    }

}
