package br.com.ensino.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.primefaces.context.RequestContext;

import br.com.ensino.dao.AtividadeDAO;
import br.com.ensino.dao.RespostaDAO;
import br.com.ensino.dao.TurmaDAO;
import br.com.ensino.entidade.Atividade;
import br.com.ensino.entidade.Enunciado;
import br.com.ensino.entidade.Resposta;
import br.com.ensino.entidade.Turma;
import br.com.ensino.util.MensagensUtil;

@ManagedBean
@ViewScoped
public class DetalharAtividadeMBean {

	private String nomeTurma;
	private Integer idAtividade;
	private Turma turma;
	private Atividade atividade;
	private List<Enunciado> enunciados;

	@ManagedProperty(value = "#{loginMBean}")
	private LoginMBean loginMBean;

	private String correcao;
	private Double pontuacao;
	private Resposta respostaSelecionada;

	private Enunciado enunciadoSelecionado;
	private String texto;

	public String getNomeTurma() {
		return nomeTurma;
	}

	public Resposta getRespostaEnunciado(Object e) {
		return RespostaDAO.buscarPorAlunoEEnunciado(loginMBean.getAlunoLogado(), (Enunciado) e);
	}

	public boolean isValidaR() {
		return respostaSelecionada != null;
	}

	public boolean isValidaAndPrazoR() {
		return respostaSelecionada == null && prazoAcabou();
	}

	public boolean isValidaAndPrazo(Object e) {
		if (e == null)
			return false;
		return (getRespostaEnunciado(e) == null) && prazoAcabou();
	}

	public boolean isValida(Object e) {
		if (e == null)
			return false;
		return getRespostaEnunciado(e) != null;
	}

	public void zerarAluno() {
		enunciadoSelecionado = null;
		texto = null;
	}

	public boolean isCorrigida() {
		if (respostaSelecionada == null)
			return false;
		return respostaSelecionada.getCorrecao() != null && respostaSelecionada.getPontuacao() != null;
	}

	public void salvarRespostaAluno(ActionEvent e) {
		if (!texto.equals("")) {
			RespostaDAO.salvar(new Resposta(texto, enunciadoSelecionado, loginMBean.getAlunoLogado()));
			
			zerarAluno();
			
			MensagensUtil.info("Sucesso", "Resposta salva com sucesso");
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("PF('SuaRespostaDiag').hide();");
		} else {
			MensagensUtil.error("Erro", "Preencha o campo 'Resposta'");
		}
	}

	public void salvarResposta(ActionEvent e) {
		if (!(correcao.equals("") || pontuacao.equals(""))) {
			respostaSelecionada.setCorrecao(correcao);
			respostaSelecionada.setPontuacao(pontuacao);

			RespostaDAO.editar(respostaSelecionada);

			zerar();

			MensagensUtil.info("Sucesso", "Correção salva com sucesso");
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("PF('DetalhesRespostaDiag').hide();");
		} else {
			MensagensUtil.info("Erro", "Preencha todos os campos");
		}
	}

	public boolean prazoAcabou() {
		return (atividade.getPrazo().compareTo(new Date()) >= 0);
	}

	public void zerar() {
		correcao = null;
		pontuacao = null;
		respostaSelecionada = null;
	}

	public void setNomeTurma(String nomeTurma) {
		if (turma == null)
			turma = TurmaDAO.buscarPorNome(nomeTurma);
		this.nomeTurma = nomeTurma;
	}

	public Integer getIdAtividade() {
		return idAtividade;
	}

	public void setIdAtividade(Integer idAtividade) {
		if (atividade == null) {
			atividade = AtividadeDAO.buscarPorId(idAtividade);
			enunciados = new ArrayList<>(atividade.getEnunciados());
		}
		this.idAtividade = idAtividade;
	}

	public Turma getTurma() {
		return turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}

	public Atividade getAtividade() {
		return atividade;
	}

	public void setAtividade(Atividade atividade) {
		this.atividade = atividade;
	}

	public List<Enunciado> getEnunciados() {
		return enunciados;
	}

	public void setEnunciados(List<Enunciado> enunciados) {
		this.enunciados = enunciados;
	}

	public Resposta getRespostaSelecionada() {
		return respostaSelecionada;
	}

	public void setRespostaSelecionada(Resposta respostaSelecionada) {
		this.respostaSelecionada = respostaSelecionada;
	}

	public String getCorrecao() {
		return correcao;
	}

	public void setCorrecao(String correcao) {
		this.correcao = correcao;
	}

	public Double getPontuacao() {
		return pontuacao;
	}

	public void setPontuacao(Double pontuacao) {
		this.pontuacao = pontuacao;
	}

	public LoginMBean getLoginMBean() {
		return loginMBean;
	}

	public void setLoginMBean(LoginMBean loginMBean) {
		this.loginMBean = loginMBean;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public Enunciado getEnunciadoSelecionado() {
		return enunciadoSelecionado;
	}

	public void setEnunciadoSelecionado(Enunciado enunciadoSelecionado) {
		this.enunciadoSelecionado = enunciadoSelecionado;
	}

}
