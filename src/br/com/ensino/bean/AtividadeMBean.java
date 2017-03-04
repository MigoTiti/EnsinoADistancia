package br.com.ensino.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import br.com.ensino.dao.AtividadeDAO;
import br.com.ensino.dao.EnunciadoDAO;
import br.com.ensino.dao.TurmaDAO;
import br.com.ensino.entidade.Atividade;
import br.com.ensino.entidade.Enunciado;
import br.com.ensino.entidade.Turma;
import br.com.ensino.twitter.TwitterUtil;

@ManagedBean
@ViewScoped
public class AtividadeMBean {

	private String nomeTurma;
	private Turma turma;
	
	private List<Atividade> atividades;
	
	private String nomeAtividade;
	private List<Enunciado> enunciados;
	private Date prazo;
	private Double pontuacao;

	@PostConstruct
	public void iniciarVetor(){
		enunciados = new ArrayList<>();
		enunciados.add(new Enunciado(""));
	}
	
	public void salvarAtividade(){
		Atividade a = new Atividade(nomeAtividade, prazo);
		a.setPontuacao(pontuacao);
		a.setTurma(turma);
		AtividadeDAO.salvar(a);
		
		Runnable r = new TwitterUtil(turma.getNome(), TwitterUtil.ATIVIDADE);
		new Thread(r).start();
		
		for (Enunciado e : enunciados) {
			e.setAtividade(a);
			EnunciadoDAO.salvar(e);
		}

		zerar();
	}
	
	public void zerar(){
		nomeAtividade = null;
		prazo = null;
		pontuacao = null;
		enunciados = new ArrayList<>();
		enunciados.add(new Enunciado(""));
	}
	
	public void novoEnun(ActionEvent e){
		enunciados.add(new Enunciado(""));
	}
	
	public String getNomeTurma() {
		return nomeTurma;
	}
	
	public int getIndex(org.primefaces.component.repeat.IterationStatus status){
		return status.getIndex() + 1;
	}
	
	public void setNomeTurma(String nomeTurma) {
		if (turma == null){
			turma = TurmaDAO.buscarPorNome(nomeTurma);
			atividades = new ArrayList<>(turma.getAtividades());
		}
		this.nomeTurma = nomeTurma;
	}

	public Turma getTurma() {
		return turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}

	public List<Atividade> getAtividades() {
		return atividades;
	}

	public void setAtividades(List<Atividade> atividades) {
		this.atividades = atividades;
	}

	public String getNomeAtividade() {
		return nomeAtividade;
	}

	public void setNomeAtividade(String nomeAtividade) {
		this.nomeAtividade = nomeAtividade;
	}

	public List<Enunciado> getEnunciados() {
		return enunciados;
	}

	public void setEnunciados(List<Enunciado> enunciados) {
		this.enunciados = enunciados;
	}

	public Date getPrazo() {
		return prazo;
	}

	public void setPrazo(Date prazo) {
		this.prazo = prazo;
	}

	public Double getPontuacao() {
		return pontuacao;
	}

	public void setPontuacao(Double pontuacao) {
		this.pontuacao = pontuacao;
	}

}
