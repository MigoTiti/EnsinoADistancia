package br.com.ensino.bean;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.apache.commons.io.FileUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;

import br.com.ensino.dao.TurmaDAO;
import br.com.ensino.dao.VideoAulaDAO;
import br.com.ensino.entidade.Turma;
import br.com.ensino.entidade.VideoAula;
import br.com.ensino.util.MensagensUtil;
import br.com.ensino.youtube.DeleteVideo;
import br.com.ensino.youtube.UploadVideo;

@ManagedBean
@ViewScoped
public class AulaMBean {

	private String turmaSelecionada;
	private Turma turma;
	private List<VideoAula> videoAulas;
	private List<VideoAula> videoAulasFiltradas;

	private String nomeAula;
	private String nomeVideo;

	public String getTurmaSelecionada() {
		return turmaSelecionada;
	}

	public void upload(FileUploadEvent e) {
		byte[] arquivo = e.getFile().getContents();
		String nomeArquivo = e.getFile().getFileName();
		String caminho = "C:\\Users\\Inspirio\\Desktop\\UFPA\\EnsinoADistancia\\src\\main\\resources\\";

		try {
			FileUtils.writeByteArrayToFile(new File(caminho + nomeArquivo), arquivo);
			nomeVideo = nomeArquivo;
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public void salvarVideoAula() {
		if (!nomeAula.equals("")) {
			if (new File("C:\\Users\\Inspirio\\Desktop\\UFPA\\EnsinoADistancia\\src\\main\\resources\\" + nomeVideo)
					.exists() && !nomeVideo.equals("")) {
				VideoAula v = new VideoAula(nomeAula);
				v.setTurma(turma);

				Runnable r = new UploadVideo(nomeVideo, v);
				new Thread(r).start();

				videoAulas.add(v);

				zerar();
				MensagensUtil.info("Sucesso", "Videoaula salva com sucesso");
				RequestContext context = RequestContext.getCurrentInstance();
				context.execute("PF('adicionarAulaDlg').hide(); PF('dt').filter();");
			} else
				MensagensUtil.error("Erro", "Arquivo não existe ou foi deletado");
		} else
			MensagensUtil.error("Erro", "Digite um título válido");
	}

	public void zerar() {
		nomeAula = null;
		nomeVideo = null;
	}

	public void excluirAula(ActionEvent e) {
		VideoAula v = (VideoAula) e.getComponent().getAttributes().get("aulaObj");
		String idVideo = v.getIdVideo();
		VideoAulaDAO.deletar(v);
		videoAulas.remove(v);

		Runnable r = new DeleteVideo(idVideo);
		new Thread(r).start();
		
		MensagensUtil.info("Sucesso", "Videoaula excluída com sucesso");
	}

	public void setTurmaSelecionada(String turmaSelecionada) {
		if (turma == null) {
			turma = TurmaDAO.buscarPorNome(turmaSelecionada);
			videoAulas = new ArrayList<>(turma.getVideoAulas());
		}
		this.turmaSelecionada = turmaSelecionada;
	}

	public Turma getTurma() {
		return turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}

	public List<VideoAula> getVideoAulas() {
		return videoAulas;
	}

	public void setVideoAulas(List<VideoAula> videoAulas) {
		this.videoAulas = videoAulas;
	}

	public List<VideoAula> getVideoAulasFiltradas() {
		return videoAulasFiltradas;
	}

	public void setVideoAulasFiltradas(List<VideoAula> videoAulasFiltradas) {
		this.videoAulasFiltradas = videoAulasFiltradas;
	}

	public String getNomeAula() {
		return nomeAula;
	}

	public void setNomeAula(String nomeAula) {
		this.nomeAula = nomeAula;
	}

}
