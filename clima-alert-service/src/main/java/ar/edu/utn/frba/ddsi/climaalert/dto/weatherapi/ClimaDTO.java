package ar.edu.utn.frba.ddsi.climaalert.dto.weatherapi;

public record ClimaDTO(
    double temp_c,
    int humidity,
    CondicionDTO condition
){}
