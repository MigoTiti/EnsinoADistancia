package br.com.ensino.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;

import br.com.ensino.dao.ForumDAO;
import br.com.ensino.dao.MensagemForumDAO;
import br.com.ensino.dao.TurmaDAO;
import br.com.ensino.entidade.Forum;
import br.com.ensino.entidade.MensagemForum;
import br.com.ensino.entidade.Turma;
import br.com.ensino.twitter.TwitterUtil;
import br.com.ensino.util.MensagensUtil;

@ManagedBean
@ViewScoped
public class ForunsMBean {

	private String nomeTurma;
	private Turma turma;
	private List<Forum> foruns;

	@ManagedProperty(value = "#{loginMBean}")
	private LoginMBean loginMBean;

	private String nomeForum;
	private String textoPrimeiraMensagem;

	public void salvarForum() {
		if (!nomeForum.equals("")) {
			if (!textoPrimeiraMensagem.equals("")) {
				Forum f = new Forum(new Date(), nomeForum, "a", turma);

				ForumDAO.salvar(f);

				Runnable r = new TwitterUtil(turma.getNome(), TwitterUtil.FORUM);
				new Thread(r).start();

				MensagemForum m = new MensagemForum(textoPrimeiraMensagem, loginMBean.getUsuarioLogado(), f);
				MensagemForumDAO.salvar(m);

				foruns.add(f);

				zerar();
				MensagensUtil.info("Sucesso", "Fórum criado com sucesso");
				RequestContext context = RequestContext.getCurrentInstance();
				context.execute("PF('adicionarForumDlg').hide();");
			} else
				MensagensUtil.error("Erro", "Digite uma mensagem válida");
		} else
			MensagensUtil.error("Erro", "Digite um título válido");
	}

	public void zerar() {
		nomeForum = null;
		textoPrimeiraMensagem = null;
	}

	public String getNomeTurma() {
		return nomeTurma;
	}

	public void setNomeTurma(String nomeTurma) {
		if (turma == null) {
			turma = TurmaDAO.buscarPorNome(nomeTurma);
			foruns = new ArrayList<>(turma.getForuns());
		}

		this.nomeTurma = nomeTurma;
	}

	public Turma getTurma() {
		return turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}

	public List<Forum> getForuns() {
		return foruns;
	}

	public void setForuns(List<Forum> foruns) {
		this.foruns = foruns;
	}

	public String getNomeForum() {
		return nomeForum;
	}

	public void setNomeForum(String nomeForum) {
		this.nomeForum = nomeForum;
	}

	public String getTextoPrimeiraMensagem() {
		return textoPrimeiraMensagem;
	}

	public void setTextoPrimeiraMensagem(String textoPrimeiraMensagem) {
		this.textoPrimeiraMensagem = textoPrimeiraMensagem;
	}

	public LoginMBean getLoginMBean() {
		return loginMBean;
	}

	public void setLoginMBean(LoginMBean loginMBean) {
		this.loginMBean = loginMBean;
	}

}
