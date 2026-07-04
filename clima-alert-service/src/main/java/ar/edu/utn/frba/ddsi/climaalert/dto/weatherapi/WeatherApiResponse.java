package ar.edu.utn.frba.ddsi.climaalert.dto.weatherapi;


import ar.edu.utn.frba.ddsi.climaalert.models.entities.datosClimaticos.DatosClimaticos;
import java.time.LocalDateTime;

public record WeatherApiResponse(
    UbicacionDTO location,
    ClimaDTO current
){
  public DatosClimaticos toDatoClimatico() {
    return DatosClimaticos.builder()
        .ubicacion(this.location.name())
        .temperaturaC(this.current.temp_c())
        .humedad(this.current.humidity())
        .descripcion(this.current.condition().text())
        .fechaHora(LocalDateTime.now())
        .build();
  }
}