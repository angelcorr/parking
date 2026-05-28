import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import parking.enums.EstadoEspacio;
import parking.enums.TipoEspacio;
import parking.model.Carro;
import parking.model.EspacioParqueadero;

import static org.junit.jupiter.api.Assertions.*;


public class EspacioParqueaderoTest {

    private EspacioParqueadero espacio;

    @BeforeEach
    public void setUp() {
        espacio = new EspacioParqueadero("C-01", TipoEspacio.CARRO);
    }

    @Test
    @DisplayName("Espacio debe iniciar como disponible")
    public void testEspacioDisponible() {
        assertEquals(EstadoEspacio.DISPONIBLE, espacio.getEstado());
        assertTrue(espacio.estaDisponible());
        assertNull(espacio.getVehiculoAsignado());
    }

    @Test
    @DisplayName("Asignar un vehiculo debe ocupar el espacio")
    public void testAsignarVehiculo() {
        Carro carro = new Carro("ABC123", "Juan Perez", "111222333");
        espacio.asignarVehiculo(carro);

        assertEquals(EstadoEspacio.OCUPADO, espacio.getEstado());
        assertFalse(espacio.estaDisponible());
        assertEquals(carro, espacio.getVehiculoAsignado());
    }

    @Test
    @DisplayName("Liberar un espacio lo debe devolver a disponible")
    public void testLiberarEspacio() {
        Carro carro = new Carro("ABC123", "Juan Perez", "111222333");
        espacio.asignarVehiculo(carro);
        espacio.liberarEspacio();

        assertEquals(EstadoEspacio.DISPONIBLE, espacio.getEstado());
        assertTrue(espacio.estaDisponible());
        assertNull(espacio.getVehiculoAsignado());
    }

    @Test
    @DisplayName("Espacio debe tener tipo y codigo correctos")
    public void testTipoEspacio() {
        assertEquals("C-01", espacio.getCodigo());
        assertEquals(TipoEspacio.CARRO, espacio.getTipoEspacio());
    }

    @Test
    @DisplayName("Espacio fuera de servicio no debe estar disponible")
    public void testFueraDeServicio() {
        espacio.setEstado(EstadoEspacio.FUERA_DE_SERVICIO);

        assertFalse(espacio.estaDisponible());
    }

}
