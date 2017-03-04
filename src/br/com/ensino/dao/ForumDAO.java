package br.com.ensino.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.ensino.entidade.Forum;
import br.com.ensino.entidade.Turma;
import br.com.ensino.util.HibernateUtil;

public class ForumDAO {

	public static void salvar(Forum forum) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.save(forum);
			transacao.commit();
		} catch (RuntimeException ex) {
			if (transacao != null)
				transacao.rollback();
			throw ex;
		} finally {
			sessao.close();
		}
	}
	
	public static void editar(Forum forum) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.update(forum);
			transacao.commit();
		} catch (RuntimeException ex) {
			if (transacao != null)
				transacao.rollback();
			throw ex;
		} finally {
			sessao.close();
		}
	}
	
	public static Forum buscarPorTituloETurma(String titulo, Turma turma){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		
		Forum f = null;
		
		try {
			Query query = sessao.getNamedQuery("Forum.buscarPorTituloETurma");
			query.setString("titulo", titulo);
			query.setEntity("turma", turma);
			
			f = (Forum) query.uniqueResult();
		} catch (RuntimeException ex) {
			throw ex;
		} finally {
			sessao.close();
		}
		
		return f;
	}
	
	public static Forum buscarPorId(Integer id){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		
		Forum f = null;
		
		try {
			Query query = sessao.getNamedQuery("Forum.buscarPorId");
			query.setInteger("id", id);
			
			f = (Forum) query.uniqueResult();
		} catch (RuntimeException ex) {
			throw ex;
		} finally {
			sessao.close();
		}
		
		return f;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Forum> listarPorTurma(Turma turma) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		
		List<Forum> foruns = null;
		try {
			Query query = sessao.getNamedQuery("Forum.listarPorTurma");
			query.setEntity("turma", turma);
			foruns = query.list();
		} catch (RuntimeException ex) {
			throw ex;
		} finally {
			sessao.close();
		}
		
		return foruns;
	}

	public static void deletarPorTituloETurma(String titulo, Turma turma){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.delete(buscarPorTituloETurma(titulo, turma));
			transacao.commit();
		} catch (RuntimeException ex) {
			if (transacao != null)
				transacao.rollback();
			throw ex;
		} finally {
			sessao.close();
		}
	}
	
	public static void deletar(Forum forum){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.delete(forum);
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
