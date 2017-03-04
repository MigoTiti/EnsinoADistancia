package br.com.ensino.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class AssistirVideoMBean {

	private String idVideo;
	private String src;
	private String titulo;
	private String nomeTurma;

	public String getIdVideo() {
		return idVideo;
	}

	public void setIdVideo(String idVideo) {
		if (src == null)
			src = "http://www.youtube.com/embed/" + idVideo
					+ "?enablejsapi=1&controls=2&showinfo=0&autoplay=1&fs=1&rel=0&origin=http://localhost:8080";
		this.idVideo = idVideo;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getNomeTurma() {
		return nomeTurma;
	}

	public void setNomeTurma(String nomeTurma) {
		this.nomeTurma = nomeTurma;
	}

}
