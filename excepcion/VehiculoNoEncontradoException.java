package parking.excepcion;

public class VehiculoNoEncontradoException extends ParkUQException {

    public VehiculoNoEncontradoException(String placa) {
        super("No se encontró ningún vehiculo activo con placa: " + placa);
    }
}
