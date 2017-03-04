package br.com.ensino.entidade;

import java.sql.Blob;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "material_complementar")
public class MaterialComplementar {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_material_complementar")
	private Integer id;
	
	@Column(length = 100, nullable = false)
	private String titulo;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_criacao", nullable = false)
	private Date dataCriacao;
	
	@Column(nullable = false)
	@Lob
	private Blob arquivo;
	
	@Column(nullable = false, name = "nome_arquivo")
	private String nomeArquivo;
	
	@Column(nullable = false)
	private String tipo;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_turma_material_complementar", referencedColumnName = "id_turma")
	private Turma turma;

	public MaterialComplementar() {
		this.dataCriacao = new Date();
	}

	public MaterialComplementar(String titulo) {
		this.titulo = titulo;
		this.dataCriacao = new Date();
	}
	
	public MaterialComplementar(Blob arquivo, String nomeArquivo, String tipo) {
		this.arquivo = arquivo;
		this.nomeArquivo = nomeArquivo;
		this.tipo = tipo;
		this.dataCriacao = new Date();
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

	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public Blob getArquivo() {
		return arquivo;
	}

	public void setArquivo(Blob arquivo) {
		this.arquivo = arquivo;
	}

	public Turma getTurma() {
		return turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}
	
	@Override
	public boolean equals(Object obj) {
		return ((MaterialComplementar) obj).getId().equals(this.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	public String getNomeArquivo() {
		return nomeArquivo;
	}

	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
}
