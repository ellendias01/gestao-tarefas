package com.gestaotarefas.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.gestaotarefas.model.Tarefa;
import com.gestaotarefas.model.Tarefa.StatusTarefa;
import com.gestaotarefas.repository.TarefaRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Transactional
public class TarefaControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private TarefaRepository tarefaRepository;

    private Tarefa tarefa;

    @BeforeEach
    void setup() {
        tarefaRepository.deleteAll();
        
        tarefa = new Tarefa();
        tarefa.setNome("Tarefa Teste " + System.currentTimeMillis());
        tarefa.setDescricao("Descrição da tarefa teste");
        tarefa.setStatus(StatusTarefa.PENDENTE);
    }

    @Test
    void deveCriarTarefaViaAPI() {
        ResponseEntity<Tarefa> response = restTemplate.postForEntity(
                "/tarefas", tarefa, Tarefa.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(tarefa.getNome(), response.getBody().getNome());
        assertEquals(StatusTarefa.PENDENTE, response.getBody().getStatus());
        assertNotNull(response.getBody().getId());
    }

    @Test
    void deveListarTodasTarefasViaAPI() {
        restTemplate.postForEntity("/tarefas", tarefa, Tarefa.class);

        ResponseEntity<Tarefa[]> response = restTemplate.getForEntity(
                "/tarefas", Tarefa[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().length > 0);
    }

    @Test
    void deveBuscarTarefaPorIdViaAPI() {
        ResponseEntity<Tarefa> created = restTemplate.postForEntity(
                "/tarefas", tarefa, Tarefa.class);
        Long id = created.getBody().getId();

        ResponseEntity<Tarefa> response = restTemplate.getForEntity(
                "/tarefas/" + id, Tarefa.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(id, response.getBody().getId());
    }

    @Test
    void deveAtualizarTarefaViaAPI() {
        ResponseEntity<Tarefa> created = restTemplate.postForEntity(
                "/tarefas", tarefa, Tarefa.class);
        Long id = created.getBody().getId();

        Tarefa tarefaAtualizada = new Tarefa();
        tarefaAtualizada.setNome("Nome Atualizado");
        tarefaAtualizada.setDescricao("Descrição atualizada");
        tarefaAtualizada.setStatus(StatusTarefa.CONCLUIDO);

        HttpEntity<Tarefa> requestEntity = new HttpEntity<>(tarefaAtualizada);
        ResponseEntity<Tarefa> response = restTemplate.exchange(
                "/tarefas/" + id, 
                HttpMethod.PUT, 
                requestEntity, 
                Tarefa.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Nome Atualizado", response.getBody().getNome());
        assertEquals(StatusTarefa.CONCLUIDO, response.getBody().getStatus());
    }

    @Test
    void deveDeletarTarefaViaAPI() {
        ResponseEntity<Tarefa> created = restTemplate.postForEntity(
                "/tarefas", tarefa, Tarefa.class);
        Long id = created.getBody().getId();

        // Deleta a tarefa
        restTemplate.delete("/tarefas/" + id);

        // ✅ Agora verifica o status HTTP diretamente (sem esperar exceção)
        ResponseEntity<Tarefa> findResponse = restTemplate.getForEntity(
                "/tarefas/" + id, Tarefa.class);
        
        assertEquals(HttpStatus.NOT_FOUND, findResponse.getStatusCode());
        assertNull(findResponse.getBody());
    }

    @Test
    void deveRetornar404QuandoTarefaNaoEncontrada() {
        // ✅ Verifica diretamente o status HTTP
        ResponseEntity<Tarefa> response = restTemplate.getForEntity(
                "/tarefas/99999", Tarefa.class);
        
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }
}