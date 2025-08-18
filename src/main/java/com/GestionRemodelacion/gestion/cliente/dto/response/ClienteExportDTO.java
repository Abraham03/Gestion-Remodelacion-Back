package com.GestionRemodelacion.gestion.cliente.dto.response;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import com.GestionRemodelacion.gestion.cliente.model.Cliente;
import com.GestionRemodelacion.gestion.export.Exportable;

public class ClienteExportDTO implements Exportable {

  private final Cliente cliente;

    public ClienteExportDTO(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public List<String> getExportHeaders() {
        return Arrays.asList("Nombre", "Teléfono", "Dirección", "Notas", "Fecha de Registro");
    }

    @Override
    public List<List<String>> getExportData() {
        String fechaFormateada = this.cliente.getFechaRegistro() != null
                ? this.cliente.getFechaRegistro().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                : "";

        return Arrays.asList(Arrays.asList(
            this.cliente.getNombreCliente(),
            this.cliente.getTelefonoContacto(),
            this.cliente.getDireccion(),
            this.cliente.getNotas(),
            fechaFormateada
        ));
    }   

}
