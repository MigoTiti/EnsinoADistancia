package br.com.ensino.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.ensino.dao.AdministradorDAO;
import br.com.ensino.dao.AlunoDAO;
import br.com.ensino.dao.ProfessorDAO;
import br.com.ensino.entidade.Administrador;
import br.com.ensino.entidade.Aluno;
import br.com.ensino.entidade.Professor;

@ManagedBean
@ViewScoped
public class UsuariosMBean {

	private List<Object> usuarios = popularLista();
	private Object usuarioSelecionado;
	
	private String nome;
	private Integer idade;
	private String email;
	private String usuario;
	private String senha;
	private String sexo;
	private String tipo;
	
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
		
		nome = null;
		idade = null;
		email = null;
		usuario = null;
		senha = null;
		tipo = null;
	}
	
	public void excluirUsuario(){
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

	public List<Object> popularLista(){
		List<Object> o = new ArrayList<>(AdministradorDAO.listar());
		o.addAll(AlunoDAO.listar());
		o.addAll(ProfessorDAO.listar());
		
		return o;
	}

	public List<Object> getUsuarios() {
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
}
