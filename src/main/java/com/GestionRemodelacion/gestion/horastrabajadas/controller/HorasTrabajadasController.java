package com.GestionRemodelacion.gestion.horastrabajadas.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @GetMapping
    @PreAuthorize("hasAuthority('HORASTRABAJADAS_READ')") 
    public ResponseEntity<ApiResponse<Page<HorasTrabajadasResponse>>> getAllHorasTrabajadas(Pageable pageable) { 
        Page<HorasTrabajadasResponse> horasTrabajadasPage = horasTrabajadasService.getAllHorasTrabajadas(pageable);
        return ResponseEntity.ok(ApiResponse.success(horasTrabajadasPage));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('HORASTRABAJADAS_READ')") 
    public ResponseEntity<HorasTrabajadasResponse> getHorasTrabajadasById(@PathVariable Long id) {
        HorasTrabajadasResponse horasTrabajadas = horasTrabajadasService.getHorasTrabajadasById(id);
        return ResponseEntity.ok(horasTrabajadas);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('HORASTRABAJADAS_CREATE')") 
    public ResponseEntity<HorasTrabajadasResponse> createHorasTrabajadas(@Valid @RequestBody HorasTrabajadasRequest horasTrabajadasRequest) {
        HorasTrabajadasResponse createdHorasTrabajadas = horasTrabajadasService.createHorasTrabajadas(horasTrabajadasRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdHorasTrabajadas);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('HORASTRABAJADAS_UPDATE')") 
    public ResponseEntity<HorasTrabajadasResponse> updateHorasTrabajadas(@PathVariable Long id, @Valid @RequestBody HorasTrabajadasRequest horasTrabajadasRequest) {
        HorasTrabajadasResponse updatedHorasTrabajadas = horasTrabajadasService.updateHorasTrabajadas(id, horasTrabajadasRequest);
        return ResponseEntity.ok(updatedHorasTrabajadas);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('HORASTRABAJADAS_DELETE')") 
    public ResponseEntity<ApiResponse<Void>> deleteHorasTrabajadas(@PathVariable Long id) {
        ApiResponse<Void> apiResponse = horasTrabajadasService.deleteHorasTrabajadas(id);
        return ResponseEntity.ok(apiResponse);
    }
}