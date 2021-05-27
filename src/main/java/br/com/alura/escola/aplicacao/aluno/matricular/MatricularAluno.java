package br.com.alura.escola.aplicacao.aluno.matricular;

import br.com.alura.escola.dominio.aluno.Aluno;
import br.com.alura.escola.dominio.aluno.RepositorioDeAlunos;

public class MatricularAluno {

    private final RepositorioDeAlunos repositorioDeAlunos;

    public MatricularAluno(RepositorioDeAlunos repositorioDeAlunos) {
        this.repositorioDeAlunos = repositorioDeAlunos;
    }

    //PADRAO PROJETO COMMAND
    public void executa(MatricularAlunoDto matricularAlunoDto) {
        Aluno aluno = matricularAlunoDto.criarAluno();
        repositorioDeAlunos.matricular(aluno);
    }
}
