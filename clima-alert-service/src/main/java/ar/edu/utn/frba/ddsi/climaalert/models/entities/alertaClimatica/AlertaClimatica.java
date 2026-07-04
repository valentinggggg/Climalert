package ar.edu.utn.frba.ddsi.climaalert.models.entities.alertaClimatica;

import ar.edu.utn.frba.ddsi.climaalert.models.entities.datosClimaticos.DatosClimaticos;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import lombok.Setter;

@Builder
@Getter
public class AlertaClimatica {
  @Setter
  private Long id;
  private DatosClimaticos datoClimatico;
  private LocalDateTime fechaGeneracion;

  public static AlertaClimatica from(DatosClimaticos dato) {
    return AlertaClimatica.builder()
        .datoClimatico(dato)
        .fechaGeneracion(LocalDateTime.now())
        .build();
  }
}