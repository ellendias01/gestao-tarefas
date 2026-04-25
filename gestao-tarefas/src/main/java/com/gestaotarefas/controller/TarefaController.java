package com.gestaotarefas.controller;

import com.gestaotarefas.model.Tarefa;
import com.gestaotarefas.service.TarefaService;
import com.gestaotarefas.exception.ResourceNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tarefas")
@CrossOrigin(origins = "*")
@Tag(name = "Tarefas", description = "Endpoints para gerenciamento de tarefas")
public class TarefaController {
    
    private static final Logger logger = LoggerFactory.getLogger(TarefaController.class);
    
    @Autowired
    private TarefaService tarefaService;
    
    @PostMapping
    @Operation(summary = "Criar nova tarefa", description = "Cria uma nova tarefa com os dados fornecidos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Tarefa criada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "409", description = "Já existe tarefa com este nome")
    })
    public ResponseEntity<Tarefa> criarTarefa(@Valid @RequestBody Tarefa tarefa) {
        logger.info("🌐 [API] POST /tarefas - Recebendo requisição para criar tarefa");
        Tarefa novaTarefa = tarefaService.criarTarefa(tarefa);
        logger.info("🌐 [API] Retornando status 201 CREATED - Tarefa ID: {}", novaTarefa.getId());
        return new ResponseEntity<>(novaTarefa, HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar tarefa", description = "Atualiza uma tarefa existente pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Tarefa atualizada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Tarefa não encontrada"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public ResponseEntity<Tarefa> atualizarTarefa(
            @Parameter(description = "ID da tarefa", required = true) @PathVariable Long id, 
            @Valid @RequestBody Tarefa tarefa) {
        logger.info("🌐 [API] PUT /tarefas/{} - Recebendo requisição para atualizar", id);
        try {
            Tarefa tarefaAtualizada = tarefaService.atualizarTarefa(id, tarefa);
            logger.info("🌐 [API] Retornando status 200 OK - Tarefa atualizada");
            return ResponseEntity.ok(tarefaAtualizada);
        } catch (ResourceNotFoundException e) {
            logger.warn("🌐 [API] Tarefa não encontrada para atualização - ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar tarefa", description = "Remove uma tarefa existente pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Tarefa removida com sucesso"),
        @ApiResponse(responseCode = "404", description = "Tarefa não encontrada")
    })
    public ResponseEntity<Void> deletarTarefa(
            @Parameter(description = "ID da tarefa", required = true) @PathVariable Long id) {
        logger.info("🌐 [API] DELETE /tarefas/{} - Recebendo requisição para deletar", id);
        try {
            tarefaService.deletarTarefa(id);
            logger.info("🌐 [API] Retornando status 204 NO CONTENT - Tarefa deletada");
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            logger.warn("🌐 [API] Tarefa não encontrada para deleção - ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping
    @Operation(summary = "Listar todas as tarefas", description = "Retorna uma lista com todas as tarefas cadastradas")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    public ResponseEntity<List<Tarefa>> listarTodasTarefas() {
        logger.info("🌐 [API] GET /tarefas - Recebendo requisição para listar");
        List<Tarefa> tarefas = tarefaService.listarTodasTarefas();
        logger.info("🌐 [API] Retornando status 200 OK - {} tarefa(s)", tarefas.size());
        return ResponseEntity.ok(tarefas);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Buscar tarefa por ID", description = "Retorna os detalhes de uma tarefa específica")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Tarefa encontrada"),
        @ApiResponse(responseCode = "404", description = "Tarefa não encontrada")
    })
    public ResponseEntity<Tarefa> buscarTarefaPorId(
            @Parameter(description = "ID da tarefa", required = true) @PathVariable Long id) {
        logger.info("🌐 [API] GET /tarefas/{} - Recebendo requisição para buscar", id);
        try {
            Tarefa tarefa = tarefaService.buscarTarefaPorId(id);
            logger.info("🌐 [API] Retornando status 200 OK - Tarefa encontrada");
            return ResponseEntity.ok(tarefa);
        } catch (ResourceNotFoundException e) {
            logger.warn("🌐 [API] Tarefa não encontrada - ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }
}