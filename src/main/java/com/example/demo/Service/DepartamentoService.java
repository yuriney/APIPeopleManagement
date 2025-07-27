package com.example.demo.Service;

import com.example.demo.Repository.PessoaRepository;
import com.example.demo.Repository.TarefaRepository;
import com.example.demo.dto.DepartamentoInfoDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class DepartamentoService {

    private final PessoaRepository pessoaRepository;
    private final TarefaRepository tarefaRepository;

    public DepartamentoService(PessoaRepository pessoaRepository, TarefaRepository tarefaRepository) {
        this.pessoaRepository = pessoaRepository;
        this.tarefaRepository = tarefaRepository;
    }

    public List<DepartamentoInfoDTO> listarDepartamentosComTotais() {
        List<String> departamentos = Arrays.asList("TI", "Financeiro", "RH"); // pensei em uma abordagem com ENUM tmb

        List<DepartamentoInfoDTO> lista = new ArrayList<>();

        for (String dep : departamentos) {
            long totalPessoas = pessoaRepository.countByDepartamento(dep);
            long totalTarefas = tarefaRepository.countByDepartamento(dep);
            lista.add(new DepartamentoInfoDTO(dep, totalPessoas, totalTarefas));
        }

        return lista;
    }
}