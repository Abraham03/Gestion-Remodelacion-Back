package com.GestionRemodelacion.gestion.empleado.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.GestionRemodelacion.gestion.dto.response.ApiResponse;
import com.GestionRemodelacion.gestion.empleado.dto.request.EmpleadoRequest;
import com.GestionRemodelacion.gestion.empleado.dto.response.EmpleadoResponse;
import com.GestionRemodelacion.gestion.empleado.model.Empleado;
import com.GestionRemodelacion.gestion.empleado.repository.EmpleadoRepository;

@Service
public class EmpleadoService {

    private final EmpleadoRepository empleadoRepository;

    public EmpleadoService(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    // *** MODIFICACIÓN PARA PAGINACIÓN ***
    @Transactional(readOnly = true)
    public Page<EmpleadoResponse> getAllEmpleados(Pageable pageable) {
        return empleadoRepository.findAll(pageable)
                .map(this::convertToDto);
    }

    @Transactional(readOnly = true)
    public EmpleadoResponse getEmpleadoById(Integer id) {
        Empleado empleado = empleadoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Empleado no encontrado con ID: " + id));
        return convertToDto(empleado);
    }

    @Transactional
    public EmpleadoResponse createEmpleado(EmpleadoRequest empleadoRequest) {
        Empleado empleado = new Empleado();
        mapRequestToEmpleado(empleadoRequest, empleado);
        if (empleado.getActivo() == null) {
            empleado.setActivo(true);
        }
        // fechaRegistro will be set automatically by @PrePersist in Empleado model
        Empleado savedEmpleado = empleadoRepository.save(empleado);
        return convertToDto(savedEmpleado);
    }

    @Transactional
    public EmpleadoResponse updateEmpleado(Integer id, EmpleadoRequest empleadoRequest) {
        Empleado empleado = empleadoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Empleado no encontrado con ID: " + id));

        mapRequestToEmpleado(empleadoRequest, empleado);
        Empleado updatedEmpleado = empleadoRepository.save(empleado);
        return convertToDto(updatedEmpleado);
    }

    @Transactional
    public ApiResponse<Void> changeEmpleadoStatus(Integer id, Boolean activo) {
        Empleado empleado = empleadoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Empleado no encontrado con ID: " + id));

        if (empleado.getActivo().equals(activo)) {
            return ApiResponse.error(HttpStatus.CONFLICT.value(), "El empleado ya tiene el estado deseado.");
        }

        empleado.setActivo(activo);
        empleadoRepository.save(empleado);
        String status = activo ? "activado" : "inactivado";
        return new ApiResponse<>(HttpStatus.OK.value(), "Empleado " + status + " exitosamente.", null);
    }

    @Transactional
    public ApiResponse<Void> deactivateEmpleado(Integer id) {
        return changeEmpleadoStatus(id, false);
    }

    @Transactional
    public ApiResponse<Void> deleteEmpleado(Integer id) {
        if (!empleadoRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Empleado no encontrado con ID: " + id);
        }
        empleadoRepository.deleteById(id);
        return new ApiResponse<>(HttpStatus.OK.value(), "Empleado eliminado físicamente de la base de datos.", null);
    }

    private void mapRequestToEmpleado(EmpleadoRequest request, Empleado empleado) {
        empleado.setNombreCompleto(request.getNombreCompleto());
        empleado.setRolCargo(request.getRolCargo());
        empleado.setTelefonoContacto(request.getTelefonoContacto());
        empleado.setFechaContratacion(request.getFechaContratacion());
        empleado.setCostoPorHora(request.getCostoPorHora());
        if (request.getActivo() != null) {
            empleado.setActivo(request.getActivo());
        }
        empleado.setNotas(request.getNotas());
        // Do NOT set fechaRegistro from request, it's an internal timestamp
    }

    private EmpleadoResponse convertToDto(Empleado empleado) {
        return new EmpleadoResponse(
                empleado.getId(),
                empleado.getNombreCompleto(),
                empleado.getRolCargo(),
                empleado.getTelefonoContacto(),
                empleado.getFechaContratacion(), // This is LocalDate
                empleado.getCostoPorHora(),
                empleado.getActivo(),
                empleado.getNotas(),
                empleado.getFechaRegistro() // This is now LocalDateTime
        );
    }
}