package br.com.ensino.bean;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import br.com.ensino.dao.MaterialComplementarDAO;
import br.com.ensino.dao.TurmaDAO;
import br.com.ensino.entidade.MaterialComplementar;
import br.com.ensino.entidade.Turma;
import br.com.ensino.twitter.TwitterUtil;
import br.com.ensino.util.MensagensUtil;

@ManagedBean
@ViewScoped
public class TurmaMBean {

	private String turmaSelecionada;
	private Turma turma;

	private List<MaterialComplementar> materiais;
	private List<MaterialComplementar> materiaisFiltrados;
	private MaterialComplementar materialSelecionado;

	private String nomeMaterial;
	private MaterialComplementar materialIntermed;
	private StreamedContent arquivo;

	public boolean isTurma() {
		return turma != null;
	}

	public void salvarMaterial() {
		if (materialIntermed == null){
			MensagensUtil.error("Erro", "Escolha um arquivo");
			return;
		}

		if (!nomeMaterial.equals("")) {
			materialIntermed.setTitulo(nomeMaterial);
			materialIntermed.setTurma(turma);
			MaterialComplementarDAO.salvar(materialIntermed);

			Runnable r = new TwitterUtil(turma.getNome(), TwitterUtil.MATERIAL_COMPLEMENTAR);
			new Thread(r).start();

			materiais.add(materialIntermed);

			zerar();
			
			MensagensUtil.info("Sucesso", "Material adicionado com sucesso");
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("PF('adicionarMaterialDlg').hide(); PF('dt').filter();");
		} else
			MensagensUtil.error("Erro", "Digite um nome válido");
	}

	public void zerar() {
		nomeMaterial = null;
		materialIntermed = null;
		materialSelecionado = null;
	}

	public void upload(FileUploadEvent e) {
		try {
			Blob blob = new javax.sql.rowset.serial.SerialBlob(e.getFile().getContents());
			if (materialIntermed == null)
				materialIntermed = new MaterialComplementar(blob, e.getFile().getFileName(),
						e.getFile().getContentType());
			else {
				materialIntermed.setArquivo(blob);
				materialIntermed.setNomeArquivo(e.getFile().getFileName());
				materialIntermed.setTipo(e.getFile().getContentType());
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	public StreamedContent getFile() {
		try {
			InputStream stream = materialSelecionado.getArquivo().getBinaryStream();
			return new DefaultStreamedContent(stream, materialSelecionado.getTipo(),
					materialSelecionado.getNomeArquivo());
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void excluirMaterial(ActionEvent e) {
		MaterialComplementar m = (MaterialComplementar) e.getComponent().getAttributes().get("materialObj");
		MaterialComplementarDAO.deletar(m);
		materiais.remove(m);
		
		MensagensUtil.info("Sucesso", "Material excluído com sucesso");
	}

	public void prepDownload(ActionEvent e) {
		materialSelecionado = (MaterialComplementar) e.getComponent().getAttributes().get("materialObj");
		setArquivo(getFile());
	}

	public String getTurmaSelecionada() {
		return turmaSelecionada;
	}

	public void setTurmaSelecionada(String turmaSelecionada) {
		if (turmaSelecionada != null) {
			turma = TurmaDAO.buscarPorNome(turmaSelecionada);
			materiais = new ArrayList<>(turma.getMateriaisComplementares());
		}
		this.turmaSelecionada = turmaSelecionada;
	}

	public Turma getTurma() {
		return turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}

	public List<MaterialComplementar> getMateriais() {
		return materiais;
	}

	public void setMateriais(List<MaterialComplementar> materiais) {
		this.materiais = materiais;
	}

	public List<MaterialComplementar> getMateriaisFiltrados() {
		return materiaisFiltrados;
	}

	public void setMateriaisFiltrados(List<MaterialComplementar> materiaisFiltrados) {
		this.materiaisFiltrados = materiaisFiltrados;
	}

	public MaterialComplementar getMaterialSelecionado() {
		return materialSelecionado;
	}

	public void setMaterialSelecionado(MaterialComplementar materialSelecionado) {
		this.materialSelecionado = materialSelecionado;
	}

	public String getNomeMaterial() {
		return nomeMaterial;
	}

	public void setNomeMaterial(String nomeMaterial) {
		this.nomeMaterial = nomeMaterial;
	}

	public MaterialComplementar getMaterialIntermed() {
		return materialIntermed;
	}

	public void setMaterialIntermed(MaterialComplementar materialIntermed) {
		this.materialIntermed = materialIntermed;
	}

	public StreamedContent getArquivo() {
		return arquivo;
	}

	public void setArquivo(StreamedContent arquivo) {
		this.arquivo = getFile();
	}
}
