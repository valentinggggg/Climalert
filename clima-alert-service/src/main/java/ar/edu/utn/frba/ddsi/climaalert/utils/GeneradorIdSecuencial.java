package ar.edu.utn.frba.ddsi.climaalert.utils;

public class GeneradorIdSecuencial {
  private long siguiente;

  public GeneradorIdSecuencial() {
    this(1L);
  }

  public GeneradorIdSecuencial(long valorInicial) {
    this.siguiente = valorInicial;
  }

  public long siguiente() {
    return siguiente++;
  }
}
