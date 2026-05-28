import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import parking.enums.TipoVehiculo;
import parking.model.Tarifa;

import static org.junit.jupiter.api.Assertions.*;

public class TarifaTest {

    @Test
    @DisplayName("Validar que el costo en menos de una hora no sobrepase el valor establecido")
    public void testCalcularCostoMenosDeUnaHora() {
      Tarifa tarifa = new Tarifa(TipoVehiculo.CARRO, 3000.0);
      double costo = tarifa.calcularCosto(30);

      //  El tercer argumento que se envia a assertEquals es para dar un valor de precision
      //  al momento de comparar doubles. En este caso dos decimales
      assertEquals(3000.0, costo, 0.01);
    }

  @Test
  @DisplayName("Validar que el costo en exactamente una hora y que no se incremente o decrezca el valor")
  public void testCalcularCostoExactoDeUnaHora() {
    Tarifa tarifa = new Tarifa(TipoVehiculo.CARRO, 3000.0);
    double costo = tarifa.calcularCosto(60);

    assertEquals(3000.0, costo, 0.01);
  }

  @Test
  @DisplayName("Validar que el costo para dos horas")
  public void testCalcularCostoDosHoras() {
    Tarifa tarifa = new Tarifa(TipoVehiculo.CARRO, 3000.0);
    double costo = tarifa.calcularCosto(120);

    assertEquals(6000.0, costo, 0.01);
  }

  @Test
  @DisplayName("Validar que el costo por hora no cambie")
  public void testMantenerElValorPorHora() {
    Tarifa tarifa = new Tarifa(TipoVehiculo.CARRO, 5000.0);

    assertEquals(5000.0, tarifa.getValorPorHora(), 0.01);
  }

  @Test
  @DisplayName("Validar el cambio de costo por hora")
  public void testCambiarElValorPorHora() {
    Tarifa tarifa = new Tarifa(TipoVehiculo.CARRO, 5000.0);
    tarifa.setValorPorHora(3000.0);

    assertEquals(3000.0, tarifa.getValorPorHora(), 0.01);
  }
}
