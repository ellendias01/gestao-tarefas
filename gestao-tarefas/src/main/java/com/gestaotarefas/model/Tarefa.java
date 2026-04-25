package com.gestaotarefas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

@Entity
@Table(name = "tarefa")
public class Tarefa {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "O nome da tarefa é obrigatório")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
    @Column(nullable = false, length = 100)
    private String nome;
    
    @Column(columnDefinition = "TEXT")
    private String descricao;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusTarefa status = StatusTarefa.PENDENTE;
    
    @Column(columnDefinition = "TEXT")
    private String observacoes;
    
    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDateTime dataCriacao;
    
    @Column(name = "data_atualizacao", nullable = false)
    private LocalDateTime dataAtualizacao;
    
    // Enum de Status
    public enum StatusTarefa {
    PENDENTE("Pendente"),
    EM_ANDAMENTO("Em Andamento"),
    CONCLUIDO("Concluído"),
    CANCELADO("Cancelado");
    
    private String descricao;
    
    StatusTarefa(String descricao) {
        this.descricao = descricao;
    }
    
    @JsonCreator
    public static StatusTarefa fromString(String value) {
    if (value == null) return null;
    
    // Se for número, tenta converter
    try {
        int codigo = Integer.parseInt(value);
        for (StatusTarefa status : StatusTarefa.values()) {
            if (status.ordinal() == codigo) {
                return status;
            }
        }
    } catch (NumberFormatException e) {
        // Não é número, continua com a busca por nome
    }
    
    // Busca por nome ou descrição
    for (StatusTarefa status : StatusTarefa.values()) {
        if (status.name().equalsIgnoreCase(value) || 
            status.getDescricao().equalsIgnoreCase(value)) {
            return status;
        }
    }
    throw new IllegalArgumentException("Status inválido: " + value);
    }
    
    @JsonValue
    public String getDescricao() {
        return descricao;
    }
    }
    
    // Construtores
    public Tarefa() {}
    
    public Tarefa(String nome, String descricao, StatusTarefa status, String observacoes) {
        this.nome = nome;
        this.descricao = descricao;
        this.status = status;
        this.observacoes = observacoes;
    }
    
    @PrePersist
    protected void onCreate() {
        dataCriacao = LocalDateTime.now();
        dataAtualizacao = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        dataAtualizacao = LocalDateTime.now();
    }
    
    // Getters e Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getDescricao() {
        return descricao;
    }
    
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    public StatusTarefa getStatus() {
        return status;
    }
    
    public void setStatus(StatusTarefa status) {
        this.status = status;
    }
    
    public String getObservacoes() {
        return observacoes;
    }
    
    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }
    
    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }
    
    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
    
    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }
    
    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }
}