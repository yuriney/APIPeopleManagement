package com.example.demo.Controller;

import com.example.demo.Entity.Tarefa;
import com.example.demo.Service.TarefaService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tarefas")
public class TarefaController {
    @Autowired
    TarefaService tarefaService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Tarefa salvarTarefa(@RequestBody Tarefa tarefa){
        return tarefaService.salvarTarefa(tarefa);
    }

    @PutMapping("/alocar/{idTarefa}")
    public ResponseEntity<Tarefa> alocarPessoaNaTarefa(
            @PathVariable UUID idTarefa,
            @RequestParam UUID idPessoa) {
        try {
            Tarefa tarefaAtualizada = tarefaService.alocarPessoa(idTarefa, idPessoa);
            return ResponseEntity.ok(tarefaAtualizada);
        } catch (EntityNotFoundException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/tarefas/finalizar/{idTarefa}")
    public Tarefa finalizarTarefa(@PathVariable UUID id_tarefa){
        return tarefaService.finalizarTarefa(id_tarefa);
    }

    @GetMapping("/pendentes")
    public List<Tarefa> listarPendentes() {
        return tarefaService.listarTarefasPendentes();
    }
}
