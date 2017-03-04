package br.com.ensino.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ensino.dao.AdministradorDAO;
import br.com.ensino.dao.AlunoDAO;
import br.com.ensino.dao.LoginDAO;
import br.com.ensino.dao.ProfessorDAO;
import br.com.ensino.dao.TurmaDAO;
import br.com.ensino.entidade.Administrador;
import br.com.ensino.entidade.Aluno;
import br.com.ensino.entidade.Professor;
import br.com.ensino.entidade.Turma;
import br.com.ensino.util.MensagensUtil;
import br.com.ensino.util.UsuarioUtil;

@ManagedBean
@SessionScoped
public class LoginMBean {

	private List<Turma> turmasCadastradas;

	private String usuario;
	private String senha;
	private Aluno alunoLogado;
	private Professor professorLogado;
	private Administrador administradorLogado;
	private Object usuarioLogado;

	public String login() {
		if (!usuario.equals("") && !senha.equals("")) {
			Object usuarioLogado = LoginDAO.buscarUsuario(usuario, senha);

			if (usuarioLogado != null) {
				this.usuarioLogado = usuarioLogado;
				if (usuarioLogado instanceof Aluno) {
					alunoLogado = (Aluno) usuarioLogado;
					turmasCadastradas = new ArrayList<>(alunoLogado.getTurmas());
				} else if (usuarioLogado instanceof Professor) {
					professorLogado = (Professor) usuarioLogado;
					turmasCadastradas = new ArrayList<>(professorLogado.getTurmas());
				} else if (usuarioLogado instanceof Administrador) {
					turmasCadastradas = TurmaDAO.listar();
					administradorLogado = (Administrador) usuarioLogado;
				}
				return "/pages/inicio.xhtml?faces-redirect=true";
			}

			MensagensUtil.error("Erro", "Usuário e/ou senha incorretos");
			return null;
		} else {
			MensagensUtil.error("Erro", "Preencha todos os campos");
			return null;
		}
	}

	public boolean editarUsuario(String nome, String senha, String email, Integer idade, String sexo) {
		if (!(nome.equals("") || idade.equals("") || email.equals("") || usuario.equals("") || senha.equals("")
				|| sexo.equals(""))) {
			if (usuarioLogado instanceof Aluno) {
				if (AlunoDAO.checkEmail(email) || ((Aluno) usuarioLogado).getEmail().equals(email)) {
					((Aluno) usuarioLogado).setNome(nome);
					((Aluno) usuarioLogado).setEmail(email);
					((Aluno) usuarioLogado).setIdade(idade);
					((Aluno) usuarioLogado).setSenha(senha);
					((Aluno) usuarioLogado).setUsuario(usuario);
					((Aluno) usuarioLogado).setSexo(sexo);

					MensagensUtil.info("Sucesso", "Usuário editado com sucesso");
					usuarioLogado = UsuarioUtil.editarUsuario(usuarioLogado, nome, senha, email, idade, sexo);
					alunoLogado = (Aluno) usuarioLogado;
					return true;
				} else {
					email = ((Aluno) usuarioLogado).getEmail();
					MensagensUtil.error("Usuário não editado", "E-mail já existente no banco de dados");
					return false;
				}
			} else if (usuarioLogado instanceof Professor) {
				if (ProfessorDAO.checkEmail(email) || ((Professor) usuarioLogado).getEmail().equals(email)) {
					((Professor) usuarioLogado).setNome(nome);
					((Professor) usuarioLogado).setEmail(email);
					((Professor) usuarioLogado).setIdade(idade);
					((Professor) usuarioLogado).setSenha(senha);
					((Professor) usuarioLogado).setUsuario(usuario);
					((Professor) usuarioLogado).setSexo(sexo);

					MensagensUtil.info("Sucesso", "Usuário editado com sucesso");
					usuarioLogado = UsuarioUtil.editarUsuario(usuarioLogado, nome, senha, email, idade, sexo);
					professorLogado = (Professor) usuarioLogado;
					return true;
				} else {
					email = ((Professor) usuarioLogado).getEmail();
					MensagensUtil.error("Usuário não editado", "E-mail já existente no banco de dados");
					return false;
				}
			} else if (usuarioLogado instanceof Administrador) {
				if (AdministradorDAO.checkEmail(email) || ((Administrador) usuarioLogado).getEmail().equals(email)) {
					((Administrador) usuarioLogado).setNome(nome);
					((Administrador) usuarioLogado).setEmail(email);
					((Administrador) usuarioLogado).setIdade(idade);
					((Administrador) usuarioLogado).setSenha(senha);
					((Administrador) usuarioLogado).setUsuario(usuario);
					((Administrador) usuarioLogado).setSexo(sexo);

					MensagensUtil.info("Sucesso", "Usuário editado com sucesso");
					usuarioLogado = UsuarioUtil.editarUsuario(usuarioLogado, nome, senha, email, idade, sexo);
					administradorLogado = (Administrador) usuarioLogado;
					return true;
				} else {
					email = ((Administrador) usuarioLogado).getEmail();
					MensagensUtil.error("Usuário não editado", "E-mail já existente no banco de dados");
					return false;
				}
			}
		} else {
			MensagensUtil.error("Usuário não editado", "Preencha todos os campos");
			return false;
		}
		return false;
	}

	public String logout() {
		if (isAluno())
			alunoLogado = null;
		else if (isAdministrador())
			administradorLogado = null;
		else if (isProfessor())
			professorLogado = null;

		usuarioLogado = null;
		turmasCadastradas = null;
		usuario = null;
		senha = null;

		return "/index.xhtml?faces-redirect=true";
	}

	public boolean isLogado() {
		return !(alunoLogado == null && professorLogado == null && administradorLogado == null);
	}

	public boolean isAluno() {
		return alunoLogado != null;
	}

	public boolean isNotAdministrador() {
		return administradorLogado == null;
	}

	public boolean isAdministrador() {
		return administradorLogado != null;
	}

	public boolean isProfessor() {
		return professorLogado != null;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Aluno getAlunoLogado() {
		return alunoLogado;
	}

	public void setAlunoLogado(Aluno alunoLogado) {
		this.alunoLogado = alunoLogado;
	}

	public Professor getProfessorLogado() {
		return professorLogado;
	}

	public void setProfessorLogado(Professor professorLogado) {
		this.professorLogado = professorLogado;
	}

	public Administrador getAdministradorLogado() {
		return administradorLogado;
	}

	public void setAdministradorLogado(Administrador administradorLogado) {
		this.administradorLogado = administradorLogado;
	}

	public List<Turma> getTurmasCadastradas() {
		return turmasCadastradas;
	}

	public void setTurmasCadastradas(List<Turma> turmasCadastradas) {
		this.turmasCadastradas = turmasCadastradas;
	}

	public Object getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Object usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}
}
