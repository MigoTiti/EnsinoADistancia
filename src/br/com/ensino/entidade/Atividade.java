package br.com.ensino.entidade;

import java.text.SimpleDateFormat;
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

@Entity
@Table(name = "atividade")
@NamedQueries({
	@NamedQuery(name = "Atividade.listarPorTurma", query = "SELECT a FROM Atividade a WHERE a.turma = :turma"),
	@NamedQuery(name = "Atividade.buscarPorId", query = "SELECT a FROM Atividade a WHERE a.id = :id")
})
public class Atividade {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_atividade")
	private Integer id;
	
	@Column(length = 100, nullable = false)
	private String titulo;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date prazo;
	
	@Column(nullable = false)
	private Double pontuacao;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_turma_atividade", referencedColumnName = "id_turma")
	private Turma turma;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "atividade")
	private Set<Enunciado> enunciados;
	
	public Atividade() {
	}

	public Atividade(String titulo, Date prazo) {
		this.titulo = titulo;
		this.prazo = prazo;
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

	public Date getPrazo() {
		return prazo;
	}
	
	public String formatarData(){
		SimpleDateFormat s = new SimpleDateFormat("dd/MM/yyyy',' 'às' HH:mm:ss");
		return s.format(prazo);
	}

	public void setPrazo(Date prazo) {
		this.prazo = prazo;
	}

	public Turma getTurma() {
		return turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}

	public Set<Enunciado> getEnunciados() {
		return enunciados;
	}

	public void setEnunciados(Set<Enunciado> enunciados) {
		this.enunciados = enunciados;
	}
	
	@Override
	public boolean equals(Object obj) {
		return ((Atividade) obj).getId().equals(this.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	public Double getPontuacao() {
		return pontuacao;
	}

	public void setPontuacao(Double pontuacao) {
		this.pontuacao = pontuacao;
	}
	
}
