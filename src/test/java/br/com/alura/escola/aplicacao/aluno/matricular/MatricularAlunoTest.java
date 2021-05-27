package br.com.alura.escola.aplicacao.aluno.matricular;

import br.com.alura.escola.dominio.aluno.Aluno;
import br.com.alura.escola.dominio.aluno.CPF;
import static org.junit.jupiter.api.Assertions.*;
import br.com.alura.escola.infra.aluno.RepositorioDeAlunosEmMemoria;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MatricularAlunoTest {


    @Test
    void alunoDeveSerPersistido() {
        RepositorioDeAlunosEmMemoria repositorioDeAlunosEmMemoria = new RepositorioDeAlunosEmMemoria();
        MatricularAluno useCase = new MatricularAluno(repositorioDeAlunosEmMemoria);

        MatricularAlunoDto dados = new MatricularAlunoDto(
                "andrei",
                "408.069.588-80",
                "andrei@hotmail.com");
        useCase.executa(dados);

        Aluno alunoEncontrado = repositorioDeAlunosEmMemoria.buscarPorCPF(new CPF("408.069.588-80"));
        assertEquals("andrei", alunoEncontrado.getNome());
        assertEquals("408.069.588-80", alunoEncontrado.getCpf());
        assertEquals("andrei@hotmail.com", alunoEncontrado.getEmail());
    }
}
