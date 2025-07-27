package com.example.demo.Controller;

import com.example.demo.Entity.Pessoa;
import com.example.demo.Service.PessoaService;
import com.example.demo.dto.PessoaListaDTO;
import com.example.demo.dto.PessoaMediaHorasDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {
    @Autowired
    PessoaService pessoaService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Pessoa salvarPessoa (@RequestBody Pessoa pessoa){
        return pessoaService.salvarPessoa(pessoa);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Pessoa alterarPessoa(@PathVariable UUID id_pessoa, @RequestBody Pessoa pessoaAlterada){
        return pessoaService.alterarPessoa(id_pessoa, pessoaAlterada);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deletarPessoaPorId(@PathVariable UUID id){
        pessoaService.deletarPessoaPorId(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PessoaListaDTO> listarPessoas(){
        return pessoaService.listarPessoas();
    }

    @GetMapping("/pessoas/gastos")
    public List<PessoaMediaHorasDTO> buscarGastos(
            @RequestParam String nome,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim) {
        return pessoaService.buscarMediaHoras(nome, dataInicio, dataFim);
    }
}
