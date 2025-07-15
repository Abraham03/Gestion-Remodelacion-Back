package com.GestionRemodelacion.gestion.proyecto.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.GestionRemodelacion.gestion.cliente.model.Cliente;
import com.GestionRemodelacion.gestion.cliente.repository.ClienteRepository;
import com.GestionRemodelacion.gestion.dto.response.ApiResponse;
import com.GestionRemodelacion.gestion.empleado.model.Empleado;
import com.GestionRemodelacion.gestion.empleado.repository.EmpleadoRepository;
import com.GestionRemodelacion.gestion.proyecto.dto.request.ProyectoRequest;
import com.GestionRemodelacion.gestion.proyecto.dto.response.ProyectoResponse;
import com.GestionRemodelacion.gestion.proyecto.model.Proyecto;
import com.GestionRemodelacion.gestion.proyecto.repository.ProyectoRepository;

@Service
public class ProyectoService {

    private final ProyectoRepository proyectoRepository;
    private final ClienteRepository clienteRepository;
    private final EmpleadoRepository empleadoRepository;

    public ProyectoService(ProyectoRepository proyectoRepository, ClienteRepository clienteRepository, EmpleadoRepository empleadoRepository) {
        this.proyectoRepository = proyectoRepository;
        this.clienteRepository = clienteRepository;
        this.empleadoRepository = empleadoRepository;
    }

    @Transactional(readOnly = true)
    public List<ProyectoResponse> getAllProyectos() {
        return proyectoRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProyectoResponse getProyectoById(Integer id) {
        Proyecto proyecto = proyectoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Proyecto no encontrado con ID: " + id));
        return convertToDto(proyecto);
    }

    @Transactional
    public ProyectoResponse createProyecto(ProyectoRequest proyectoRequest) {
        Proyecto proyecto = new Proyecto();
        mapRequestToProyecto(proyectoRequest, proyecto);
        Proyecto savedProyecto = proyectoRepository.save(proyecto);
        return convertToDto(savedProyecto);
    }

    @Transactional
    public ProyectoResponse updateProyecto(Integer id, ProyectoRequest proyectoRequest) {
        Proyecto proyecto = proyectoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Proyecto no encontrado con ID: " + id));

        mapRequestToProyecto(proyectoRequest, proyecto);
        Proyecto updatedProyecto = proyectoRepository.save(proyecto);
        return convertToDto(updatedProyecto);
    }

    @Transactional
    public ApiResponse<Void> deleteProyecto(Integer id) {
        if (!proyectoRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Proyecto no encontrado con ID: " + id);
        }
        proyectoRepository.deleteById(id);
        return ApiResponse.success(null);
    }

    private void mapRequestToProyecto(ProyectoRequest request, Proyecto proyecto) {
        Cliente cliente = clienteRepository.findById(request.getIdCliente())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cliente no encontrado con ID: " + request.getIdCliente()));
        proyecto.setCliente(cliente);

        if (request.getIdEmpleadoResponsable() != null) {
            Empleado empleadoResponsable = empleadoRepository.findById(request.getIdEmpleadoResponsable())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Empleado responsable no encontrado con ID: " + request.getIdEmpleadoResponsable()));
            proyecto.setEmpleadoResponsable(empleadoResponsable);
        } else {
            proyecto.setEmpleadoResponsable(null);
        }

        proyecto.setNombreProyecto(request.getNombreProyecto());
        proyecto.setDescripcion(request.getDescripcion());
        proyecto.setDireccionPropiedad(request.getDireccionPropiedad());
        proyecto.setEstado(request.getEstado());
        proyecto.setFechaInicio(request.getFechaInicio());
        proyecto.setFechaFinEstimada(request.getFechaFinEstimada());
        proyecto.setFechaFinalizacionReal(request.getFechaFinalizacionReal());
        proyecto.setMontoContrato(request.getMontoContrato());
        proyecto.setMontoRecibido(request.getMontoRecibido());
        proyecto.setFechaUltimoPagoRecibido(request.getFechaUltimoPagoRecibido());
        proyecto.setCostoMaterialesConsolidado(request.getCostoMaterialesConsolidado());
        proyecto.setOtrosGastosDirectosConsolidado(request.getOtrosGastosDirectosConsolidado());
        proyecto.setProgresoPorcentaje(request.getProgresoPorcentaje());
        proyecto.setNotasProyecto(request.getNotasProyecto());
    }

    private ProyectoResponse convertToDto(Proyecto proyecto) {
        String nombreCliente = (proyecto.getCliente() != null) ? proyecto.getCliente().getNombreCliente() : null;
        String nombreEmpleadoResponsable = (proyecto.getEmpleadoResponsable() != null) ? proyecto.getEmpleadoResponsable().getNombreCompleto() : null;

        return new ProyectoResponse(
                proyecto.getId(),
                proyecto.getCliente().getId(),
                nombreCliente,
                proyecto.getNombreProyecto(),
                proyecto.getDescripcion(),
                proyecto.getDireccionPropiedad(),
                proyecto.getEstado(),
                proyecto.getFechaInicio(),
                proyecto.getFechaFinEstimada(),
                proyecto.getFechaFinalizacionReal(),
                (proyecto.getEmpleadoResponsable() != null) ? proyecto.getEmpleadoResponsable().getId() : null,
                nombreEmpleadoResponsable,
                proyecto.getMontoContrato(),
                proyecto.getMontoRecibido(),
                proyecto.getFechaUltimoPagoRecibido(),
                proyecto.getCostoMaterialesConsolidado(),
                proyecto.getOtrosGastosDirectosConsolidado(),
                proyecto.getProgresoPorcentaje(),
                proyecto.getNotasProyecto(),
                proyecto.getFechaCreacion()
        );
    }
}