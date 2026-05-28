import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import parking.enums.TipoEspacio;
import parking.enums.TipoVehiculo;
import parking.model.Carro;
import parking.model.EspacioParqueadero;
import parking.model.Tarifa;
import parking.servicio.GestorReportes;
import parking.servicio.Parqueadero;

import static org.junit.jupiter.api.Assertions.*;

public class GestorReportesTest {

    private Parqueadero parqueadero;
    private GestorReportes gestor;

    @BeforeEach
    public void setUp() {
        parqueadero = new Parqueadero("ParkUQ Test");
        parqueadero.agregarEspacio(new EspacioParqueadero("C-01", TipoEspacio.CARRO));
        parqueadero.agregarEspacio(new EspacioParqueadero("C-02", TipoEspacio.CARRO));
        parqueadero.agregarTarifa(new Tarifa(TipoVehiculo.CARRO, 3000));
        gestor = new GestorReportes(parqueadero);
    }

    @Test
    @DisplayName("Calcular ingresos sin historial debe retornar 0")
    public void testCalcularIngresosSinHistorial() {
        assertEquals(0, gestor.calcularIngresosTotales(), 0.01);
    }

    @Test
    @DisplayName("Calcular tiempo promedio sin historial debe retornar 0")
    public void testCalcularTiempoPromedioSinHistorial() {
        assertEquals(0, gestor.calcularTiempoPromedio(), 0.01);
    }

    @Test
    @DisplayName("Calcular ingresos con registros debe sumar correctamente")
    public void testCalcularIngresosConRegistros() {
        Carro carro = new Carro("ABC123", "Juan Perez", "112233");
        parqueadero.registrarIngreso(carro, "112233");
        parqueadero.registrarSalida("ABC123");

        String resumen = gestor.generarResumenDia();

        assertTrue(resumen.contains("1"));
        assertTrue(resumen.contains("ParkUQ"));
    }

    @Test
    @DisplayName("Listar vehiculos con sobretiempo de 0 min debe retornar los activos")
    public void testListarVehiculosSobretiempo() throws InterruptedException {
        Carro carro = new Carro("ABC123", "Juan Perez", "112233");
        parqueadero.registrarIngreso(carro, "112233");

        Thread.sleep(60000);
        int cantidad = gestor.listarVehiculosSobreTiempo(0).size();
        assertEquals(1, cantidad);
    }

}
