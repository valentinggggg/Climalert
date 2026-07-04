package ar.edu.utn.frba.ddsi.climaalert.models.entities.datosClimaticos;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
public class DatosClimaticos {
  @Setter
  private Long id;
  private String ubicacion;
  private double temperaturaC;
  private double humedad;
  private String descripcion;
  private LocalDateTime fechaHora;

  public boolean esCondicionCritica() {
    return this.temperaturaC > 35 && this.humedad > 60;
  }
}
