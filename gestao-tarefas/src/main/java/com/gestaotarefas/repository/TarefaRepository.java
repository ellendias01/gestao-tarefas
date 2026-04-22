package com.gestaotarefas.repository;

import com.gestaotarefas.model.Tarefa;
import com.gestaotarefas.model.Tarefa.StatusTarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
    List<Tarefa> findByStatus(StatusTarefa status);
    boolean existsByNome(String nome);
}