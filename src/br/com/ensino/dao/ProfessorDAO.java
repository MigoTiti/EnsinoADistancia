package br.com.ensino.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.ensino.entidade.Professor;
import br.com.ensino.util.HibernateUtil;

public class ProfessorDAO {
	
	public static void salvar(Professor professor) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.save(professor);
			transacao.commit();
		} catch (RuntimeException ex) {
			if (transacao != null)
				transacao.rollback();
			throw ex;
		} finally {
			sessao.close();
		}
	}
	
	public static void editar(Professor professor) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.update(professor);
			transacao.commit();
		} catch (RuntimeException ex) {
			if (transacao != null)
				transacao.rollback();
			throw ex;
		} finally {
			sessao.close();
		}
	}
	
	public static Professor buscarPorUsuario(String usuario){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		
		Professor p = null;
		
		try {
			Query query = sessao.getNamedQuery("Professor.buscarPorUsuario");
			query.setString("usuario", usuario);
			
			p = (Professor) query.uniqueResult();
		} catch (RuntimeException ex) {
			throw ex;
		} finally {
			sessao.close();
		}
		
		return p;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Professor> listar() {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		
		List<Professor> professores = null;
		try {
			Query query = sessao.getNamedQuery("Professor.listarTodos");
			professores = query.list();
		} catch (RuntimeException ex) {
			throw ex;
		} finally {
			sessao.close();
		}
		
		return professores;
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
	
	public static void deletar(Professor professor){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.delete(professor);
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
