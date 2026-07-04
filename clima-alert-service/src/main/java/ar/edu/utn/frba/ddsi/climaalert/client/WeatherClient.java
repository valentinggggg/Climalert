package ar.edu.utn.frba.ddsi.climaalert.client;

import ar.edu.utn.frba.ddsi.climaalert.dto.weatherapi.WeatherApiResponse;
import ar.edu.utn.frba.ddsi.climaalert.models.entities.datosClimaticos.DatosClimaticos;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class WeatherClient {

  private final RestClient restClient;

  @Value("${weatherapi.key}")
  private String apiKey;

  public WeatherClient(@Value("${weatherapi.base-url}") String baseUrl) {
    this.restClient = RestClient.builder().baseUrl(baseUrl).build();
  }

  public DatosClimaticos obtenerClimaActual(String ubicacion) {
    WeatherApiResponse response = restClient.get()
        .uri("/current.json?key={key}&q={q}", apiKey, ubicacion)
        .retrieve()
        .body(WeatherApiResponse.class);

    if (response == null) {
      throw new IllegalStateException("No se pudo obtener el clima de WeatherAPI");
    }
    return response.toDatoClimatico();
  }
}
