package com.example.demo.Repository;

import com.example.demo.Entity.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, UUID> {
    List<Tarefa> findTop3ByPessoaAlocadaIsNullOrderByPrazoAsc();

    long countByDepartamento(String departamento);
}
