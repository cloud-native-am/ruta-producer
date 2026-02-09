package com.musabeli.ruta_producer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RutaDTO {
    private String patente;
    private String rutaInicio;
    private String rutaFin;
    private LocalDateTime horaLlegada;
    private LocalDateTime horaSalida;
    private LocalDateTime fechaActualizacion;
}