package com.gestaotarefas.service.impl;

import com.gestaotarefas.exception.ResourceNotFoundException;
import com.gestaotarefas.model.Tarefa;
import com.gestaotarefas.repository.TarefaRepository;
import com.gestaotarefas.service.TarefaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class TarefaServiceImpl implements TarefaService {
    
    private static final Logger logger = LoggerFactory.getLogger(TarefaServiceImpl.class);
    
    @Autowired
    private TarefaRepository tarefaRepository;
    
    @Override
    public Tarefa criarTarefa(Tarefa tarefa) {
        logger.info("📝 SOLICITAÇÃO: Criar nova tarefa - Nome: {}", tarefa.getNome());
        
        if (tarefaRepository.existsByNome(tarefa.getNome())) {
            logger.error("❌ ERRO: Já existe tarefa com o nome: {}", tarefa.getNome());
            throw new RuntimeException("Já existe uma tarefa com este nome");
        }
        
        Tarefa saved = tarefaRepository.save(tarefa);
        logger.info("✅ SUCESSO: Tarefa criada - ID: {}, Nome: {}, Status: {}", 
                    saved.getId(), saved.getNome(), saved.getStatus());
        return saved;
    }
    
    @Override
    public Tarefa atualizarTarefa(Long id, Tarefa tarefaAtualizada) {
        logger.info("✏️ SOLICITAÇÃO: Atualizar tarefa - ID: {}", id);
        
        Tarefa tarefaExistente = tarefaRepository.findById(id)
            .orElseThrow(() -> {
                logger.error("❌ ERRO: Tarefa não encontrada - ID: {}", id);
                return new ResourceNotFoundException("Tarefa não encontrada com id: " + id);
            });
        
        logger.info("📊 Dados anteriores - Nome: {}, Status: {}", 
                    tarefaExistente.getNome(), tarefaExistente.getStatus());
        
        tarefaExistente.setNome(tarefaAtualizada.getNome());
        tarefaExistente.setDescricao(tarefaAtualizada.getDescricao());
        tarefaExistente.setStatus(tarefaAtualizada.getStatus());
        tarefaExistente.setObservacoes(tarefaAtualizada.getObservacoes());
        
        Tarefa updated = tarefaRepository.save(tarefaExistente);
        logger.info("✅ SUCESSO: Tarefa atualizada - ID: {}, Novo Nome: {}, Novo Status: {}", 
                    updated.getId(), updated.getNome(), updated.getStatus());
        return updated;
    }
    
    @Override
    public void deletarTarefa(Long id) {
        logger.info("🗑️ SOLICITAÇÃO: Deletar tarefa - ID: {}", id);
        
        Tarefa tarefa = tarefaRepository.findById(id)
            .orElseThrow(() -> {
                logger.error("❌ ERRO: Tarefa não encontrada para deletar - ID: {}", id);
                return new ResourceNotFoundException("Tarefa não encontrada com id: " + id);
            });
        
        logger.info("📊 Tarefa a ser deletada - Nome: {}, Status: {}", 
                    tarefa.getNome(), tarefa.getStatus());
        tarefaRepository.delete(tarefa);
        logger.info("✅ SUCESSO: Tarefa deletada - ID: {}, Nome: {}", id, tarefa.getNome());
    }
    
    @Override
    public List<Tarefa> listarTodasTarefas() {
        logger.info("📋 SOLICITAÇÃO: Listar todas as tarefas");
        List<Tarefa> tarefas = tarefaRepository.findAll();
        logger.info("✅ SUCESSO: {} tarefa(s) encontrada(s)", tarefas.size());
        tarefas.forEach(t -> logger.debug("   - ID: {}, Nome: {}, Status: {}", 
                         t.getId(), t.getNome(), t.getStatus()));
        return tarefas;
    }
    
    @Override
    public Tarefa buscarTarefaPorId(Long id) {
        logger.info("🔍 SOLICITAÇÃO: Buscar tarefa por ID: {}", id);
        Tarefa tarefa = tarefaRepository.findById(id)
            .orElseThrow(() -> {
                logger.error("❌ ERRO: Tarefa não encontrada - ID: {}", id);
                return new ResourceNotFoundException("Tarefa não encontrada com id: " + id);
            });
        logger.info("✅ SUCESSO: Tarefa encontrada - Nome: {}, Status: {}", 
                    tarefa.getNome(), tarefa.getStatus());
        return tarefa;
    }
}