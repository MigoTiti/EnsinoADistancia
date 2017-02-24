package br.com.ensino.teste;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import br.com.ensino.dao.AdministradorDAO;
import br.com.ensino.entidade.Administrador;

public class AdmDAOTeste {

	@Test
	//@Ignore
	public void testSalvar() {
		Administrador a = new Administrador("titititiiiiiii", 19, "eu@eua", "eitaa", "123", "M");
		
		AdministradorDAO.salvar(a);
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
