package ar.edu.utn.frba.ddsi.climaalert.services;

import ar.edu.utn.frba.ddsi.climaalert.models.entities.alertaClimatica.AlertaClimatica;

public interface INotificacionesService {
  void notificarAlerta(AlertaClimatica alerta);
}
