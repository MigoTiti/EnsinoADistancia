package br.com.ensino.bean;

import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.primefaces.context.RequestContext;

import br.com.ensino.dao.ProfessorDAO;
import br.com.ensino.dao.TurmaDAO;
import br.com.ensino.entidade.Professor;
import br.com.ensino.entidade.Turma;
import br.com.ensino.util.MensagensUtil;

@ManagedBean
@ViewScoped
public class GerenciarTurmasMBean {

	private List<Turma> turmas;
	private List<Turma> turmasFiltradas;

	private String professorEscolhido;
	private List<Professor> professores;
	private String nome;
	private Date data;

	public void excluirTurma(ActionEvent e) {
		Object turma = e.getComponent().getAttributes().get("turmaObj");
		TurmaDAO.deletar((Turma) turma);
		turmas.remove(turma);

		MensagensUtil.info("Sucesso", "Turma excluída com sucesso");
	}

	public void salvarTurma() {
		if (nome != null && !nome.equals(""))
			if (TurmaDAO.buscarPorNome(nome) == null) {
				Turma t = new Turma(nome, new Date());

				t.setProfessor(ProfessorDAO.buscarPorUsuario(professorEscolhido));

				TurmaDAO.salvar(t);
				turmas.add(t);
				zerar();
				MensagensUtil.info("Sucesso", "Turma criada com sucesso");
				RequestContext context = RequestContext.getCurrentInstance();
				context.execute("PF('adicionarTurmaDlg').hide(); PF('dt').filter();");
			} else {
				MensagensUtil.error("Turma não criada", "Nome existente no banco de dados");
				nome = null;
			}
		else
			MensagensUtil.error("Turma não criada", "Digite um nome");
	}

	public void zerar() {
		nome = null;
		data = null;
	}

	public List<Turma> getTurmas() {
		if (turmas == null)
			turmas = TurmaDAO.listar();

		return turmas;
	}

	public void setTurmas(List<Turma> turmas) {
		this.turmas = turmas;
	}

	public List<Turma> getTurmasFiltradas() {
		return turmasFiltradas;
	}

	public void setTurmasFiltradas(List<Turma> turmasFiltradas) {
		this.turmasFiltradas = turmasFiltradas;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public List<Professor> getProfessores() {
		if (professores == null) {
			professores = ProfessorDAO.listar();
		}

		return professores;
	}

	public void setProfessores(List<Professor> nomesProfessores) {
		this.professores = nomesProfessores;
	}

	public String getProfessorEscolhido() {
		return professorEscolhido;
	}

	public void setProfessorEscolhido(String professorEscolhido) {
		this.professorEscolhido = professorEscolhido;
	}

}
