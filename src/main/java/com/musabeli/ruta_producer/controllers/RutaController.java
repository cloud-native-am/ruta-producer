package com.musabeli.ruta_producer.controllers;

import com.musabeli.ruta_producer.dto.RutaDTO;
import com.musabeli.ruta_producer.services.RutaProducerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/gps")
@Slf4j
public class RutaController {

    @Autowired
    private RutaProducerService producerService;

    @PostMapping("/ruta")
    public ResponseEntity<String> sendRuta(@RequestBody RutaDTO rutaDto){
        log.info("Recibiendo actualización Ruta desde Postman - Patente: {}", rutaDto.getPatente());

        try {
            producerService.sendRuta(rutaDto);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Ruta actualizada del vehículo con patente " + rutaDto.getPatente());
        } catch (Exception ex){
            log.error("Error al enviar ruta: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al enviar ruta: " + ex.getMessage());
        }
    }
}