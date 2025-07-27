package com.example.demo.Service;

import com.example.demo.Entity.Pessoa;
import com.example.demo.Entity.Tarefa;
import com.example.demo.Repository.PessoaRepository;
import com.example.demo.dto.PessoaListaDTO;
import com.example.demo.dto.PessoaMediaHorasDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PessoaService {
    @Autowired
    PessoaRepository pessoaRepository;

    public Pessoa salvarPessoa(Pessoa pessoa){
        return pessoaRepository.save(pessoa);
    }

    public Pessoa alterarPessoa(UUID id, Pessoa pessoaAlterada){
        Optional<Pessoa> pessoaOptional = pessoaRepository.findById(id);
        if(pessoaOptional.isPresent()){
            Pessoa p = pessoaOptional.get();
            p.setDepartamento(pessoaAlterada.getDepartamento());
            p.setNome(pessoaAlterada.getNome());
            p.setTarefas(pessoaAlterada.getTarefas());
            return pessoaRepository.save(p);
        }else{
            throw new EntityNotFoundException("Pessoa não encontrada: " + id);
        }
    }
    public void deletarPessoaPorId(UUID id){
        pessoaRepository.deleteById(id);
    }

    public List<PessoaListaDTO> listarPessoas(){
        List<Pessoa> listaPesoas = pessoaRepository.findAll();
        List<PessoaListaDTO> listaDTOS = new ArrayList<>();
        String tempoTotalFormatado;
        for(Pessoa pessoa : listaPesoas){
            Duration tempoTotal = Duration.ZERO;

            for(Tarefa tarefa: pessoa.getTarefas()){
                if(tarefa.getDuracao()!=null){
                    tempoTotal = tempoTotal.plus(tarefa.getDuracao());
                }

            }
            tempoTotalFormatado = formatarDuracao(tempoTotal);
            listaDTOS.add(new PessoaListaDTO(pessoa.getNome(), pessoa.getDepartamento(), tempoTotalFormatado));
        }
        return listaDTOS;
    }

    private String formatarDuracao(Duration duracao) {
        long horas = duracao.toHours();
        long minutos = duracao.minusHours(horas).toMinutes();
        return horas + "h " + minutos + "min";
    }

    public List<PessoaMediaHorasDTO> buscarMediaHoras(String nome, LocalDate dataInicio, LocalDate dataFim) {
        List<Pessoa> pessoas = pessoaRepository.findByNomeContainingIgnoreCase(nome);

        List<PessoaMediaHorasDTO> resultado = new ArrayList<>();

        for (Pessoa p : pessoas) {
            List<Tarefa> tarefasFiltradas = p.getTarefas().stream()
                    .filter(t -> !t.getPrazo().toLocalDate().isBefore(dataInicio) &&
                            !t.getPrazo().toLocalDate().isAfter(dataFim) &&
                            t.isFinalizada())
                    .toList();

            double media = tarefasFiltradas.stream()
                    .map(Tarefa::getDuracao)
                    .mapToDouble(d -> d.toHours())
                    .average()
                    .orElse(0.0);

            resultado.add(new PessoaMediaHorasDTO(p.getNome(), media));
        }

        return resultado;
    }

}
