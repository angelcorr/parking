import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import parking.enums.TipoEspacio;
import parking.enums.TipoVehiculo;
import parking.model.*;
import parking.servicio.Parqueadero;

import static org.junit.jupiter.api.Assertions.*;

public class ParqueaderoTest {

    private Parqueadero parqueadero;

    @BeforeEach
    public void setUp() {
        parqueadero = new Parqueadero("ParkUQ Test");
        parqueadero.agregarEspacio(new EspacioParqueadero("C-01", TipoEspacio.CARRO));
        parqueadero.agregarEspacio(new EspacioParqueadero("C-02", TipoEspacio.CARRO));
        parqueadero.agregarEspacio(new EspacioParqueadero("M-01", TipoEspacio.MOTO));
        parqueadero.agregarTarifa(new Tarifa(TipoVehiculo.CARRO, 3000));
        parqueadero.agregarTarifa(new Tarifa(TipoVehiculo.MOTO, 2000));
        parqueadero.agregarTarifa(new Tarifa(TipoVehiculo.BICICLETA, 500));
    }

    @Test
    @DisplayName("Registrar ingreso debe asignar espacio y vehiculo")
    public void testRegistrarIngreso() {
        Carro carro = new Carro("ABC123", "Juan Perez", "112233");

        EspacioParqueadero espacio = parqueadero.registrarIngreso(carro, "1122333");

        assertNotNull(espacio);
        assertEquals("C-01", espacio.getCodigo());
        assertEquals(1, parqueadero.consultarVehiculosActivos().size());
        assertNotNull(carro.getHoraIngreso());
    }

    @Test
    @DisplayName("Registrar salida debe generar recibo y liberar espacio")
    public void testRegistrarSalida() {
        Carro carro = new Carro("ABC123", "Juan Perez", "112233");
        EspacioParqueadero espacio = parqueadero.registrarIngreso(carro, "112233");

        RegistroSalida registro = parqueadero.registrarSalida("ABC123");

        assertNotNull(registro);
        assertEquals(0, parqueadero.consultarVehiculosActivos().size());
        assertTrue(espacio.estaDisponible());
        assertTrue(registro.getTotalCobrado() >= 0);
    }

    @Test
    @DisplayName("Registrar salida de conductor con descuento debe aplicar descuento")
    public void testAplicarDescuento() {
        Docente docente = new Docente("Juan Perez", "112233");
        parqueadero.agregarUsuarioAutorizado(docente);

        Carro carro = new Carro("ABC123", "Juan Perez", "112233");
        parqueadero.registrarIngreso(carro, "112233");
        RegistroSalida registro = parqueadero.registrarSalida("ABC123");

        assertEquals(0.30, registro.getDescuentoAplicado(), 0.001);
        assertTrue(registro.getTotalCobrado() < registro.getTarifaBase());
    }

    @Test
    @DisplayName("Consular espacios después de ingreso debe reducir cantidad")
    public void testConsultarEspaciosDespuesDeIngreso() {
        int disponiblesAntes = parqueadero.consultarEspaciosDisponibles().size();

        Carro carro = new Carro("ABC123", "Juan Perez", "112233");
        parqueadero.registrarIngreso(carro, "112233");

        int disponiblesDespues = parqueadero.consultarEspaciosDisponibles().size();

        assertEquals(disponiblesAntes - 1, disponiblesDespues);
    }

    @Test
    @DisplayName("Ingresar motocicleta no debe ocupar espacio de carro")
    public void testIngresarMoto() {
        Motocicleta moto = new Motocicleta("ABC123", "Juan Perez", "112233", 750);
        EspacioParqueadero espacio = parqueadero.registrarIngreso(moto, "112233");

        assertEquals(TipoEspacio.MOTO, espacio.getTipoEspacio());
    }

    @Test
    @DisplayName("Placas no tiene en cuenta min o mayus al buscar salida")
    public void testBuscarSalida() {
        Carro carro = new Carro("ABC123", "Juan Perez", "112233");
        parqueadero.registrarIngreso(carro, "112233");

        RegistroSalida salida = parqueadero.registrarSalida("abc123");

        assertNotNull(salida);
    }

}
