package br.com.ensino.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import br.com.ensino.entidade.Administrador;
import br.com.ensino.entidade.Aluno;
import br.com.ensino.entidade.Professor;
import br.com.ensino.util.UsuarioUtil;

@ManagedBean
@ViewScoped
public class MeuPerfilMBean {

	@ManagedProperty(value = "#{loginMBean}")
	private LoginMBean loginMBean;
	
	private String nome;
	private Integer idade;
	private String email;
	private String senha;
	private String sexo;
	
	public void editarUsuario(ActionEvent e){
		if(!loginMBean.editarUsuario(nome, senha, email, idade, sexo)){
			Aluno al = loginMBean.getAlunoLogado();
			if (al != null){
				nome = al.getNome();
				idade = al.getIdade();
				email = al.getEmail();
				senha = al.getSenha();
				sexo = al.getSexo();
				return;
			}
			
			Professor p = loginMBean.getProfessorLogado();
			
			if (p != null){
				nome = p.getNome();
				idade = p.getIdade();
				email = p.getEmail();
				senha = p.getSenha();
				sexo = p.getSexo();
				return;
			}
			
			Administrador adm = loginMBean.getAdministradorLogado();
			
			if (adm != null){
				nome = adm.getNome();
				idade = adm.getIdade();
				email = adm.getEmail();
				senha = adm.getSenha();
				sexo = adm.getSexo();
				return;
			}
		}
	}
	
	public LoginMBean getLoginMBean() {
		return loginMBean;
	}
	
	public void setLoginMBean(LoginMBean loginMBean) {
		this.loginMBean = loginMBean;
	}
	
	public String getNome() {
		if (nome == null)
			nome = UsuarioUtil.getNome(loginMBean.getUsuarioLogado());
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public Integer getIdade() {
		if (idade == null)
			idade = UsuarioUtil.getIdade(loginMBean.getUsuarioLogado());
		return idade;
	}
	
	public void setIdade(Integer idade) {
		this.idade = idade;
	}
	
	public String getEmail() {
		if (email == null)
			email = UsuarioUtil.getEmail(loginMBean.getUsuarioLogado());
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getSenha() {
		if (senha == null)
			senha = UsuarioUtil.getSenha(loginMBean.getUsuarioLogado());
		return senha;
	}
	
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public String getSexo() {
		if (sexo == null)
			sexo = UsuarioUtil.getSexo(loginMBean.getUsuarioLogado());
		return sexo;
	}
	
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
}
