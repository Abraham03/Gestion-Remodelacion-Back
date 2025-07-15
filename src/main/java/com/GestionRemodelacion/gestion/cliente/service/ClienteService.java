package com.GestionRemodelacion.gestion.cliente.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException; // Importar Page

import com.GestionRemodelacion.gestion.cliente.dto.request.ClienteRequest; // Importar Pageable
import com.GestionRemodelacion.gestion.cliente.dto.response.ClienteResponse;
import com.GestionRemodelacion.gestion.cliente.model.Cliente;
import com.GestionRemodelacion.gestion.cliente.repository.ClienteRepository;
import com.GestionRemodelacion.gestion.dto.response.ApiResponse;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    // *** NUEVO MÉTODO PARA PAGINACIÓN DE CLIENTES ***
    @Transactional(readOnly = true)
    public Page<ClienteResponse> getAllClientes(Pageable pageable) {
        return clienteRepository.findAll(pageable) // Usa findAll con Pageable
                .map(this::convertToDto); // Mapea la Page de Cliente a Page de ClienteResponse
    }

    @Transactional(readOnly = true)
    public ClienteResponse getClienteById(Integer id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado con ID: " + id));
        return convertToDto(cliente);
    }

    @Transactional
    public ClienteResponse createCliente(ClienteRequest clienteRequest) {
        Cliente cliente = new Cliente();
        mapRequestToCliente(clienteRequest, cliente);
        Cliente savedCliente = clienteRepository.save(cliente);
        return convertToDto(savedCliente);
    }

    @Transactional
    public ClienteResponse updateCliente(Integer id, ClienteRequest clienteRequest) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado con ID: " + id));

        mapRequestToCliente(clienteRequest, cliente);
        Cliente updatedCliente = clienteRepository.save(cliente);
        return convertToDto(updatedCliente);
    }

    @Transactional
    public ApiResponse<Void> deleteCliente(Integer id) {
        if (!clienteRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado con ID: " + id);
        }
        clienteRepository.deleteById(id);
        return new ApiResponse<>(HttpStatus.OK.value(), "Cliente eliminado exitosamente.", null);
    }

    // Método auxiliar para mapear ClienteRequest a Cliente
    private void mapRequestToCliente(ClienteRequest request, Cliente cliente) {
        cliente.setNombreCliente(request.getNombreCliente());
        cliente.setTelefonoContacto(request.getTelefonoContacto());
        cliente.setDireccion(request.getDireccion());
        cliente.setNotas(request.getNotas());
        // La fecha_registro usualmente se setea en la entidad o en la DB directamente en el insert
        // Si la manejas en el servicio, podrías añadir: cliente.setFechaRegistro(LocalDateTime.now());
    }

    // Método auxiliar para convertir Entidad a DTO
    private ClienteResponse convertToDto(Cliente cliente) {
        return new ClienteResponse(
                cliente.getId(),
                cliente.getNombreCliente(),
                cliente.getTelefonoContacto(),
                cliente.getDireccion(),
                cliente.getNotas(),
                cliente.getFechaRegistro()
        );
    }
}