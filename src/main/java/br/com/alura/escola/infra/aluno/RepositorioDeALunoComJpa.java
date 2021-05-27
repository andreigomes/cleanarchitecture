package br.com.alura.escola.infra.aluno;

import br.com.alura.escola.dominio.aluno.Aluno;
import br.com.alura.escola.dominio.aluno.CPF;
import br.com.alura.escola.dominio.aluno.RepositorioDeAlunos;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
public class RepositorioDeALunoComJpa  implements RepositorioDeAlunos {

    @Autowired
    private  AlunoRepository alunoRepository;

    @Override
    public void matricular(Aluno aluno) {
        alunoRepository.save(aluno);
    }

    @Override
    public Aluno buscarPorCPF(CPF cpf) {
        return alunoRepository.findByCpfNumero(cpf.getNumero());
    }

    @Override
    public List<Aluno> listarTodosALunosMatriculados() {
        return alunoRepository.findAll();
    }
}
