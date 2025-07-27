package com.example.demo.Service;

import com.example.demo.Entity.Pessoa;
import com.example.demo.Entity.Tarefa;
import com.example.demo.Repository.PessoaRepository;
import com.example.demo.Repository.TarefaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TarefaService {
    @Autowired
    TarefaRepository tarefaRepository;
    @Autowired
    PessoaRepository pessoaRepository;

    public Tarefa salvarTarefa(Tarefa tarefa){
        return tarefaRepository.save(tarefa);
    }

    public Tarefa alocarPessoa(UUID id_tarefa, UUID id_pessoa){
        Tarefa tarefa = tarefaRepository.findById(id_tarefa)
                .orElseThrow(() -> new EntityNotFoundException("Tarefa não encontrada: " + id_tarefa));

        Pessoa pessoa = pessoaRepository.findById(id_pessoa)
                .orElseThrow(() -> new EntityNotFoundException("Pessoa não encontrada: " + id_pessoa));

        if(!tarefa.getDepartamento().equalsIgnoreCase(pessoa.getDepartamento())){
            throw new IllegalArgumentException("Departamentos não coincidem!");
        }
        tarefa.setPessoaAlocada(pessoa);
        return tarefaRepository.save(tarefa);
    }

    public Tarefa finalizarTarefa(UUID id_tarefa){
        Optional<Tarefa> tarefaOptional = tarefaRepository.findById(id_tarefa);
        if(tarefaOptional.isPresent()){
            Tarefa t = tarefaOptional.get();
            t.setFinalizada(true);
            return tarefaRepository.save(t);
        }
        else{
            throw new EntityNotFoundException("Tarefa não encontrada! " + id_tarefa);
        }
    }

    public List<Tarefa> listarTarefasPendentes() {
        return tarefaRepository.findTop3ByPessoaAlocadaIsNullOrderByPrazoAsc();
    }
}
