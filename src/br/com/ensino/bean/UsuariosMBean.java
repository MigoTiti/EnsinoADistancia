package br.com.ensino.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.ensino.dao.AdministradorDAO;
import br.com.ensino.dao.AlunoDAO;
import br.com.ensino.dao.ProfessorDAO;

@ManagedBean
@ViewScoped
public class UsuariosMBean {

	private List<Object> usuarios = popularLista();
	private Object usuarioSelecionado;
	
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
