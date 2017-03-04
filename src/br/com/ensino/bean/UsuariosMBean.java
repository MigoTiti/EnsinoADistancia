package br.com.ensino.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.primefaces.context.RequestContext;

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
@ViewScoped
public class UsuariosMBean {

	private List<Object> usuarios;
	private Object usuarioSelecionado;

	@ManagedProperty(value = "#{loginMBean}")
	private LoginMBean loginMBean;

	private String nome;
	private Integer idade;
	private String email;
	private String usuario;
	private String usuario2;
	private String senha;
	private String sexo;
	private String tipo;

	private List<Turma> turmasMatriculadas;
	private List<Turma> turmasGeral;
	private List<Turma> turmasSelecionadas;

	public boolean notAdministrador() {
		return !(usuarioSelecionado instanceof Administrador);
	}

	public void removerAlunoDeTurma(Turma t) {
		t.removerAluno((Aluno) usuarioSelecionado);
		TurmaDAO.editar(t);

		turmasMatriculadas.remove(t);
		turmasGeral = TurmaDAO.listarNPertencentesAluno((Aluno) usuarioSelecionado);
		
		MensagensUtil.info("Sucesso", "Aluno removido de turma com sucesso");
	}

	public void removerProfessorDeTurma(Turma t) {
		t.setProfessor(null);
		TurmaDAO.editar(t);
		
		turmasMatriculadas.remove(t);
		turmasGeral = TurmaDAO.listarNPertencentesProfessor();
		
		MensagensUtil.info("Sucesso", "Professor removido de turma com sucesso");
	}

	public boolean isAluno() {
		return usuarioSelecionado instanceof Aluno;
	}

	public boolean isProfessor() {
		return usuarioSelecionado instanceof Professor;
	}

	public void matricularTurmas() {
		if (turmasSelecionadas.size() > 0) {
			if (usuarioSelecionado instanceof Aluno) {
				for (Turma turma : turmasSelecionadas) {
					turma.addAluno((Aluno) usuarioSelecionado);
					TurmaDAO.editar(turma);
				}
				usuarioSelecionado = AlunoDAO.buscarPorUsuario(((Aluno) usuarioSelecionado).getUsuario());
				turmasMatriculadas = new ArrayList<>(((Aluno) usuarioSelecionado).getTurmas());
				MensagensUtil.info("Sucesso", "Aluno matriculado em turmas com sucesso");
			} else if (usuarioSelecionado instanceof Professor) {
				for (Turma turma : turmasSelecionadas) {
					turma.setProfessor((Professor) usuarioSelecionado);
					TurmaDAO.editar(turma);
				}
				usuarioSelecionado = ProfessorDAO.buscarPorUsuario(((Professor) usuarioSelecionado).getUsuario());
				turmasMatriculadas = new ArrayList<>(((Professor) usuarioSelecionado).getTurmas());
				MensagensUtil.info("Sucesso", "Professor matriculado em turmas com sucesso");
			}
		}
		
		cancelarTurmas();
	}

	public void cancelarTurmas() {
		turmasGeral = null;
		turmasSelecionadas = null;
	}

	public void salvarUsuario() {
		Object u = null;

		if (!(nome.equals("") || idade.equals("") || email.equals("") || usuario.equals("") || senha.equals("")
				|| sexo.equals(""))) {
			if (LoginDAO.checkUsuario(usuario)) {
				try {
					switch (tipo) {
					case "Al":
						if (AlunoDAO.checkEmail(email)) {
							u = new Aluno(nome, idade, email, usuario, senha, sexo);
							AlunoDAO.salvar((Aluno) u);
						} else {
							MensagensUtil.error("Usuário não foi criado",
									"E-mail já cadastrado para o tipo selecionado");
							email = null;
							return;
						}
						break;
					case "Pr":
						if (ProfessorDAO.checkEmail(email)) {
							u = new Professor(nome, idade, email, usuario, senha, sexo);
							ProfessorDAO.salvar((Professor) u);
						} else {
							MensagensUtil.error("Usuário não foi criado",
									"E-mail já cadastrado para o tipo selecionado");
							email = null;
							return;
						}
						break;
					case "Adm":
						if (AdministradorDAO.checkEmail(email)) {
							u = new Administrador(nome, idade, email, usuario, senha, sexo);
							AdministradorDAO.salvar((Administrador) u);
						} else {
							MensagensUtil.error("Usuário não foi criado",
									"E-mail já cadastrado para o tipo selecionado");
							email = null;
							return;
						}
						break;
					}

					MensagensUtil.info("Sucesso", "Usuário criado com sucesso");
					usuarios.add(u);
					zerar();

					RequestContext context = RequestContext.getCurrentInstance();
					context.execute("PF('adicionarUserDlg').hide(); PF('dtable').filter();");

				} catch (RuntimeException e) {
					MensagensUtil.error("Usuário não foi criado", "Dados inválidos");
					return;
				}
			} else {
				MensagensUtil.error("Usuário não foi criado", "Nome de usuário já existente no banco de dados");
				usuario = null;
			}
		} else
			MensagensUtil.error("Usuário não foi criado", "preencha todos os campos");
	}

