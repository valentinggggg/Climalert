package ar.edu.utn.frba.ddsi.climaalert.models.repositories.impl;

import ar.edu.utn.frba.ddsi.climaalert.models.entities.datosClimaticos.DatosClimaticos;
import ar.edu.utn.frba.ddsi.climaalert.models.repositories.IDatoClimaticoRepository;
import ar.edu.utn.frba.ddsi.climaalert.utils.GeneradorIdSecuencial;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class inMemoryDatosClimaticoRepository implements IDatoClimaticoRepository {

  private final List<DatosClimaticos> historico = new ArrayList<>();
  private final GeneradorIdSecuencial generadorId = new GeneradorIdSecuencial();

  @Override
  public synchronized DatosClimaticos save(DatosClimaticos dato) {
    if (dato.getId() == null) {
      dato.setId(generadorId.siguiente());
    }
    historico.add(dato);
    return dato;
  }

  @Override
  public synchronized Optional<DatosClimaticos> obtenerUltimo() {
    return historico.isEmpty()
        ? Optional.empty()
        : Optional.of(historico.get(historico.size() - 1));
  }

  @Override
  public synchronized List<DatosClimaticos> findAll() {
    return new ArrayList<>(historico);
  }
}