package br.com.ensino.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Table(name = "login")
@NamedQueries({
	@NamedQuery(name = "Login.buscarPorUsuarioESenha", query = "SELECT l FROM Login l WHERE l.usuario = :usuario AND l.senha = :senha")
})
public class Login {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_login")
	private Integer idLogin;
	
	@Column(nullable = false, unique = true)
	private String usuario;
	
	@Column(nullable = false)
	private String senha;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_administrador")
	@Cascade({CascadeType.ALL})
	private Administrador administrador;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_aluno")
	@Cascade({CascadeType.ALL})
	private Aluno aluno;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_professor")
	@Cascade({CascadeType.ALL})
	private Professor professor;
	
	public Login(String usuario, String senha) {
		this.usuario = usuario;
		this.senha = senha;
	}
	
	public Login() {
	}

	public Login(String usuario, String senha, Professor professor) {
		this.usuario = usuario;
		this.senha = senha;
		this.professor = professor;
	}

	public Login(String usuario, String senha, Aluno aluno) {
		this.usuario = usuario;
		this.senha = senha;
		this.aluno = aluno;
	}

	public Login(String usuario, String senha, Administrador administrador) {
		this.usuario = usuario;
		this.senha = senha;
		this.administrador = administrador;
	}

	public Integer getIdLogin() {
		return idLogin;
	}

	public void setIdLogin(Integer idLogin) {
		this.idLogin = idLogin;
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

	public Administrador getAdministrador() {
		return administrador;
	}

	public void setAdministrador(Administrador administrador) {
		this.administrador = administrador;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}
	
}
