package com.GestionRemodelacion.gestion.horastrabajadas.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize; // Importar Page
import org.springframework.web.bind.annotation.DeleteMapping; // Importar Pageable
import org.springframework.web.bind.annotation.GetMapping; // Importar PageableDefault
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.GestionRemodelacion.gestion.dto.response.ApiResponse;
import com.GestionRemodelacion.gestion.horastrabajadas.dto.request.HorasTrabajadasRequest;
import com.GestionRemodelacion.gestion.horastrabajadas.dto.response.HorasTrabajadasResponse;
import com.GestionRemodelacion.gestion.horastrabajadas.service.HorasTrabajadasService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/horas-trabajadas")
public class HorasTrabajadasController {

    private final HorasTrabajadasService horasTrabajadasService;

    public HorasTrabajadasController(HorasTrabajadasService horasTrabajadasService) {
        this.horasTrabajadasService = horasTrabajadasService;
    }

    // *** MODIFICACIÓN PARA PAGINACIÓN ***
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Page<HorasTrabajadasResponse>> getAllHorasTrabajadas(
            @PageableDefault(size = 10, page = 0, sort = "fecha") Pageable pageable) { // Ordenar por fecha por defecto
        Page<HorasTrabajadasResponse> horasTrabajadasPage = horasTrabajadasService.getAllHorasTrabajadas(pageable);
        return ResponseEntity.ok(horasTrabajadasPage);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<HorasTrabajadasResponse> getHorasTrabajadasById(@PathVariable Integer id) {
        HorasTrabajadasResponse horasTrabajadas = horasTrabajadasService.getHorasTrabajadasById(id);
        return ResponseEntity.ok(horasTrabajadas);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')") // Asumo que usuarios normales pueden registrar horas
    public ResponseEntity<HorasTrabajadasResponse> createHorasTrabajadas(@Valid @RequestBody HorasTrabajadasRequest horasTrabajadasRequest) {
        HorasTrabajadasResponse createdHorasTrabajadas = horasTrabajadasService.createHorasTrabajadas(horasTrabajadasRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdHorasTrabajadas);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HorasTrabajadasResponse> updateHorasTrabajadas(@PathVariable Integer id, @Valid @RequestBody HorasTrabajadasRequest horasTrabajadasRequest) {
        HorasTrabajadasResponse updatedHorasTrabajadas = horasTrabajadasService.updateHorasTrabajadas(id, horasTrabajadasRequest);
        return ResponseEntity.ok(updatedHorasTrabajadas);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteHorasTrabajadas(@PathVariable Integer id) {
        ApiResponse<Void> apiResponse = horasTrabajadasService.deleteHorasTrabajadas(id);
        return ResponseEntity.ok(apiResponse);
    }
}