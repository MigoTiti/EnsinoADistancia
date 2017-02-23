package br.com.ensino.entidade;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "turma")
@NamedQueries({
	@NamedQuery(name = "Turma.listarTodos", query = "SELECT t FROM Turma t"),
	@NamedQuery(name = "Turma.buscarPorNome", query = "SELECT t FROM Turma t WHERE t.nome = :nome")
})
public class Turma {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_turma")
	private Integer id;
	
	@Column(length = 100, nullable = false, unique = true)
	private String nome;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_professor_turma", referencedColumnName = "id_professor")
	private Professor professor;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "alunos_turmas", joinColumns = {@JoinColumn(name = "id_turma")}, inverseJoinColumns = {@JoinColumn(name = "id_aluno")})
	private Set<Aluno> alunos;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "turma")
	private Set<Atividade> atividades;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "turma")
	private Set<VideoAula> videoAulas;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "turma")
	private Set<Forum> foruns;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "turma")
	private Set<MaterialComplementar> materiaisComplementares;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "data_criacao", nullable = false)
	private Date dataCriacao;

	public Turma() {
	}

	public Turma(String nome, Date dataCriacao) {
		this.nome = nome;
		this.dataCriacao = dataCriacao;
	}

	public Turma(String nome, Professor professor, Set<Aluno> alunos, Date dataCriacao) {
		this.nome = nome;
		this.professor = professor;
		this.alunos = alunos;
		this.dataCriacao = dataCriacao;
	}

	public void addAluno(Aluno a){
		alunos.add(a);
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	public Set<Aluno> getAlunos() {
		return alunos;
	}

	public void setAlunos(Set<Aluno> alunos) {
		this.alunos = alunos;
	}

	public Set<Atividade> getAtividades() {
		return atividades;
	}

	public void setAtividades(Set<Atividade> atividades) {
		this.atividades = atividades;
	}

	public Set<VideoAula> getVideoAulas() {
		return videoAulas;
	}

	public void setVideoAulas(Set<VideoAula> videoAulas) {
		this.videoAulas = videoAulas;
	}

	public Set<Forum> getForuns() {
		return foruns;
	}

	public void setForuns(Set<Forum> foruns) {
		this.foruns = foruns;
	}

	public Set<MaterialComplementar> getMateriaisComplementares() {
		return materiaisComplementares;
	}

	public void setMateriaisComplementares(Set<MaterialComplementar> materiaisComplementares) {
		this.materiaisComplementares = materiaisComplementares;
	}

	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
	
	
}
