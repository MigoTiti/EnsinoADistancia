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
@Table(name = "administrador")
@NamedQueries({
	@NamedQuery(name = "Administrador.listarTodos", query = "SELECT a FROM Administrador a"),
	@NamedQuery(name = "Administrador.buscarPorUsuario", query = "SELECT a FROM Administrador a WHERE a.login.usuario = :usuario"),
})
public class Administrador{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_administrador")
	private Integer id;
	
	@Column(length = 100, nullable = false)
	private String nome;
	
	@Column(nullable = false)
	private Integer idade;
	
	@Column(length = 100, nullable = false, unique = true)
	private String email;
	
	@OneToOne(fetch = FetchType.EAGER, mappedBy = "administrador")
	@Cascade({CascadeType.ALL})
	private Login login; 
	
	@Column(length = 1, nullable = false)
	private String sexo;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "administrador")
	@Cascade({CascadeType.SAVE_UPDATE})
	private Set<MensagemForum> mensagensForum;

	public Administrador(){}
	
	public Administrador(String nome, Integer idade, String email, String usuario, String senha, String sexo) {
		super();
		this.nome = nome;
		this.idade = idade;
		this.email = email;
		this.sexo = sexo;
		this.login = new Login(usuario, senha, this);
		this.login.setAdministrador(this);
	}

	public Administrador(String nome, Integer idade, String email, String usuario, String senha, String sexo,
			Set<MensagemForum> mensagensForum) {
		this.nome = nome;
		this.idade = idade;
		this.email = email;
		this.login = new Login(usuario, senha, this);
		this.sexo = sexo;
		this.mensagensForum = mensagensForum;
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
		return this.login.getUsuario();
	}

	public String getSenha() {
		return this.login.getSenha();
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

	public Set<MensagemForum> getMensagensForum() {
		return mensagensForum;
	}

	public void setMensagensForum(Set<MensagemForum> mensagensForum) {
		this.mensagensForum = mensagensForum;
	}

	public Login getLogin() {
		return login;
	}

	public void setLogin(Login login) {
		this.login = login;
	}
	
}
