package br.com.ensino.entidade;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Table(name = "aluno")
@NamedQueries({
	@NamedQuery(name = "Aluno.listarTodos", query = "SELECT a FROM Aluno a"),
	@NamedQuery(name = "Aluno.buscarPorUsuario", query = "SELECT a FROM Aluno a WHERE a.login.usuario = :usuario")
})
public class Aluno{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_aluno")
	private Integer id;
	
	@Column(length = 100, nullable = false)
	private String nome;
	
	@Column(nullable = false)
	private Integer idade;
	
	@Column(length = 100, nullable = false, unique = true)
	private String email;

	@OneToOne(fetch = FetchType.EAGER, mappedBy = "aluno")
	@Cascade({CascadeType.ALL})
	private Login login;
	
	@Column(length = 1, nullable = false)
	private String sexo;
	
	@ManyToMany(fetch = FetchType.EAGER, mappedBy = "alunos")
	private Set<Turma> turmas;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "aluno")
	private Set<Resposta> respostas;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "aluno")
	private Set<MensagemForum> mensagensForum;

	public Aluno() {}

	public Aluno(String nome, Integer idade, String email, String usuario, String senha, String sexo) {
		this.nome = nome;
		this.idade = idade;
		this.email = email;
		this.login = new Login(usuario, senha, this);
		this.sexo = sexo;
	}
	
	public Login getLogin() {
		return login;
	}

	public void setLogin(Login login) {
		this.login = login;
	}
	public Integer getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public Integer getIdade() {
		return idade;
	}

	public String getEmail() {
		return email;
	}

	public String getUsuario() {
		return login.getUsuario();
	}

	public String getSenha() {
		return login.getSenha();
	}

	public String getSexo() {
		return sexo;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setIdade(Integer idade) {
		this.idade = idade;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setUsuario(String usuario) {
		this.login.setUsuario(usuario);
	}

	public void setSenha(String senha) {
		this.login.setSenha(senha);
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public Set<Turma> getTurmas() {
		return turmas;
	}

	public void setTurmas(Set<Turma> turmas) {
		this.turmas = turmas;
	}

	public Set<Resposta> getRespostas() {
		return respostas;
	}

	public void setRespostas(Set<Resposta> respostas) {
		this.respostas = respostas;
	}

	public Set<MensagemForum> getMensagensForum() {
		return mensagensForum;
	}

	public void setMensagensForum(Set<MensagemForum> mensagensForum) {
		this.mensagensForum = mensagensForum;
	}
	
	
}
