package org.msvc_clientes.controller;

import org.msvc_clientes.client.venta.pojo.Venta;
import org.msvc_clientes.model.entity.Cliente;
import org.msvc_clientes.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping("/listar")
    public ResponseEntity<List<Cliente>> clientes() {
        return ResponseEntity.ok(clienteService.clientes());
    }

    @PostMapping("/guardar")
    public ResponseEntity<?> guardar(
            @RequestBody Cliente cliente
    ) {
        Cliente guardarCliente = clienteService.guardar(cliente);

        if(guardarCliente == null){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .build();
        }

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(guardarCliente);
    }

    @PutMapping("/edita/{id}")
    public ResponseEntity<?> editar(
            @PathVariable Long id,
            @RequestBody Cliente cliente
    ) {
        Cliente editarCliente = clienteService.editar(id, cliente);

        if(editarCliente == null){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .build();
        }

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(editarCliente);
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<?> buscarPorId(
            @PathVariable Long id
    ) {
        Cliente cliente = clienteService.buscarPorId(id);
        if(cliente != null) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(cliente);
        }
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .build();
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Boolean> eliminar(
            @PathVariable Long id
    ) {
        boolean eliminar = clienteService.eliminar(id);
        if(eliminar) {
            return ResponseEntity.ok(eliminar);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    //meotdo remoto
    @GetMapping("/listar-cliente-con-ventas/{idCliente}")
    public ResponseEntity<List<Venta>> listarClientes(@PathVariable Long idCliente){
        return ResponseEntity.ok(clienteService.listarVentasPorCliente(idCliente).get());
    }
}
