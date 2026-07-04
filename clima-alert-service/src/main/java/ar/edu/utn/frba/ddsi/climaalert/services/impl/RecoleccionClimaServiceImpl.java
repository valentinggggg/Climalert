package ar.edu.utn.frba.ddsi.climaalert.services.impl;

import ar.edu.utn.frba.ddsi.climaalert.client.WeatherClient;
import ar.edu.utn.frba.ddsi.climaalert.models.entities.datosClimaticos.DatosClimaticos;
import ar.edu.utn.frba.ddsi.climaalert.models.repositories.IDatoClimaticoRepository;
import ar.edu.utn.frba.ddsi.climaalert.services.IRecoleccionClimaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RecoleccionClimaServiceImpl implements IRecoleccionClimaService {

  private static final Logger log = LoggerFactory.getLogger(RecoleccionClimaServiceImpl.class);

  private final WeatherClient weatherClient;
  private final IDatoClimaticoRepository datoClimaticoRepository;

  @Value("${climalert.ubicacion}")
  private String ubicacion;

  public RecoleccionClimaServiceImpl(
      WeatherClient weatherClient,
      IDatoClimaticoRepository datoClimaticoRepository) {
    this.weatherClient = weatherClient;
    this.datoClimaticoRepository = datoClimaticoRepository;
  }

  @Override
  public void recolectarClima() {
    try {
      DatosClimaticos dato = weatherClient.obtenerClimaActual(ubicacion);
      datoClimaticoRepository.save(dato);
      log.info("Dato climatico registrado: {}", dato);
    } catch (Exception e) {
      log.error("Error al recolectar datos climaticos", e);
    }
  }
}