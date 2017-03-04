package br.com.ensino.entidade;

import java.util.Objects;

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
import javax.persistence.Table;

@Entity
@Table(name = "resposta")
@NamedQueries({
	@NamedQuery(name = "Resposta.buscarPorAlunoEEnunciado", query = "SELECT r FROM Resposta r WHERE r.aluno = :aluno AND "
			+ "r.enunciado = :enunciado")
})
public class Resposta {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_resposta")
	private Integer id;
	
	@Column(nullable = false, length = 30000)
	private String texto;
	
	@Column(length = 30000)
	private String correcao;
	
	@Column
	private Double pontuacao;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_enunciado_resposta", referencedColumnName = "id_enunciado")
	private Enunciado enunciado;
	
	@ManyToOne
	@JoinColumn(name = "id_aluno_resposta", referencedColumnName = "id_aluno")
	private Aluno aluno;
	
	public Resposta() {
	}

	public Resposta(String texto, Enunciado enunciado, Aluno aluno) {
		this.texto = texto;
		this.enunciado = enunciado;
		this.aluno = aluno;
	}

	@Override
	public boolean equals(Object obj) {
		return ((Resposta) obj).getId().equals(this.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public Double getPontuacao() {
		return pontuacao;
	}

	public void setPontuacao(Double pontuacao) {
		this.pontuacao = pontuacao;
	}

	public Enunciado getEnunciado() {
		return enunciado;
	}

	public void setEnunciado(Enunciado enunciado) {
		this.enunciado = enunciado;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public String getCorrecao() {
		return correcao;
	}

	public void setCorrecao(String correcao) {
		this.correcao = correcao;
	}
	
	
}
