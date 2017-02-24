package br.com.ensino.entidade;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Table(name = "professor")
@NamedQueries({
	@NamedQuery(name = "Professor.listarTodos", query = "SELECT p FROM Professor p"),
	@NamedQuery(name = "Professor.buscarPorUsuario", query = "SELECT p FROM Professor p WHERE p.login.usuario = :usuario"),
})
public class Professor{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_professor")
	private Integer id;
	
	@Column(length = 100, nullable = false)
	private String nome;
	
	@Column(nullable = false)
	private Integer idade;
	
	@Column(length = 100, nullable = false, unique = true)
	private String email;
	
	@OneToOne(fetch = FetchType.EAGER, mappedBy = "professor")
	@Cascade({CascadeType.ALL})
	private Login login;
	
	@Column(length = 1, nullable = false)
	private String sexo;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "professor")
	private Set<Turma> turmas;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "professor")
	private Set<MensagemForum> mensagensForum;
	
	public Professor() {}

	public Professor(String nome, Integer idade, String email, String usuario, String senha, String sexo) {
		this.nome = nome;
		this.idade = idade;
		this.email = email;
		this.login = new Login(usuario, senha, this);
		this.sexo = sexo;
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

	public Login getLogin() {
		return login;
	}

	public void setLogin(Login login) {
		this.login = login;
	}

	public Set<Turma> getTurmas() {
		return turmas;
	}

	public void setTurmas(Set<Turma> turmas) {
		this.turmas = turmas;
	}

	public Set<MensagemForum> getMensagensForum() {
		return mensagensForum;
	}

	public void setMensagensForum(Set<MensagemForum> mensagensForum) {
		this.mensagensForum = mensagensForum;
	}
	
}
