package br.com.ensino.teste;

import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import br.com.ensino.dao.AdministradorDAO;
import br.com.ensino.dao.AlunoDAO;
import br.com.ensino.dao.ProfessorDAO;
import br.com.ensino.dao.TurmaDAO;
import br.com.ensino.entidade.Administrador;
import br.com.ensino.entidade.Aluno;
import br.com.ensino.entidade.Professor;
import br.com.ensino.entidade.Turma;

public class AdmDAOTeste {

	@Test
	//@Ignore
	public void testSalvar() {
		Administrador a = new Administrador("titititiiiiiii", 19, "eu@eua", "e", "e", "M");
		AdministradorDAO.salvar(a);
		
		Professor p = new Professor("Prof 1", 40, "prof@prof", "q", "q", "M");
		ProfessorDAO.salvar(p);
		
		Aluno al = new Aluno("Lucas", 19, "lucasrodriguesgui@hotmail.com", "w", "w", "M");
		AlunoDAO.salvar(al);
		al = AlunoDAO.buscarPorUsuario("w");
		p = ProfessorDAO.buscarPorUsuario("q");
		
		Turma t = new Turma("T1", new Date());
		TurmaDAO.salvar(t);
		t.addAluno(al);
		t.setProfessor(p);
		TurmaDAO.editar(t);
	}
	
	@Test
	@Ignore
	public void testEditar() {
		Administrador a = AdministradorDAO.buscarPorUsuario("eita");

		a.setUsuario("EITAAAA");
		
		AdministradorDAO.editar(a);
	}
	
	@Test
	@Ignore
	public void testDeletar(){
		Administrador a = AdministradorDAO.buscarPorUsuario("eitaa");
		
		AdministradorDAO.deletar(a);
	}
	
	@Test
	@Ignore
	public void testDeletarPorUsuario(){
		AdministradorDAO.deletarPorUsuario("eita");
	}
	
	@Test
	@Ignore
	public void testBuscarPorUsuario(){
		System.out.println(AdministradorDAO.buscarPorUsuario("eitaa").getNome());
	}

	@Test
	@Ignore
	public void testListar() {
		List<Administrador> administradores = AdministradorDAO.listar();
		
		for (Administrador administrador : administradores) {
			System.out.println(administrador.getNome());
		}
	}

}
