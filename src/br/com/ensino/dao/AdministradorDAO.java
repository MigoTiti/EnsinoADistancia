package br.com.ensino.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.ensino.entidade.Administrador;
import br.com.ensino.util.HibernateUtil;

public class AdministradorDAO {
	
	public static void salvar(Administrador administrador) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.save(administrador);
			transacao.commit();
		} catch (RuntimeException ex) {
			if (transacao != null)
				transacao.rollback();
			throw ex;
		} finally {
			sessao.close();
		}
	}
	
	public static void editar(Administrador administrador) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.update(administrador);
			transacao.commit();
		} catch (RuntimeException ex) {
			if (transacao != null)
				transacao.rollback();
			throw ex;
		} finally {
			sessao.close();
		}
	}
	
	public static Administrador buscarPorUsuario(String usuario){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		
		Administrador a = null;
		
		try {
			Query query = sessao.getNamedQuery("Administrador.buscarPorUsuario");
			query.setString("usuario", usuario);
			
			a = (Administrador) query.uniqueResult();
		} catch (RuntimeException ex) {
			throw ex;
		} finally {
			sessao.close();
		}
		
		return a;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Administrador> listar() {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		
		List<Administrador> administradores = null;
		try {
			Query query = sessao.getNamedQuery("Administrador.listarTodos");
			administradores = query.list();
		} catch (RuntimeException ex) {
			throw ex;
		} finally {
			sessao.close();
		}
		
		return administradores;
	}

	public static void deletarPorUsuario(String usuario){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.delete(buscarPorUsuario(usuario));
			transacao.commit();
		} catch (RuntimeException ex) {
			if (transacao != null)
				transacao.rollback();
			throw ex;
		} finally {
			sessao.close();
		}
	}
	
	public static void deletar(Administrador administrador){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.delete(administrador);
			transacao.commit();
		} catch (RuntimeException ex) {
			if (transacao != null)
				transacao.rollback();
			throw ex;
		} finally {
			sessao.close();
		}
	}
}
