package br.com.ensino.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ensino.dao.LoginDAO;
import br.com.ensino.entidade.Administrador;
import br.com.ensino.entidade.Aluno;
import br.com.ensino.entidade.Professor;
import br.com.ensino.entidade.Turma;

@ManagedBean
@SessionScoped
public class LoginMBean {

	private List<Turma> turmasCadastradas;
	private Turma turmaSelecionada;
	
	private String usuario;
	private String senha;
	private Aluno alunoLogado;
	private Professor professorLogado;
	private Administrador administradorLogado;
	
	public String login(){
		Object usuarioLogado = LoginDAO.buscarUsuario(usuario, senha);
		
		if (usuarioLogado != null){
			if (usuarioLogado instanceof Aluno){
				alunoLogado = (Aluno) usuarioLogado;
				turmasCadastradas = new ArrayList<>(alunoLogado.getTurmas());
				return "/pages/inicio.xhtml?faces-redirect=true";
			} else if (usuarioLogado instanceof Professor){
				professorLogado = (Professor) usuarioLogado;
				turmasCadastradas = new ArrayList<>(professorLogado.getTurmas());
				return "/pages/inicio.xhtml?faces-redirect=true";
			} else if (usuarioLogado instanceof Administrador){
				administradorLogado = (Administrador) usuarioLogado;
				return "/pages/inicio_admin.xhtml?faces-redirect=true";
			}
		}
	
		return null;
	}
	
	public String redirecionar(Turma t){
		turmaSelecionada = t;
		return "/turma/turma_inicio.xhtml?faces-redirect=true";
	}
	
	public String logout(){
		if (isAluno())
			alunoLogado = null;
		 else if (isAdministrador())
			administradorLogado = null;
		 else if (isProfessor())
			professorLogado = null;
		
		return "/index.xhtml?faces-redirect=true";
	}
	
	public boolean isLogado(){
		if (alunoLogado == null && professorLogado == null && administradorLogado == null)
			return false;
		return true;
	}
	
	public boolean isAluno(){
		if (alunoLogado != null)
			return true;
		return false;
	}
	
	public boolean isNotAdministrador(){
		return administradorLogado == null;
	}

	public boolean isTurmaSelecionada(){
		return turmaSelecionada != null;
	}
	
	public boolean isNotTurmaSelecionada(){
		return turmaSelecionada == null;
	}
	
	public boolean isAdministrador(){
		return administradorLogado != null;
	}
	
	public boolean isProfessor(){
		if (professorLogado != null)
			return true;
		return false;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Aluno getAlunoLogado() {
		return alunoLogado;
	}

	public void setAlunoLogado(Aluno alunoLogado) {
		this.alunoLogado = alunoLogado;
	}

	public Professor getProfessorLogado() {
		return professorLogado;
	}

	public void setProfessorLogado(Professor professorLogado) {
		this.professorLogado = professorLogado;
	}

	public Administrador getAdministradorLogado() {
		return administradorLogado;
	}

	public void setAdministradorLogado(Administrador administradorLogado) {
		this.administradorLogado = administradorLogado;
	}
	
	public List<Turma> getTurmasCadastradas() {
		return turmasCadastradas;
	}

	public void setTurmasCadastradas(List<Turma> turmasCadastradas) {
		this.turmasCadastradas = turmasCadastradas;
	}
	
	public Turma getTurmaSelecionada() {
		return turmaSelecionada;
	}

	public void setTurmaSelecionada(Turma turmaSelecionada) {
		this.turmaSelecionada = turmaSelecionada;
	}
	
}
