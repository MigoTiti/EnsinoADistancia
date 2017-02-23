package br.com.ensino.entidade;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "forum")
@NamedQueries({
	@NamedQuery(name = "Forum.buscarPorTituloETurma", query = "SELECT f FROM Forum f WHERE f.titulo = :titulo AND f.turma = :turma"),
	@NamedQuery(name = "Forum.listarPorTurma", query = "SELECT f FROM Forum f WHERE f.turma = :turma")
})
public class Forum {
	
	public Forum() {
	}

	public Forum(String titulo, String estado) {
		this.titulo = titulo;
		this.estado = estado;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_forum")
	private Integer id;
	
	@Column(length = 100, nullable = false)
	private String titulo;
	
	@Column(length = 1, nullable = false)
	private String estado;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_turma_forum", referencedColumnName = "id_turma")
	private Turma turma;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "forum")
	private Set<MensagemForum> mensagens;

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

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Turma getTurma() {
		return turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}

	public Set<MensagemForum> getMensagens() {
		return mensagens;
	}

	public void setMensagens(Set<MensagemForum> mensagens) {
		this.mensagens = mensagens;
	}
}
