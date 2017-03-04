package br.com.ensino.teste;

import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import br.com.ensino.dao.AlunoDAO;
import br.com.ensino.dao.TurmaDAO;
import br.com.ensino.entidade.Aluno;
import br.com.ensino.entidade.Turma;

public class AlunoDAOTeste {

	@Test
	//@Ignore
	public void testSalvar() {
		Aluno a = new Aluno("Lucas", 19, "lucasrodriguesgui@hotmail.com", "w", "w", "M");
		
		AlunoDAO.salvar(a);
		
		System.out.println(AlunoDAO.buscarPorUsuario("titi").getNome());
	}

	@Test
	@Ignore
	public void testEditar() {
		Aluno aluno = AlunoDAO.buscarPorUsuario("Lion");
		
		aluno.setNome("OI");
		aluno.setUsuario("TITI2");
		aluno.setEmail("TESTEEDITAR2");
		
		AlunoDAO.editar(aluno);
		
		System.out.println(AlunoDAO.buscarPorUsuario("TITI").getNome());
	}

	@Test
	@Ignore
	public void testBuscarPorUsuario() {
		Aluno a = new Aluno("Lucas", 19, "lucasrodriguesgui@hotmailW.com", "Lion2", "123", "M");
		
		AlunoDAO.salvar(a);
		
		System.out.println(AlunoDAO.buscarPorUsuario("Lion2").getNome());
	}

	@Test
	@Ignore
	public void testListar() {
		List<Aluno> alunos = AlunoDAO.listar();
		
		for (Aluno aluno : alunos) {
			System.out.println(aluno.getNome());
		}
	}

	@Test
	@Ignore
	public void testDeletarPorUsuario() {
		
	}

	@Test
	@Ignore
	public void testTurma() {
		TurmaDAO.salvar(new Turma("Teste2", new Date()));
		TurmaDAO.salvar(new Turma("Teste3", new Date()));
		TurmaDAO.salvar(new Turma("Teste4", new Date()));
		TurmaDAO.salvar(new Turma("Teste5", new Date()));
	}

}
