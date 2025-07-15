package com.GestionRemodelacion.gestion.horastrabajadas.service;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.GestionRemodelacion.gestion.dto.response.ApiResponse;
import com.GestionRemodelacion.gestion.empleado.model.Empleado;
import com.GestionRemodelacion.gestion.empleado.repository.EmpleadoRepository;
import com.GestionRemodelacion.gestion.horastrabajadas.dto.request.HorasTrabajadasRequest;
import com.GestionRemodelacion.gestion.horastrabajadas.dto.response.HorasTrabajadasResponse;
import com.GestionRemodelacion.gestion.horastrabajadas.model.HorasTrabajadas;
import com.GestionRemodelacion.gestion.horastrabajadas.repository.HorasTrabajadasRepository;
import com.GestionRemodelacion.gestion.proyecto.model.Proyecto;
import com.GestionRemodelacion.gestion.proyecto.repository.ProyectoRepository;

@Service
public class HorasTrabajadasService {

    private final HorasTrabajadasRepository horasTrabajadasRepository;
    private final EmpleadoRepository empleadoRepository;
    private final ProyectoRepository proyectoRepository;

    public HorasTrabajadasService(HorasTrabajadasRepository horasTrabajadasRepository,
                                  EmpleadoRepository empleadoRepository,
                                  ProyectoRepository proyectoRepository) {
        this.horasTrabajadasRepository = horasTrabajadasRepository;
        this.empleadoRepository = empleadoRepository;
        this.proyectoRepository = proyectoRepository;
    }

    @Transactional(readOnly = true)
    public Page<HorasTrabajadasResponse> getAllHorasTrabajadas(Pageable pageable) {
        return horasTrabajadasRepository.findAll(pageable)
                .map(this::convertToDto);
    }

    @Transactional(readOnly = true)
    public HorasTrabajadasResponse getHorasTrabajadasById(Integer id) {
        HorasTrabajadas horasTrabajadas = horasTrabajadasRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Registro de horas trabajadas no encontrado con ID: " + id));
        return convertToDto(horasTrabajadas);
    }

    @Transactional
    public HorasTrabajadasResponse createHorasTrabajadas(HorasTrabajadasRequest horasTrabajadasRequest) {
        HorasTrabajadas horasTrabajadas = new HorasTrabajadas();

        Empleado empleado = empleadoRepository.findById(horasTrabajadasRequest.getIdEmpleado())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Empleado no encontrado con ID: " + horasTrabajadasRequest.getIdEmpleado()));
        horasTrabajadas.setEmpleado(empleado);

        Proyecto proyecto = proyectoRepository.findById(horasTrabajadasRequest.getIdProyecto())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Proyecto no encontrado con ID: " + horasTrabajadasRequest.getIdProyecto()));
        horasTrabajadas.setProyecto(proyecto);

        mapRequestToHorasTrabajadas(horasTrabajadasRequest, horasTrabajadas);
        horasTrabajadas.setFechaRegistro(LocalDateTime.now());
        HorasTrabajadas savedHorasTrabajadas = horasTrabajadasRepository.save(horasTrabajadas);
        return convertToDto(savedHorasTrabajadas);
    }

    @Transactional
    public HorasTrabajadasResponse updateHorasTrabajadas(Integer id, HorasTrabajadasRequest horasTrabajadasRequest) {
        HorasTrabajadas horasTrabajadas = horasTrabajadasRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Registro de horas trabajadas no encontrado con ID: " + id));

        if (horasTrabajadasRequest.getIdEmpleado() != null &&
            !horasTrabajadasRequest.getIdEmpleado().equals(horasTrabajadas.getEmpleado().getId())) {
            Empleado empleado = empleadoRepository.findById(horasTrabajadasRequest.getIdEmpleado())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Empleado no encontrado con ID: " + horasTrabajadasRequest.getIdEmpleado()));
            horasTrabajadas.setEmpleado(empleado);
        }

        if (horasTrabajadasRequest.getIdProyecto() != null &&
            !horasTrabajadasRequest.getIdProyecto().equals(horasTrabajadas.getProyecto().getId())) {
            Proyecto proyecto = proyectoRepository.findById(horasTrabajadasRequest.getIdProyecto())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Proyecto no encontrado con ID: " + horasTrabajadasRequest.getIdProyecto()));
            horasTrabajadas.setProyecto(proyecto);
        }

        mapRequestToHorasTrabajadas(horasTrabajadasRequest, horasTrabajadas);
        HorasTrabajadas updatedHorasTrabajadas = horasTrabajadasRepository.save(horasTrabajadas);
        return convertToDto(updatedHorasTrabajadas);
    }

    @Transactional
    public ApiResponse<Void> deleteHorasTrabajadas(Integer id) {
        if (!horasTrabajadasRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Registro de horas trabajadas no encontrado con ID: " + id);
        }
        horasTrabajadasRepository.deleteById(id);
        return new ApiResponse<>(HttpStatus.OK.value(), "Registro de horas trabajadas eliminado exitosamente.", null);
    }

    private void mapRequestToHorasTrabajadas(HorasTrabajadasRequest request, HorasTrabajadas horasTrabajadas) {
        // Assuming request.getFecha() now returns LocalDate, if you updated HorasTrabajadasRequest
        horasTrabajadas.setFecha(request.getFecha());
        horasTrabajadas.setHoras(request.getHoras());
        horasTrabajadas.setActividadRealizada(request.getActividadRealizada());
    }

    private HorasTrabajadasResponse convertToDto(HorasTrabajadas horasTrabajadas) {
        return new HorasTrabajadasResponse(
                horasTrabajadas.getId(),
                horasTrabajadas.getEmpleado() != null ? horasTrabajadas.getEmpleado().getId() : null,
                horasTrabajadas.getEmpleado() != null ? horasTrabajadas.getEmpleado().getNombreCompleto() : null,
                horasTrabajadas.getProyecto() != null ? horasTrabajadas.getProyecto().getId() : null,
                horasTrabajadas.getProyecto() != null ? horasTrabajadas.getProyecto().getNombreProyecto() : null,
                // These now correctly pass LocalDate and LocalDateTime
                horasTrabajadas.getFecha(),
                horasTrabajadas.getHoras(),
                horasTrabajadas.getActividadRealizada(),
                horasTrabajadas.getFechaRegistro()
        );
    }
}