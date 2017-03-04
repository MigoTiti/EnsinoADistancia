package br.com.ensino.entidade;

import java.util.ArrayList;
import java.util.List;
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

@Entity
@Table(name = "enunciado")
@NamedQueries({
	@NamedQuery(name = "Enunciado.buscarPorAtividadeETexto", query = "SELECT e FROM Enunciado e WHERE e.atividade = :atividade AND "
			+ "e.texto = :texto"),
	@NamedQuery(name = "Enunciado.listarPorAtividade", query = "SELECT e FROM Enunciado e WHERE e.atividade = :atividade")
})
public class Enunciado {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_enunciado")
	private Integer id;
	
	@Column(nullable = false, length = 30000)
	private String texto;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_atividade_enunciado", referencedColumnName = "id_atividade")
	private Atividade atividade;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "enunciado")
	private Set<Resposta> respostas;
	
	public Enunciado() {
	}

	public Enunciado(String texto) {
		this.texto = texto;
	}
	
	public List<Resposta> getRespostasList(){
		return new ArrayList<>(respostas);
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

	public Atividade getAtividade() {
		return atividade;
	}

	public void setAtividade(Atividade atividade) {
		this.atividade = atividade;
	}

	public Set<Resposta> getResposta() {
		return respostas;
	}

	public void setResposta(Set<Resposta> resposta) {
		this.respostas = resposta;
	}
	
	@Override
	public boolean equals(Object obj) {
		return ((Enunciado) obj).getId().equals(this.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
