package ar.edu.utn.frba.ddsi.climaalert.services.impl;

import ar.edu.utn.frba.ddsi.climaalert.models.entities.alertaClimatica.AlertaClimatica;
import ar.edu.utn.frba.ddsi.climaalert.services.INotificacionesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NotificacionesService implements INotificacionesService {

  private static final Logger log = LoggerFactory.getLogger(NotificacionesService.class);

  private final JavaMailSender mailSender;

  @Value("${climalert.destinatarios}")
  private String[] destinatarios;

  public NotificacionesService(JavaMailSender mailSender) {
    this.mailSender = mailSender;
  }

  @Override
  public void notificarAlerta(AlertaClimatica alerta) {
    var dato = alerta.getDatoClimatico();

    SimpleMailMessage mensaje = new SimpleMailMessage();
    mensaje.setTo(destinatarios);
    mensaje.setSubject("[Climalert] Alerta meteorologica en " + dato.getUbicacion());
    mensaje.setText(String.format("""
        Se registró una condición climática crítica en %s.
        La última medición, realizada el %s, indicó una temperatura de %.1f °C, una humedad del %d%% y la condición "%s".
        La alerta fue generada el %s.
        """, dato.getUbicacion(), dato.getFechaHora(), dato.getTemperaturaC(), (int) dato.getHumedad(), dato.getDescripcion(), alerta.getFechaGeneracion()));

    try {
      mailSender.send(mensaje);
      log.info("Correo de alerta enviado a {}", (Object) destinatarios);
    } catch (Exception e) {
      log.error("Error al enviar correo de alerta", e);
    }
  }
}