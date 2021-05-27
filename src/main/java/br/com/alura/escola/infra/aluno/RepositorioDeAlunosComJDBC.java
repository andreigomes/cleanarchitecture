package br.com.alura.escola.infra.aluno;

import br.com.alura.escola.dominio.aluno.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RepositorioDeAlunosComJDBC implements RepositorioDeAlunos {

    private Connection connection;

    public RepositorioDeAlunosComJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void matricular(Aluno aluno) {
        try {
            String sql = "INSERT INTO ALUNOS VALUES(?,?,?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, aluno.getCpf());
            ps.setString(2, aluno.getNome());
            ps.setString(3, aluno.getEmail());
            ps.execute();

            sql = "INSERT INTO TELEFONE VALUES(?,?)";
            ps = connection.prepareStatement(sql);
            PreparedStatement finalPs = ps;
            aluno.getTelefones().forEach(telefone -> {
                try {
                    finalPs.setString(1, telefone.getDdd());
                    finalPs.setString(2, telefone.getNumero());
                    finalPs.execute();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            });
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public Aluno buscarPorCPF(CPF cpf) {
        try {
            String sql = "SELEC ID, NOME, EMAIL FROM ALUNO WHERE CPF = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, cpf.getNumero());

            ResultSet rs = ps.executeQuery();
            boolean encontrou = rs.next();
            if (!encontrou) {
                throw new AlunoNaoEncontrado(cpf);
            }

            String nome = rs.getString("nome");
            Email email = new Email(rs.getString("email"));
            Aluno encontrado = new Aluno(cpf, nome, email);

            Long id = rs.getLong("id");
            sql = "SELECT DDD, NUMERO FROM TELEFONE WHERE ALUNO_ID = ?";
            PreparedStatement psTelefone = connection.prepareStatement(sql);
            psTelefone.setLong(1, id);
            ResultSet rsTelefone = psTelefone.executeQuery();
            while (rsTelefone.next()) {
                String numero = rsTelefone.getString("numero");
                String ddd = rsTelefone.getString("ddd");
                encontrado.adicionarTelefone(ddd, numero);
            }
            return encontrado;

        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public List<Aluno> listarTodosALunosMatriculados() {
        try {
            String sql = "SELEC ID, NOME, EMAIL FROM ALUNO";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            List<Aluno> alunos = new ArrayList<>();
            while (rs.next()) {
                CPF cpf = new CPF(rs.getString("cpf"));
                String nome = rs.getString("nome");
                Email email = new Email(rs.getString("email"));
                Aluno aluno = new Aluno(cpf, nome, email);

                Long id = rs.getLong("id");
                sql = "SELECT DDD, NUMERO FROM TELEFONE WHERE ALUNO_ID = ?";
                PreparedStatement psTelefone = connection.prepareStatement(sql);
                psTelefone.setLong(1, id);
                ResultSet rsTelefone = psTelefone.executeQuery();
                while (rsTelefone.next()) {
                    String numero = rsTelefone.getString("numero");
                    String ddd = rsTelefone.getString("ddd");
                    aluno.adicionarTelefone(ddd, numero);
                }
                alunos.add(aluno);
            }
            return alunos;
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }
}