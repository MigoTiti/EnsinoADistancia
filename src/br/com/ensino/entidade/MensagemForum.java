package br.com.ensino.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "mensagem_forum")
public class MensagemForum {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_mensagem_forum")
	private Integer id;
	
	@Column
	private String titulo;
	
	@Column(nullable = false)
	private String mensagem;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_forum_mensagem_forum", referencedColumnName = "id_forum")
	private Forum forum;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_aluno_mensagem_forum", referencedColumnName = "id_aluno")
	private Aluno aluno;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_professor_mensagem_forum", referencedColumnName = "id_professor")
	private Professor professor;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_administrador_forum", referencedColumnName = "id_administrador")
	private Administrador administrador;
	
	public MensagemForum() {
	}

	public MensagemForum(String titulo, String mensagem, Administrador administrador) {
		this.titulo = titulo;
		this.mensagem = mensagem;
		this.administrador = administrador;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public Forum getForum() {
		return forum;
	}

	public void setForum(Forum forum) {
		this.forum = forum;
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

	public Administrador getAdministrador() {
		return administrador;
	}

	public void setAdministrador(Administrador administrador) {
		this.administrador = administrador;
	}
	
	
}
