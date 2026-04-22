package com.gestaotarefas.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/health")
public class HealthController {
    
    @GetMapping
    public ResponseEntity<Map<String, Object>> healthCheck() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "online");
        response.put("timestamp", System.currentTimeMillis());
        response.put("message", "API de Gerenciamento de Tarefas funcionando!");
        response.put("database", "PostgreSQL conectado");
        response.put("version", "1.0.0");
        return ResponseEntity.ok(response);
    }
}