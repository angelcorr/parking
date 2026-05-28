import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import parking.enums.TipoUsuario;
import parking.model.Carro;
import parking.model.Docente;
import parking.model.Visitante;

import static org.junit.jupiter.api.Assertions.*;

public class UsuarioTest {

  @Test
  @DisplayName("Validar descuento del 20% a los estudiantes")
  void testValidarDescuentoEstudiantil() {
    Carro.Estudiante estudiante = new Carro.Estudiante("Jelen Holguín", "10234678");
//    El tercer argumento que se envia a assertEquals es para dar un valor de precision
//    al momento de comparar doubles. En este caso tres decimales
    assertEquals(0.20, estudiante.getDescuento(), 0.001);
    assertEquals(TipoUsuario.ESTUDIANTE, estudiante.getTipoUsuario());
  }

  @Test
  @DisplayName("Validar descuento del 25% a los administrativos")
  public void testValidarDescuentoAdministrativos() {
    Docente.Administrativo administrativo = new Docente.Administrativo("Luis Fernando Polanía", "1025648256");

    assertEquals(0.25, administrativo.getDescuento(), 0.001);
    assertEquals(TipoUsuario.ADMINISTRATIVO, administrativo.getTipoUsuario());
  }

  @Test
  @DisplayName("Validar descuento del 30% a los docentes")
  void testValidarDescuentoDocentes() {
    Docente docente = new Docente("Juan Castaño", "10234627934");

    assertEquals(0.30, docente.getDescuento(), 0.001);
    assertEquals(TipoUsuario.DOCENTE, docente.getTipoUsuario());
  }

  @Test
  @DisplayName("Validar que los visitantes no tienen descuento")
  void testValidarNoDescuentoParaVisitantes() {
    Visitante visitante = new Visitante("Rodrigo Lara", "1267428908");

    assertEquals(0.30, visitante.getDescuento(), 0.001);
    assertEquals(TipoUsuario.VISITANTE, visitante.getTipoUsuario());
  }

  @Test
  @DisplayName("Validar descuento por subclases")
  void testValidarDescuentoParaLosDiferentesUsuarios() {
    Carro.Estudiante estudiante = new Carro.Estudiante("Sebastian Quiceno", "1094682927");
    Docente docente = new Docente("Juan Castaño", "10234627934");

    assertNotEquals(estudiante.getDescuento(), docente.getDescuento());
    assertTrue(docente.getDescuento() > estudiante.getDescuento());
  }

}
