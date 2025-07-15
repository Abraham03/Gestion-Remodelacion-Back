package com.GestionRemodelacion.gestion.cliente.controller;

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

import com.GestionRemodelacion.gestion.cliente.dto.request.ClienteRequest;
import com.GestionRemodelacion.gestion.cliente.dto.response.ClienteResponse;
import com.GestionRemodelacion.gestion.cliente.service.ClienteService;
import com.GestionRemodelacion.gestion.dto.response.ApiResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    // *** MODIFICACIÓN PARA PAGINACIÓN ***
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Page<ClienteResponse>> getAllClientes(
            @PageableDefault(size = 10, page = 0, sort = "nombreCliente") Pageable pageable) {
        Page<ClienteResponse> clientesPage = clienteService.getAllClientes(pageable);
        return ResponseEntity.ok(clientesPage);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<ClienteResponse> getClienteById(@PathVariable Integer id) {
        ClienteResponse cliente = clienteService.getClienteById(id);
        return ResponseEntity.ok(cliente);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ClienteResponse> createCliente(@Valid @RequestBody ClienteRequest clienteRequest) {
        ClienteResponse createdCliente = clienteService.createCliente(clienteRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCliente);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ClienteResponse> updateCliente(@PathVariable Integer id, @Valid @RequestBody ClienteRequest clienteRequest) {
        ClienteResponse updatedCliente = clienteService.updateCliente(id, clienteRequest);
        return ResponseEntity.ok(updatedCliente);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteCliente(@PathVariable Integer id) {
        ApiResponse<Void> apiResponse = clienteService.deleteCliente(id);
        return ResponseEntity.ok(apiResponse);
    }
}