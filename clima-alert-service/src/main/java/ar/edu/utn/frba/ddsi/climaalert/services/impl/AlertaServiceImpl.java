package ar.edu.utn.frba.ddsi.climaalert.services.impl;

import ar.edu.utn.frba.ddsi.climaalert.models.entities.alertaClimatica.AlertaClimatica;
import ar.edu.utn.frba.ddsi.climaalert.models.entities.datosClimaticos.DatosClimaticos;
import ar.edu.utn.frba.ddsi.climaalert.models.repositories.IDatoClimaticoRepository;
import ar.edu.utn.frba.ddsi.climaalert.services.IAlertaService;
import ar.edu.utn.frba.ddsi.climaalert.services.INotificacionesService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AlertaServiceImpl implements IAlertaService {

  private static final Logger log = LoggerFactory.getLogger(AlertaServiceImpl.class);

  private final IDatoClimaticoRepository datoClimaticoRepository;
  private final INotificacionesService notificacionService;

  public AlertaServiceImpl(
      IDatoClimaticoRepository datoClimaticoRepository,
      INotificacionesService notificacionService) {
    this.datoClimaticoRepository = datoClimaticoRepository;
    this.notificacionService = notificacionService;
  }

  @Override
  public void analizarClima() {
    Optional<DatosClimaticos> ultimoDato = datoClimaticoRepository.obtenerUltimo();

    if (ultimoDato.isEmpty()) {
      log.info("Todavia no hay datos climaticos registrados para analizar");
      return;
    }

    DatosClimaticos dato = ultimoDato.get();
    if (dato.esCondicionCritica()) {
      AlertaClimatica alerta = AlertaClimatica.from(dato);
      log.warn("Condicion critica detectada: {}", dato);
      notificacionService.notificarAlerta(alerta);
    }
  }
}
