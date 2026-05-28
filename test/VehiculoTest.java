import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import parking.enums.EstadoVehiculo;
import parking.enums.TipoVehiculo;
import parking.model.Bicicleta;
import parking.model.Carro;
import parking.model.Motocicleta;

import static org.junit.jupiter.api.Assertions.*;

public class VehiculoTest {
    @Test
    @DisplayName("Validar creación de vehiculo de tipo carro")
    public void testCrearCarro() {
        Carro carro = new Carro("LRS1234", "Juan Castaño", "10234627934");

        assertEquals("LRS1234", carro.getPlaca());
        assertEquals(TipoVehiculo.CARRO, carro.getTipoVehiculo());
        assertEquals("Juan Castaño", carro.getNombreConductor());
        assertEquals("10234627934", carro.getIdentificacionConductor());
    }

    @Test
    @DisplayName("Validar que la informacion de las placas se almacena en mayusculas")
    public void testGuardarPlacaEnMayusculas() {
        Carro carro = new Carro("lrs1234", "Juan Castaño", "10234627934");

        assertEquals("LRS1234", carro.getPlaca());
    }

    @Test
    @DisplayName("Validar que la informacion del cilindraje se guarda de manera esperada")
    public void testGuardarCilindrajeDeMoto() {
        Motocicleta moto = new Motocicleta("LRS1234", "Juan Castaño", "10234627934", 750);

        assertEquals(750, moto.getCilindraje());
        assertEquals(TipoVehiculo.MOTO, moto.getTipoVehiculo());
    }

    @Test
    @DisplayName("Validar que la informacion cuando ingresa un vehiculo su estado debe ser Dentro")
    public void testEstadoInicialDelVehiculo() {
        Carro carro = new Carro("STB1234", "Dario Peñafiel", "10234627934");

        assertEquals(EstadoVehiculo.DENTRO, carro.getEstado());
    }

    @Test
    @DisplayName("Validar que se pueda cambiar el estado del vehiculo de manera exitosa")
    public void testCambiarEstado() {
        Carro carro = new Carro("STB1234", "Daniel Steven", "10234627935");
        carro.setEstado(EstadoVehiculo.SALIO);

        assertEquals(EstadoVehiculo.SALIO, carro.getEstado());
    }

    @Test
    @DisplayName("Validar que se guarda la informacion de Bicicletas de manera exitosa")
    public void bicicleta_debeCrearseConTipoCorrecto() {
        Bicicleta bicicleta = new Bicicleta("BCI001", "Emi Corrales", "110846289");

        assertEquals(TipoVehiculo.BICICLETA, bicicleta.getTipoVehiculo());
        assertEquals("Bicicleta", bicicleta.getTipoDescripcion());
    }

}
