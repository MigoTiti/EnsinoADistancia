package br.com.ensino.teste;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Ignore;
import org.junit.Test;

import br.com.ensino.dao.AtividadeDAO;
import br.com.ensino.dao.TurmaDAO;
import br.com.ensino.entidade.Atividade;
import br.com.ensino.entidade.Turma;

public class AtividadeDAOTeste {

	@Test
	@Ignore
	public void testSalvar() {
		Atividade a = new Atividade("Trabson", new Date());
		
		AtividadeDAO.salvar(a);
		
		System.out.println(AtividadeDAO.buscarPorTitulo("Trabson").getTitulo());
	}

	@Test
	@Ignore
	public void testEditar() {
		Atividade a = AtividadeDAO.buscarPorTitulo("Trabson");
		
		Turma t = new Turma("Turma 1", new Date());
		TurmaDAO.salvar(t);
		
		a.setTurma(t);
		AtividadeDAO.editar(a);
	}

	@Test
	@Ignore
	public void testListarPorTurma() {
		Turma t = TurmaDAO.buscarPorNome("Turma 1");
		
		assertEquals("Trabson", AtividadeDAO.listarPorTurma(t).get(0).getTitulo());
	}

	@Test
	@Ignore
	public void testDeletarPorTitulo() {
		Atividade a = new Atividade("Trabson", new Date());
		
		AtividadeDAO.salvar(a);
		
		AtividadeDAO.deletarPorTitulo("Trabson");
		
		assertNull(AtividadeDAO.buscarPorTitulo("Trabson"));
	}

	@Test
	@Ignore
	public void testDeletar() {
		Atividade a = AtividadeDAO.buscarPorTitulo("Trabson");
		
		AtividadeDAO.deletar(a);
		
		assertNull(AtividadeDAO.buscarPorTitulo("Trabson"));
	}

}