	public void salvarAlteracoes() {
		if (!(nome.equals("") || idade.equals("") || email.equals("") || usuario.equals("") || senha.equals("")
				|| sexo.equals(""))) {
			if (usuarioSelecionado instanceof Aluno) {
				if (AlunoDAO.checkEmail(email) || ((Aluno) usuarioSelecionado).getEmail().equals(email)) {
					((Aluno) usuarioSelecionado).setNome(nome);
					((Aluno) usuarioSelecionado).setEmail(email);
					((Aluno) usuarioSelecionado).setIdade(idade);
					((Aluno) usuarioSelecionado).setSenha(senha);
					((Aluno) usuarioSelecionado).setUsuario(usuario);
					((Aluno) usuarioSelecionado).setSexo(sexo);
					AlunoDAO.editar((Aluno) usuarioSelecionado);
					
					MensagensUtil.info("Sucesso", "Usuário editado com sucesso");
					popularLista();
					return;
				} else {
					email = ((Aluno) usuarioSelecionado).getEmail();
					MensagensUtil.error("Usuário não editado", "E-mail já existente no banco de dados");
				}
			} else if (usuarioSelecionado instanceof Professor) {
				if (ProfessorDAO.checkEmail(email) || ((Professor) usuarioSelecionado).getEmail().equals(email)) {
					((Professor) usuarioSelecionado).setNome(nome);
					((Professor) usuarioSelecionado).setEmail(email);
					((Professor) usuarioSelecionado).setIdade(idade);
					((Professor) usuarioSelecionado).setSenha(senha);
					((Professor) usuarioSelecionado).setUsuario(usuario);
					((Professor) usuarioSelecionado).setSexo(sexo);
					ProfessorDAO.editar((Professor) usuarioSelecionado);
					
					MensagensUtil.info("Sucesso", "Usuário editado com sucesso");
					popularLista();
					return;
				} else {
					email = ((Professor) usuarioSelecionado).getEmail();
					MensagensUtil.error("Usuário não editado", "E-mail já existente no banco de dados");
				}
			} else if (usuarioSelecionado instanceof Administrador) {
				if (AdministradorDAO.checkEmail(email)
						|| ((Administrador) usuarioSelecionado).getEmail().equals(email)) {
					((Administrador) usuarioSelecionado).setNome(nome);
					((Administrador) usuarioSelecionado).setEmail(email);
					((Administrador) usuarioSelecionado).setIdade(idade);
					((Administrador) usuarioSelecionado).setSenha(senha);
					((Administrador) usuarioSelecionado).setUsuario(usuario);
					((Administrador) usuarioSelecionado).setSexo(sexo);
					AdministradorDAO.editar((Administrador) usuarioSelecionado);
					
					MensagensUtil.info("Sucesso", "Usuário editado com sucesso");
					popularLista();
					return;
				} else {
					email = ((Administrador) usuarioSelecionado).getEmail();
					MensagensUtil.error("Usuário não editado", "E-mail já existente no banco de dados");
				}
			}
		} else
			MensagensUtil.error("Usuário não editado", "Preencha todos os campos");
	}

	public void zerar() {
		nome = null;
		idade = null;
		email = null;
		usuario = null;
		senha = null;
		tipo = null;
	}

	public void excluirUsuario(ActionEvent e) {
		Object u = e.getComponent().getAttributes().get("user");
		UsuarioUtil.deletarUsuario(u);
		usuarios.remove(u);
		MensagensUtil.info("Sucesso", "Usuário excluído com sucesso");
	}

