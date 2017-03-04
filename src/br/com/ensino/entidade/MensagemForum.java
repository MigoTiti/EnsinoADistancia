package br.com.ensino.entidade;

import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "mensagem_forum")
public class MensagemForum {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_mensagem_forum")
	private Integer id;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date hora;
	
	@Column(nullable = false, columnDefinition = "varchar(8000)")
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
	
	public Object getUsuario(){
		if (aluno == null) 
			if (professor == null) 
				if (administrador == null)
					return null;
				else
					return administrador;
			 else 
				return professor;
		 else
			return aluno;
	}
	
	public String getNomeUsuario(){
		if (aluno == null) 
			if (professor == null) 
				if (administrador == null)
					return null;
				else
					return administrador.getUsuario();
			 else 
				return professor.getUsuario();
		 else
			return aluno.getUsuario();
	}

	public MensagemForum(Date hora, String mensagem, Forum forum) {
		this.hora = hora;
		this.mensagem = mensagem;
		this.forum = forum;
	}

	public MensagemForum(String mensagem, Object usuario, Forum f) {
		this.mensagem = mensagem;
		if (usuario instanceof Administrador)
			this.administrador = (Administrador) usuario;
		else if (usuario instanceof Aluno)
			this.aluno = (Aluno) usuario;
		else if (usuario instanceof Professor)
			this.professor = (Professor) usuario;
		
		this.hora = new Date();
		this.forum = f;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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
	
	@Override
	public boolean equals(Object obj) {
		return ((MensagemForum) obj).getId().equals(this.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	public Date getHora() {
		return hora;
	}

	public void setHora(Date hora) {
		this.hora = hora;
	}
}
