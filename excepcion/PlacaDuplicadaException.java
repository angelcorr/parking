package parking.excepcion;

public class PlacaDuplicadaException extends ParkUQException {

    public PlacaDuplicadaException(String placa) {
        super("El vehiculo con placa " + placa + " ya se encuentra dentro del parqueadero.");
    }

}
