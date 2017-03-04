package br.com.ensino.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import br.com.ensino.dao.ForumDAO;
import br.com.ensino.dao.MensagemForumDAO;
import br.com.ensino.dao.TurmaDAO;
import br.com.ensino.entidade.Forum;
import br.com.ensino.entidade.MensagemForum;
import br.com.ensino.entidade.Turma;
import br.com.ensino.util.MensagensUtil;

@ManagedBean
@ViewScoped
public class ExibirForumMBean {

	private String nometurma;
	private Turma turma;
	private Integer idForum;
	private Forum forum;

	@ManagedProperty(value = "#{loginMBean}")
	private LoginMBean loginMBean;

	private List<MensagemForum> mensagensForum;

	private String mensagemNova;

	public boolean isAtivo() {
		return forum.getEstado().equals("a");
	}

	public void excluirMensagem(ActionEvent e) {
		MensagemForum m = (MensagemForum) e.getComponent().getAttributes().get("mens");
		MensagemForumDAO.deletar(m);

		mensagensForum.remove(m);

		MensagensUtil.info("Sucesso", "Mensagem excluída com sucesso");
	}

	public void salvarMensagem(ActionEvent e) {
		if (!mensagemNova.equals("")) {
			MensagemForum m = new MensagemForum(mensagemNova, loginMBean.getUsuarioLogado(), forum);
			MensagemForumDAO.salvar(m);

			mensagensForum.add(m);
			mensagemNova = null;

			MensagensUtil.info("Sucesso", "Mensagem enviada com sucesso");
		} else 
			MensagensUtil.error("Erro", "Digite uma mensagem válida");
	}

	public String getNometurma() {
		return nometurma;
	}

	public void setNometurma(String nometurma) {
		if (turma == null)
			turma = TurmaDAO.buscarPorNome(nometurma);
		this.nometurma = nometurma;
	}

	public Integer getIdForum() {
		return idForum;
	}

	public boolean isAutor(MensagemForum m) {
		return loginMBean.getUsuarioLogado().equals(m.getUsuario());
	}

	public void setIdForum(Integer idForum) {
		if (forum == null) {
			forum = ForumDAO.buscarPorId(idForum);
			mensagensForum = new ArrayList<>(forum.getMensagens());
		}
		this.idForum = idForum;
	}

	public Forum getForum() {
		return forum;
	}

	public void setForum(Forum forum) {
		this.forum = forum;
	}

	public List<MensagemForum> getMensagensForum() {
		return mensagensForum;
	}

	public void setMensagensForum(List<MensagemForum> mensagensForum) {
		this.mensagensForum = mensagensForum;
	}

	public LoginMBean getLoginMBean() {
		return loginMBean;
	}

	public void setLoginMBean(LoginMBean loginMBean) {
		this.loginMBean = loginMBean;
	}

	public String getMensagemNova() {
		return mensagemNova;
	}

	public void setMensagemNova(String mensagemNova) {
		this.mensagemNova = mensagemNova;
	}

	public Turma getTurma() {
		return turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}
}
