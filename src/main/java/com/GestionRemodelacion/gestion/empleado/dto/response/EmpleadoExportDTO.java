package com.GestionRemodelacion.gestion.empleado.dto.response;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import com.GestionRemodelacion.gestion.empleado.model.Empleado;
import com.GestionRemodelacion.gestion.export.Exportable;

public class EmpleadoExportDTO implements Exportable{

    private final Empleado empleado;
    public EmpleadoExportDTO(Empleado empleado) {
        this.empleado = empleado;
    }

    @Override
    public List<String> getExportHeaders() {
        return Arrays.asList("Nombre", "Teléfono", "Cargo", "Telefono", "Fecha de Contratacion", "Costo/Hora", "Activo", "Fecha Registro");
    }

    @Override
    public List<List<String>> getExportData() {
        String fechaFormateada = this.empleado.getFechaContratacion() != null
                ? this.empleado.getFechaContratacion().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
                : "";
        String fechaRegistroFormateada = this.empleado.getFechaRegistro() != null
                ? this.empleado.getFechaRegistro().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
                : "";    
                    
        return Arrays.asList(Arrays.asList(
            this.empleado.getNombreCompleto(),
            this.empleado.getTelefonoContacto(),
            this.empleado.getRolCargo(),
            this.empleado.getTelefonoContacto(),
            fechaFormateada,
            this.empleado.getCostoPorHora().toString(),
            this.empleado.getActivo().toString(),
            fechaRegistroFormateada
        ));
    }

}
