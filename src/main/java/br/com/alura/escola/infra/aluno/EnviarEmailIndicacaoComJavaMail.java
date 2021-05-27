package br.com.alura.escola.infra.aluno;

import br.com.alura.escola.dominio.indicacao.EnviarEmailIndicacao;
import br.com.alura.escola.dominio.aluno.Aluno;

public class EnviarEmailIndicacaoComJavaMail implements EnviarEmailIndicacao {
    @Override
    public void enviarPara(Aluno indicado) {
        //implementar envio de email
    }
}
