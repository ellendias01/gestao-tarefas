-- Criação da tabela tarefa
CREATE TABLE IF NOT EXISTS tarefa (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao TEXT,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDENTE',
    observacoes TEXT,
    data_criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Índice para busca por status
CREATE INDEX idx_tarefa_status ON tarefa(status);

-- Comentários
COMMENT ON TABLE tarefa IS 'Tabela para gerenciamento de tarefas';
COMMENT ON COLUMN tarefa.status IS 'Valores possíveis: PENDENTE, EM_ANDAMENTO, CONCLUIDO, CANCELADO';