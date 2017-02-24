package br.com.ensino.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import br.com.ensino.dao.AdministradorDAO;
import br.com.ensino.dao.AlunoDAO;
import br.com.ensino.dao.ProfessorDAO;
import br.com.ensino.entidade.Administrador;
import br.com.ensino.entidade.Aluno;
import br.com.ensino.entidade.Professor;

@ManagedBean
@ViewScoped
public class UsuariosMBean {

	private List<Object> usuarios;
	private Object usuarioSelecionado;
	
	private String nome;
	private Integer idade;
	private String email;
	private String usuario;
	private String usuario2;
	private String senha;
	private String sexo;
	private String tipo;
	
	public boolean notAdministrador(){
		if (usuarioSelecionado instanceof Administrador)
			return false;
		
		return true;
	}
	
	public void salvarUsuario(){
		switch (tipo) {
		case "Al":
			AlunoDAO.salvar(new Aluno(nome, idade, email, usuario, senha, sexo));
			break;
		case "Pr":
			ProfessorDAO.salvar(new Professor(nome, idade, email, usuario, senha, sexo));
			break;
		case "Adm":
			AdministradorDAO.salvar(new Administrador(nome, idade, email, usuario, senha, sexo));
			break;
		}
		
		popularLista();
		
		zerar();
	}
	
	public void salvarAlteracoes(){
		if (usuarioSelecionado instanceof Aluno){
			((Aluno) usuarioSelecionado).setNome(nome);
			((Aluno) usuarioSelecionado).setEmail(email);
			((Aluno) usuarioSelecionado).setIdade(idade);
			((Aluno) usuarioSelecionado).setSenha(senha);
			((Aluno) usuarioSelecionado).setUsuario(usuario);
			((Aluno) usuarioSelecionado).setSexo(sexo);
			AlunoDAO.editar((Aluno) usuarioSelecionado);
		} else if (usuarioSelecionado instanceof Professor){
			((Professor) usuarioSelecionado).setNome(nome);
			((Professor) usuarioSelecionado).setEmail(email);
			((Professor) usuarioSelecionado).setIdade(idade);
			((Professor) usuarioSelecionado).setSenha(senha);
			((Professor) usuarioSelecionado).setUsuario(usuario);
			((Professor) usuarioSelecionado).setSexo(sexo);
			ProfessorDAO.editar((Professor) usuarioSelecionado);
		} else if (usuarioSelecionado instanceof Administrador){
			((Administrador) usuarioSelecionado).setNome(nome);
			((Administrador) usuarioSelecionado).setEmail(email);
			((Administrador) usuarioSelecionado).setIdade(idade);
			((Administrador) usuarioSelecionado).setSenha(senha);
			((Administrador) usuarioSelecionado).setUsuario(usuario);
			((Administrador) usuarioSelecionado).setSexo(sexo);
			AdministradorDAO.editar((Administrador) usuarioSelecionado);
		}
		
		popularLista();		
		return;
	}
	
	public void zerar(){
		nome = null;
		idade = null;
		email = null;
		usuario = null;
		senha = null;
		tipo = null;
	}
	
	public void excluirUsuario(ActionEvent e){
		Object usuarioSelecionado = e.getComponent().getAttributes().get("user");
		if (usuarioSelecionado instanceof Aluno){
			AlunoDAO.deletar((Aluno) usuarioSelecionado);
			popularLista();
			return;
		}
		
		if (usuarioSelecionado instanceof Professor){
			ProfessorDAO.deletar((Professor) usuarioSelecionado);
			popularLista();
			return;
		}
		
		if (usuarioSelecionado instanceof Administrador){
			AdministradorDAO.deletar((Administrador) usuarioSelecionado);
			popularLista();
			return;
		}
	}
	
	public void popularLista(){
		usuarios = new ArrayList<>(AdministradorDAO.listar());
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
		if(sexo != null){
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
		if (this.usuarioSelecionado == null){
			this.usuarioSelecionado = ProfessorDAO.buscarPorUsuario(usuario2);
			if (this.usuarioSelecionado == null){
				this.usuarioSelecionado = AdministradorDAO.buscarPorUsuario(usuario2);
				nome = ((Administrador)this.usuarioSelecionado).getNome();
				idade = ((Administrador)this.usuarioSelecionado).getIdade();
				sexo = ((Administrador)this.usuarioSelecionado).getSexo();
				email = ((Administrador)this.usuarioSelecionado).getEmail();
				senha = ((Administrador)this.usuarioSelecionado).getSenha();
			} else {
				nome = ((Professor)this.usuarioSelecionado).getNome();
				idade = ((Professor)this.usuarioSelecionado).getIdade();
				sexo = ((Professor)this.usuarioSelecionado).getSexo();
				email = ((Professor)this.usuarioSelecionado).getEmail();
				senha = ((Professor)this.usuarioSelecionado).getSenha();
			}
		} else {
			nome = ((Aluno)this.usuarioSelecionado).getNome();
			idade = ((Aluno)this.usuarioSelecionado).getIdade();
			sexo = ((Aluno)this.usuarioSelecionado).getSexo();
			email = ((Aluno)this.usuarioSelecionado).getEmail();
			senha = ((Aluno)this.usuarioSelecionado).getSenha();
		}
		usuario = usuario2;
	}
}
