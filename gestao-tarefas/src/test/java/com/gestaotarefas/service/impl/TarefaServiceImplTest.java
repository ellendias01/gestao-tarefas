package com.gestaotarefas.service.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gestaotarefas.exception.ResourceNotFoundException;
import com.gestaotarefas.model.Tarefa;
import com.gestaotarefas.model.Tarefa.StatusTarefa;
import com.gestaotarefas.repository.TarefaRepository;

@ExtendWith(MockitoExtension.class)
public class TarefaServiceImplTest {

    @Mock
    private TarefaRepository repository;

    @InjectMocks
    private TarefaServiceImpl service;

    private Tarefa tarefa;

    @BeforeEach
    void setup() {
        tarefa = new Tarefa();
        tarefa.setId(1L);
        tarefa.setNome("Estudar Spring Boot");
        tarefa.setDescricao("Revisar testes unitários");
        tarefa.setStatus(StatusTarefa.PENDENTE);
    }

    @Test
    void deveCriarTarefaComSucesso() {
        when(repository.save(any(Tarefa.class))).thenReturn(tarefa);
        when(repository.existsByNome(any(String.class))).thenReturn(false);

        Tarefa resultado = service.criarTarefa(tarefa);

        assertNotNull(resultado);
        assertEquals("Estudar Spring Boot", resultado.getNome());
        verify(repository, times(1)).save(any(Tarefa.class));
    }

    @Test
    void deveLancarExcecaoAoTentarCriarTarefaComNomeDuplicado() {
        when(repository.existsByNome("Estudar Spring Boot")).thenReturn(true);

        assertThrows(RuntimeException.class, () -> service.criarTarefa(tarefa));
    }

    @Test
    void deveAtualizarTarefaComSucesso() {
        when(repository.findById(1L)).thenReturn(Optional.of(tarefa));
        when(repository.save(any(Tarefa.class))).thenReturn(tarefa);

        Tarefa tarefaAtualizada = new Tarefa();
        tarefaAtualizada.setNome("Novo Nome");
        tarefaAtualizada.setStatus(StatusTarefa.CONCLUIDO);

        Tarefa resultado = service.atualizarTarefa(1L, tarefaAtualizada);

        assertEquals("Novo Nome", resultado.getNome());
        assertEquals(StatusTarefa.CONCLUIDO, resultado.getStatus());
    }

    @Test
    void deveLancarExcecaoAoAtualizarTarefaInexistente() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            service.atualizarTarefa(99L, tarefa);
        });
    }

    @Test
    void deveDeletarTarefaComSucesso() {
        when(repository.findById(1L)).thenReturn(Optional.of(tarefa));
        doNothing().when(repository).delete(tarefa);

        assertDoesNotThrow(() -> service.deletarTarefa(1L));
        verify(repository, times(1)).delete(tarefa);
    }

    @Test
    void deveLancarExcecaoAoDeletarTarefaInexistente() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            service.deletarTarefa(99L);
        });
    }

    @Test
    void deveListarTodasTarefas() {
        when(repository.findAll()).thenReturn(java.util.List.of(tarefa));

        var resultado = service.listarTodasTarefas();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void deveBuscarTarefaPorIdComSucesso() {
        when(repository.findById(1L)).thenReturn(Optional.of(tarefa));

        Tarefa resultado = service.buscarTarefaPorId(1L);

        assertNotNull(resultado);
        assertEquals("Estudar Spring Boot", resultado.getNome());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void deveLancarExcecaoAoBuscarTarefaInexistente() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            service.buscarTarefaPorId(99L);
        });
    }
}