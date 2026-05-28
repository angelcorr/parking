import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import parking.enums.EstadoEspacio;
import parking.enums.TipoEspacio;
import parking.enums.TipoVehiculo;
import parking.excepcion.PlacaDuplicadaException;
import parking.excepcion.SinEspacioDisponibleException;
import parking.excepcion.VehiculoNoEncontradoException;
import parking.model.Carro;
import parking.model.EspacioParqueadero;
import parking.model.Tarifa;
import parking.servicio.Parqueadero;

import static org.junit.jupiter.api.Assertions.*;

public class ExcepcionesTest {

    private Parqueadero parqueadero;

    @BeforeEach
    public void setUp() {

        parqueadero = new Parqueadero("ParkUQ Test");
        parqueadero.agregarEspacio(new EspacioParqueadero("C-01", TipoEspacio.CARRO));
        parqueadero.agregarTarifa(new Tarifa(TipoVehiculo.CARRO, 3000));
    }

    @Test
    @DisplayName("Placa duplicada debe lanzar excepción")
    public void testPlacaDuplicada() {

        Carro carro1 = new Carro("AAA111", "Juan Perez", "112233");
        Carro carro2 = new Carro("AAA111", "Omar Ortiz", "112244");
        parqueadero.registrarIngreso(carro1, "112233");

        PlacaDuplicadaException exception = assertThrows(
                PlacaDuplicadaException.class,
                () -> parqueadero.registrarIngreso(carro2, "112244")
        );
        assertTrue(exception.getMessage().contains("AAA111"));
    }

    @Test
    @DisplayName("Registrar ingreso sin espacios debe lanzar excepción")
    public void testRegistrarSinEspacios() {

        Carro carro1 = new Carro("AAA111", "Juan Perez", "112233");
        parqueadero.registrarIngreso(carro1, "112233");

        Carro carro2 = new Carro("AAA222", "Omar Ortiz", "112244");

        SinEspacioDisponibleException exception = assertThrows(
                SinEspacioDisponibleException.class,
                () -> parqueadero.registrarIngreso(carro2, "112244")
        );
        assertTrue(exception.getMessage().contains("CARRO"));
    }

    @Test
    @DisplayName("Registrar salida con placa inexistente lanza excepción")
    public void testRegistrarSalidaConPlacaInexistente() {

        VehiculoNoEncontradoException exception = assertThrows(
                VehiculoNoEncontradoException.class,
                () -> parqueadero.registrarSalida("ABC123")
        );
        assertTrue(exception.getMessage().contains("ABC123"));
    }

    @Test
    @DisplayName("Espacio fuera de servicio no permite ingreso")
    public void testEspacioFueraDeServicio() {

        parqueadero.modificarEstadoEspacio("C-01", EstadoEspacio.FUERA_DE_SERVICIO);

        Carro carro = new Carro("AAA111", "Juan Perez", "112233");

        assertThrows(
                SinEspacioDisponibleException.class,
                () -> parqueadero.registrarIngreso(carro, "112233")
        );
    }

}
