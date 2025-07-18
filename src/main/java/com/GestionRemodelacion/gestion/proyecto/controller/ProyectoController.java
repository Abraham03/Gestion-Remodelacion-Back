package com.GestionRemodelacion.gestion.proyecto.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.GestionRemodelacion.gestion.dto.response.ApiResponse;
import com.GestionRemodelacion.gestion.proyecto.dto.request.ProyectoRequest;
import com.GestionRemodelacion.gestion.proyecto.dto.response.ProyectoResponse;
import com.GestionRemodelacion.gestion.proyecto.service.ProyectoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/proyectos")
public class ProyectoController {

    private final ProyectoService proyectoService;

    public ProyectoController(ProyectoService proyectoService) {
        this.proyectoService = proyectoService;
    }

    @GetMapping
   @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ProyectoResponse>> getAllProyectos() {
        List<ProyectoResponse> proyectos = proyectoService.getAllProyectos();
        return ResponseEntity.ok(proyectos);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProyectoResponse> getProyectoById(@PathVariable Integer id) {
        ProyectoResponse proyecto = proyectoService.getProyectoById(id);
        return ResponseEntity.ok(proyecto);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProyectoResponse> createProyecto(@Valid @RequestBody ProyectoRequest proyectoRequest) {
        ProyectoResponse createdProyecto = proyectoService.createProyecto(proyectoRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProyecto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProyectoResponse> updateProyecto(@PathVariable Integer id, @Valid @RequestBody ProyectoRequest proyectoRequest) {
        ProyectoResponse updatedProyecto = proyectoService.updateProyecto(id, proyectoRequest);
        return ResponseEntity.ok(updatedProyecto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteProyecto(@PathVariable Integer id) {
        proyectoService.deleteProyecto(id);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}