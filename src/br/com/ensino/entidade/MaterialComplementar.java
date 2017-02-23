package br.com.ensino.entidade;

import java.util.Date;

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
@Table(name = "material_complementar")
public class MaterialComplementar {

	public MaterialComplementar() {
	}

	public MaterialComplementar(String titulo, Date dataCriacao) {
		this.titulo = titulo;
		this.dataCriacao = dataCriacao;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_material_complementar")
	private Integer id;
	
	@Column(length = 100, nullable = false)
	private String titulo;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "data_criacao", nullable = false)
	private Date dataCriacao;
	
	@Column(nullable = false)
	private String arquivo;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_turma_material_complementar", referencedColumnName = "id_turma")
	private Turma turma;

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

	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public String getArquivo() {
		return arquivo;
	}

	public void setArquivo(String arquivo) {
		this.arquivo = arquivo;
	}

	public Turma getTurma() {
		return turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}
}
