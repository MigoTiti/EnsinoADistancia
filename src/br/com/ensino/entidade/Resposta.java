package br.com.ensino.entidade;

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
	@Column(name = "id_enunciado")
	private Integer id;
	
	@Column(nullable = false)
	private String texto;
	
	@Column
	private Float pontuacao;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_enunciado_resposta", referencedColumnName = "id_enunciado")
	private Enunciado enunciado;
	
	@ManyToOne
	@JoinColumn(name = "id_aluno_resposta", referencedColumnName = "id_aluno")
	private Aluno aluno;
}
