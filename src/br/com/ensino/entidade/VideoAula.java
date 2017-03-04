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
import javax.persistence.Table;

@Entity
@Table(name = "video_aula")
public class VideoAula {

	public VideoAula() {
	}

	public VideoAula(String titulo) {
		this.titulo = titulo;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_video_aula")
	private Integer id;
	
	@Column(length = 100, nullable = false)
	private String titulo;
	
	@Column(length = 20, nullable = false, unique = true)
	private String idVideo;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_turma_video_aula", referencedColumnName = "id_turma")
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

	public String getIdVideo() {
		return idVideo;
	}

	public void setIdVideo(String idVideo) {
		this.idVideo = idVideo;
	}

	public Turma getTurma() {
		return turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}
	
	@Override
	public boolean equals(Object obj) {
		return ((VideoAula) obj).getId().equals(this.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
