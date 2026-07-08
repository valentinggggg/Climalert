package ar.edu.utn.frba.ddsi.climaalert;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ar.edu.utn.frba.ddsi.climaalert.models.entities.alertaClimatica.AlertaClimatica;
import ar.edu.utn.frba.ddsi.climaalert.models.entities.datosClimaticos.DatosClimaticos;
import ar.edu.utn.frba.ddsi.climaalert.models.repositories.IDatoClimaticoRepository;
import ar.edu.utn.frba.ddsi.climaalert.services.INotificacionesService;
import ar.edu.utn.frba.ddsi.climaalert.services.impl.AlertaServiceImpl;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AlertaServiceImplTest {

  @Mock
  private IDatoClimaticoRepository datoClimaticoRepository;

  @Mock
  private INotificacionesService notificacionService;

  private AlertaServiceImpl alertaService;

  @BeforeEach
  void setUp() {
    alertaService = new AlertaServiceImpl(datoClimaticoRepository, notificacionService);
  }

  @Test
  void analizarClima_noNotifica_noHaydatos() {
    when(datoClimaticoRepository.obtenerUltimo()).thenReturn(Optional.empty());

    alertaService.analizarClima();

    verify(notificacionService, never()).notificarAlerta(any());
  }

  @Test
  void analizarClima_noNotifica_datoNoCritico() {
    when(datoClimaticoRepository.obtenerUltimo()).thenReturn(Optional.of(datoNoCritico(1L)));

    alertaService.analizarClima();

    verify(notificacionService, never()).notificarAlerta(any());
  }

  @Test
  void analizarClima_notifica_datoCritico() {
    when(datoClimaticoRepository.obtenerUltimo()).thenReturn(Optional.of(datoCritico(1L)));

    alertaService.analizarClima();

    verify(notificacionService, times(1)).notificarAlerta(any(AlertaClimatica.class));
  }

  @Test
  void analizarClima_noReenvia_mismoDatoAnalizadoAntes() {
    when(datoClimaticoRepository.obtenerUltimo()).thenReturn(Optional.of(datoCritico(1L)));

    alertaService.analizarClima();
    alertaService.analizarClima();

    verify(notificacionService, times(1)).notificarAlerta(any(AlertaClimatica.class));
  }

  @Test
  void analizarClima_vuelveANotificar_datoCriticoNuevo() {
    when(datoClimaticoRepository.obtenerUltimo())
        .thenReturn(Optional.of(datoCritico(1L)))
        .thenReturn(Optional.of(datoCritico(2L)));

    alertaService.analizarClima();
    alertaService.analizarClima();

    verify(notificacionService, times(2)).notificarAlerta(any(AlertaClimatica.class));
  }

  private DatosClimaticos datoCritico(Long id) {
    return DatosClimaticos.builder()
        .id(id)
        .ubicacion("CABA")
        .temperaturaC(38.0)
        .humedad(70)
        .descripcion("Tormenta")
        .fechaHora(LocalDateTime.now())
        .build();
  }

  private DatosClimaticos datoNoCritico(Long id) {
    return DatosClimaticos.builder()
        .id(id)
        .ubicacion("CABA")
        .temperaturaC(20.0)
        .humedad(40)
        .descripcion("Templado")
        .fechaHora(LocalDateTime.now())
        .build();
  }
}