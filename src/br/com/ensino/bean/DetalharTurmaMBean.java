package br.com.ensino.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.ensino.dao.AlunoDAO;
import br.com.ensino.dao.ProfessorDAO;
import br.com.ensino.dao.TurmaDAO;
import br.com.ensino.entidade.Aluno;
import br.com.ensino.entidade.Professor;
import br.com.ensino.entidade.Turma;
import br.com.ensino.util.MensagensUtil;

@ManagedBean
@ViewScoped
public class DetalharTurmaMBean {

	private Integer idTurma;
	private String nome;
	private Turma turma;

	private String professorEscolhido;
	private List<Professor> professores;
	private List<Aluno> alunosMatriculados;

	private List<Aluno> alunosGeral;
	private List<Aluno> alunosSelecionados;

	public void salvarAlteracoes() {
		if (TurmaDAO.checkNome(nome) || nome.equals(turma.getNome())) {
			turma.setNome(nome);
			turma.setProfessor(ProfessorDAO.buscarPorUsuario(professorEscolhido));

			TurmaDAO.editar(turma);

			MensagensUtil.info("Sucesso", "Turma modificada com sucesso");
		} else {
			MensagensUtil.error("Erro", "Nome já existente no banco de dados");
			nome = turma.getNome();
		}
	}

	public void removerAlunoDeTurma(Aluno a) {
		turma.removerAluno(a);

		TurmaDAO.editar(turma);

		alunosMatriculados.remove(a);
		alunosGeral.add(a);

		MensagensUtil.info("Sucesso", "Aluno removido de turma com sucesso");
	}

	public void matricularAlunos() {
		for (Aluno aluno : alunosSelecionados) {
			turma.addAluno(aluno);
			alunosMatriculados.add(aluno);
			alunosGeral.remove(aluno);
		}

		alunosSelecionados = null;
		TurmaDAO.editar(turma);

		MensagensUtil.info("Sucesso", "Alunos matriculados com sucesso");
	}

	public void cancelarAlunos() {
		alunosGeral = null;
		alunosSelecionados = null;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Turma getTurma() {
		return turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}

	public String getProfessorEscolhido() {
		return professorEscolhido;
	}

	public void setProfessorEscolhido(String professorSelecionado) {
		this.professorEscolhido = professorSelecionado;
	}

	public List<Professor> getProfessores() {
		if (professores == null)
			professores = ProfessorDAO.listar();
		return professores;
	}

	public void setProfessores(List<Professor> professores) {
		this.professores = professores;
	}

	public List<Aluno> getAlunosMatriculados() {
		if (alunosMatriculados == null && turma != null)
			alunosMatriculados = new ArrayList<>(turma.getAlunos());

		return alunosMatriculados;
	}

	public void setAlunosMatriculados(List<Aluno> alunosMatriculados) {
		this.alunosMatriculados = alunosMatriculados;
	}

	public List<Aluno> getAlunosGeral() {
		if (alunosGeral == null && turma != null)
			alunosGeral = AlunoDAO.listarNPertenceTurma(turma);

		return alunosGeral;
	}

	public void setAlunosGeral(List<Aluno> alunosGeral) {
		this.alunosGeral = alunosGeral;
	}

	public List<Aluno> getAlunosSelecionados() {
		return alunosSelecionados;
	}

	public void setAlunosSelecionados(List<Aluno> alunosSelecionados) {
		this.alunosSelecionados = alunosSelecionados;
	}

	public Integer getIdTurma() {
		return idTurma;
	}

	public void setIdTurma(Integer idTurma) {
		if (turma == null){
			turma = TurmaDAO.buscarPorId(idTurma);
			nome = turma.getNome();
		}
		this.idTurma = idTurma;
	}

}
