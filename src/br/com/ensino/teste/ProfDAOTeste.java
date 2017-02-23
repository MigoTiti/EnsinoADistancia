package br.com.ensino.teste;

import org.junit.Ignore;
import org.junit.Test;

import br.com.ensino.dao.ProfessorDAO;
import br.com.ensino.entidade.Professor;

public class ProfDAOTeste {

	@Test
	@Ignore
	public void testSalvar() {
		Professor p = new Professor("Prof 1", 40, "prof@prof", "User1", "s1", "M");
		
		ProfessorDAO.salvar(p);
	}

	@Test
	@Ignore
	public void testEditar() {
		Professor p = new Professor("Prof 2", 40, "prof@prof2", "User2", "s1", "M");
		
		ProfessorDAO.salvar(p);
		
		p = ProfessorDAO.buscarPorUsuario("User2");
		p.setEmail("prof@profTeste");
		p.setSenha("s3");
		
		ProfessorDAO.editar(p);
	}

	@Test
	@Ignore
	public void testDeletarPorUsuario() {
		Professor p = new Professor("Prof 2", 40, "prof@prof234", "User54", "s1", "M");
		
		ProfessorDAO.salvar(p);
		
		ProfessorDAO.deletarPorUsuario("User54");
	}

	@Test
	@Ignore
	public void testDeletar() {
		Professor p = new Professor("Prof 2", 40, "prof@prof23", "User5", "s1", "M");
		
		ProfessorDAO.salvar(p);
		
		p = ProfessorDAO.buscarPorUsuario("User5");
		
		ProfessorDAO.deletar(p);
	}

}
