package com.gestaotarefas.service;

import com.gestaotarefas.model.Tarefa;
import java.util.List;

public interface TarefaService {
    Tarefa criarTarefa(Tarefa tarefa);
    Tarefa atualizarTarefa(Long id, Tarefa tarefa);
    void deletarTarefa(Long id);
    List<Tarefa> listarTodasTarefas();
    Tarefa buscarTarefaPorId(Long id);
}