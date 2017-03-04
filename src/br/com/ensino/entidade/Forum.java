package br.com.ensino.entidade;

import java.util.Date;
import java.util.Objects;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.ensino.dao.ForumDAO;

@Entity
@Table(name = "forum")
@NamedQueries({
		@NamedQuery(name = "Forum.buscarPorTituloETurma", query = "SELECT f FROM Forum f WHERE f.titulo = :titulo AND f.turma = :turma"),
		@NamedQuery(name = "Forum.listarPorTurma", query = "SELECT f FROM Forum f WHERE f.turma = :turma"),
		@NamedQuery(name = "Forum.buscarPorId", query = "SELECT f FROM Forum f WHERE f.id = :id") })
public class Forum {

	public Forum() {
	}

	public Forum(String titulo, String estado) {
		this.titulo = titulo;
		this.estado = estado;
	}

	public Forum(Date dataCriacao, String titulo, String estado, Turma turma) {
		this.dataCriacao = dataCriacao;
		this.titulo = titulo;
		this.estado = estado;
		this.turma = turma;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_forum")
	private Integer id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, name = "data_criacao")
	private Date dataCriacao;

	@Column(length = 100, nullable = false)
	private String titulo;

	@Column(length = 1, nullable = false)
	private String estado;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_turma_forum", referencedColumnName = "id_turma")
	private Turma turma;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "forum")
	private Set<MensagemForum> mensagens;

	public void addMensagem(MensagemForum m) {
		mensagens.add(m);
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

	public boolean isAberto(){
		return estado.equals("a");
	}
	
	public boolean isFechado(){
		return estado.equals("f");
	}
	
	public void fechar() {
		this.estado = "f";
		ForumDAO.editar(this);
	}

	public void abrir() {
		this.estado = "a";
		ForumDAO.editar(this);
	}

	@Override
	public boolean equals(Object obj) {
		return ((Forum) obj).getId().equals(this.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
}
