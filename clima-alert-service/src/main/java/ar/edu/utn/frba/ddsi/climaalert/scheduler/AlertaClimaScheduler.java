package ar.edu.utn.frba.ddsi.climaalert.scheduler;

import ar.edu.utn.frba.ddsi.climaalert.services.IAlertaService;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class AlertaClimaScheduler {

  private final IAlertaService alertaService;

  public AlertaClimaScheduler(IAlertaService alertaService) {
    this.alertaService = alertaService;
  }

  @Scheduled(cron = "0 * * * * *")
  public void ejecutarAlerta() {
    alertaService.analizarClima();
  }
}