	public void popularLista() {
		usuarios = new ArrayList<>(AdministradorDAO.listarExcetoAtual(loginMBean.getAdministradorLogado()));
		usuarios.addAll(AlunoDAO.listar());
		usuarios.addAll(ProfessorDAO.listar());
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getNome() {
		return nome;
	}

	public Integer getIdade() {
		return idade;
	}

	public String getEmail() {
		return email;
	}

	public String getUsuario() {
		return usuario;
	}

	public String getSenha() {
		return senha;
	}

	public String getSexo() {
		if (sexo != null) {
			if (sexo.equals("M"))
				return "Masculino";
			else
				return "Feminino";
		}

		return sexo;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setIdade(Integer idade) {
		this.idade = idade;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public List<Object> getUsuarios() {
		if (usuarios == null)
			popularLista();
		return usuarios;
	}

	public void setUsuarios(List<Object> usuarios) {
		this.usuarios = usuarios;
	}

	public Object getUsuarioSelecionado() {
		return usuarioSelecionado;
	}

	public void setUsuarioSelecionado(Object usuarioSelecionado) {
		this.usuarioSelecionado = usuarioSelecionado;
	}

	public String getUsuario2() {
		return usuario2;
	}

	public void setUsuario2(String usuario2) {
		this.usuarioSelecionado = AlunoDAO.buscarPorUsuario(usuario2);
		if (this.usuarioSelecionado == null) {
			this.usuarioSelecionado = ProfessorDAO.buscarPorUsuario(usuario2);
			if (this.usuarioSelecionado == null) {
				this.usuarioSelecionado = AdministradorDAO.buscarPorUsuario(usuario2);
				nome = ((Administrador) this.usuarioSelecionado).getNome();
				idade = ((Administrador) this.usuarioSelecionado).getIdade();
				sexo = ((Administrador) this.usuarioSelecionado).getSexo();
				email = ((Administrador) this.usuarioSelecionado).getEmail();
				senha = ((Administrador) this.usuarioSelecionado).getSenha();
			} else {
				nome = ((Professor) this.usuarioSelecionado).getNome();
				idade = ((Professor) this.usuarioSelecionado).getIdade();
				sexo = ((Professor) this.usuarioSelecionado).getSexo();
				email = ((Professor) this.usuarioSelecionado).getEmail();
				senha = ((Professor) this.usuarioSelecionado).getSenha();
			}
		} else {
			nome = ((Aluno) this.usuarioSelecionado).getNome();
			idade = ((Aluno) this.usuarioSelecionado).getIdade();
			sexo = ((Aluno) this.usuarioSelecionado).getSexo();
			email = ((Aluno) this.usuarioSelecionado).getEmail();
			senha = ((Aluno) this.usuarioSelecionado).getSenha();
		}
		usuario = usuario2;
	}

	public List<Turma> getTurmasGeral() {
		if (turmasGeral == null && usuarioSelecionado != null)
			if (usuarioSelecionado instanceof Aluno)
				turmasGeral = TurmaDAO.listarNPertencentesAluno((Aluno) usuarioSelecionado);
			else if (usuarioSelecionado instanceof Professor)
				turmasGeral = TurmaDAO.listarNPertencentesProfessor();

		return turmasGeral;
	}

	public void setTurmasGeral(List<Turma> turmasGeral) {
		this.turmasGeral = turmasGeral;
	}

	public List<Turma> getTurmasSelecionadas() {
		return turmasSelecionadas;
	}

	public void setTurmasSelecionadas(List<Turma> turmasSelecionadas) {
		this.turmasSelecionadas = turmasSelecionadas;
	}

	public List<Turma> getTurmasMatriculadas() {
		if (turmasMatriculadas == null && usuarioSelecionado != null)
			if (usuarioSelecionado instanceof Aluno)
				turmasMatriculadas = new ArrayList<>(((Aluno) usuarioSelecionado).getTurmas());
			else if (usuarioSelecionado instanceof Professor)
				turmasMatriculadas = new ArrayList<>(((Professor) usuarioSelecionado).getTurmas());
		return turmasMatriculadas;
	}

	public void setTurmasMatriculadas(List<Turma> turmasMatriculadas) {
		this.turmasMatriculadas = turmasMatriculadas;
	}

	public LoginMBean getLoginMBean() {
		return loginMBean;
	}

	public void setLoginMBean(LoginMBean loginMBean) {
		this.loginMBean = loginMBean;
	}
}
