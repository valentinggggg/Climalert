package ar.edu.utn.frba.ddsi.climaalert.scheduler;

import ar.edu.utn.frba.ddsi.climaalert.services.IRecoleccionClimaService;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class RecoleccionScheduler {

  private final IRecoleccionClimaService recoleccionClimaService;

  public RecoleccionScheduler(IRecoleccionClimaService recoleccionClimaService) {
    this.recoleccionClimaService = recoleccionClimaService;
  }

  @Scheduled(cron = "0 0/5 * * * *")
  public void ejecutarRecoleccion() {
    recoleccionClimaService.recolectarClima();
  }
}
