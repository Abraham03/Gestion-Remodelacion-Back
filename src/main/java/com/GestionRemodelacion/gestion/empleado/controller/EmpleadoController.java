package com.GestionRemodelacion.gestion.empleado.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable; // Importar Page
import org.springframework.data.web.PageableDefault; // Importar Pageable
import org.springframework.http.HttpStatus; // Importar PageableDefault para valores por defecto
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.GestionRemodelacion.gestion.dto.response.ApiResponse;
import com.GestionRemodelacion.gestion.empleado.dto.request.EmpleadoRequest;
import com.GestionRemodelacion.gestion.empleado.dto.response.EmpleadoResponse;
import com.GestionRemodelacion.gestion.empleado.service.EmpleadoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/empleados")
public class EmpleadoController {

    private final EmpleadoService empleadoService;

    public EmpleadoController(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }

    // *** MODIFICACIÓN PARA PAGINACIÓN ***
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<EmpleadoResponse>> getAllEmpleados( // Cambia el tipo de retorno a Page
            @PageableDefault(size = 10, page = 0, sort = "nombreCompleto") Pageable pageable) { // Inyecta Pageable
        Page<EmpleadoResponse> empleadosPage = empleadoService.getAllEmpleados(pageable);
        return ResponseEntity.ok(empleadosPage);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EmpleadoResponse> getEmpleadoById(@PathVariable Integer id) {
        EmpleadoResponse empleado = empleadoService.getEmpleadoById(id);
        return ResponseEntity.ok(empleado);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EmpleadoResponse> createEmpleado(@Valid @RequestBody EmpleadoRequest empleadoRequest) {
        EmpleadoResponse createdEmpleado = empleadoService.createEmpleado(empleadoRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEmpleado);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EmpleadoResponse> updateEmpleado(@PathVariable Integer id, @Valid @RequestBody EmpleadoRequest empleadoRequest) {
        EmpleadoResponse updatedEmpleado = empleadoService.updateEmpleado(id, empleadoRequest);
        return ResponseEntity.ok(updatedEmpleado);
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> changeEmpleadoStatus(@PathVariable Integer id, @RequestParam Boolean activo) {
        ApiResponse<Void> apiResponse = empleadoService.changeEmpleadoStatus(id, activo);
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deactivateEmpleado(@PathVariable Integer id) {
        ApiResponse<Void> apiResponse = empleadoService.deactivateEmpleado(id);
        return ResponseEntity.ok(apiResponse);
    }
}