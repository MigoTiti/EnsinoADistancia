package br.com.ensino.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.ensino.dao.TurmaDAO;
import br.com.ensino.entidade.Aluno;
import br.com.ensino.entidade.Turma;

@ManagedBean
@ViewScoped
public class PesquisarTurmaMBean {

	private List<Turma> turmas;
	private List<Turma> turmasFiltradas;
	
	private List<Aluno> alunosFiltrados;
	
	private Turma turmaAtual;

	@PostConstruct
	private void init(){
		turmas = TurmaDAO.listar();
	}
	
	public List<Turma> getTurmas() {
		return turmas;
	}

	public void setTurmas(List<Turma> turmas) {
		this.turmas = turmas;
	}

	public List<Turma> getTurmasFiltradas() {
		return turmasFiltradas;
	}

	public void setTurmasFiltradas(List<Turma> turmasFiltradas) {
		this.turmasFiltradas = turmasFiltradas;
	}

	public Turma getTurmaAtual() {
		return turmaAtual;
	}

	public void setTurmaAtual(Turma turmaAtual) {
		this.turmaAtual = turmaAtual;
	}

	public List<Aluno> getAlunosFiltrados() {
		return alunosFiltrados;
	}

	public void setAlunosFiltrados(List<Aluno> alunosFiltrados) {
		this.alunosFiltrados = alunosFiltrados;
	}
}
