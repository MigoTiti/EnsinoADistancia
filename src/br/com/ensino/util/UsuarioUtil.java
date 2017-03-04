package br.com.ensino.util;

import br.com.ensino.dao.AdministradorDAO;
import br.com.ensino.dao.AlunoDAO;
import br.com.ensino.dao.ProfessorDAO;
import br.com.ensino.entidade.Administrador;
import br.com.ensino.entidade.Aluno;
import br.com.ensino.entidade.Professor;

public class UsuarioUtil {

	public static Object editarUsuario(Object u, String nome, String senha, String email, Integer idade, String sexo) {
		if (u instanceof Aluno) {
			Aluno us = (Aluno) u;
			us.setNome(nome);
			us.setEmail(email);
			us.setSenha(senha);
			us.setIdade(idade);
			us.setSexo(sexo);
			AlunoDAO.editar(us);
			return us;
		} else if (u instanceof Professor) {
			Professor us = (Professor) u;
			us.setNome(nome);
			us.setEmail(email);
			us.setSenha(senha);
			us.setIdade(idade);
			us.setSexo(sexo);
			ProfessorDAO.editar(us);
			return us;
		} else if (u instanceof Administrador) {
			Administrador us = (Administrador) u;
			us.setNome(nome);
			us.setEmail(email);
			us.setSenha(senha);
			us.setIdade(idade);
			us.setSexo(sexo);
			AdministradorDAO.editar(us);
			return us;
		} 
		
		return null;
	}

	public static void deletarUsuario(Object u) {
		if (u instanceof Aluno) {
			AlunoDAO.deletar((Aluno) u);
			return;
		}

		if (u instanceof Professor) {
			ProfessorDAO.deletar((Professor) u);
			return;
		}

		if (u instanceof Administrador) {
			AdministradorDAO.deletar((Administrador) u);
			return;
		}
	}

	public static String getNome(Object u) {
		if (u instanceof Aluno)
			return ((Aluno) u).getNome();
		else if (u instanceof Professor)
			return ((Professor) u).getNome();
		else if (u instanceof Administrador)
			return ((Administrador) u).getNome();
		
		return null;
	}

	public static String getEmail(Object u) {
		if (u instanceof Aluno)
			return ((Aluno) u).getEmail();
		else if (u instanceof Professor)
			return ((Professor) u).getEmail();
		else if (u instanceof Administrador)
			return ((Administrador) u).getEmail();
		
		return null;
	}

	public static Integer getIdade(Object u) {
		if (u instanceof Aluno)
			return ((Aluno) u).getIdade();
		else if (u instanceof Professor)
			return ((Professor) u).getIdade();
		else if (u instanceof Administrador)
			return ((Administrador) u).getIdade();
		
		return null;
	}

	public static String getUsuario(Object u) {
		if (u instanceof Aluno)
			return ((Aluno) u).getLogin().getUsuario();
		else if (u instanceof Professor)
			return ((Professor) u).getLogin().getUsuario();
		else if (u instanceof Administrador)
			return ((Administrador) u).getLogin().getUsuario();
		
		return null;
	}

	public static String getSenha(Object u) {
		if (u instanceof Aluno)
			return ((Aluno) u).getLogin().getSenha();
		else if (u instanceof Professor)
			return ((Professor) u).getLogin().getSenha();
		else if (u instanceof Administrador)
			return ((Administrador) u).getLogin().getSenha();
		
		return null;
	}

	public static String getSexo(Object u) {
		if (u instanceof Aluno)
			return ((Aluno) u).getSexo();
		else if (u instanceof Professor)
			return ((Professor) u).getSexo();
		else if (u instanceof Administrador)
			return ((Administrador) u).getSexo();
		
		return null;
	}

}
