package ar.edu.utn.frba.ddsi.climaalert.models.repositories;

import ar.edu.utn.frba.ddsi.climaalert.models.entities.datosClimaticos.DatosClimaticos;
import java.util.List;
import java.util.Optional;

public interface IDatoClimaticoRepository {
  DatosClimaticos save(DatosClimaticos dato);
  Optional<DatosClimaticos> obtenerUltimo();
  List<DatosClimaticos> findAll();
}
