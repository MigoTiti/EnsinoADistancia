package br.com.ensino.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.ensino.entidade.Atividade;
import br.com.ensino.entidade.Turma;
import br.com.ensino.util.HibernateUtil;

public class AtividadeDAO {
	
	public static void salvar(Atividade atividade) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.save(atividade);
			transacao.commit();
		} catch (RuntimeException ex) {
			if (transacao != null)
				transacao.rollback();
			throw ex;
		} finally {
			sessao.close();
		}
	}
	
	public static void editar(Atividade atividade) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.update(atividade);
			transacao.commit();
		} catch (RuntimeException ex) {
			if (transacao != null)
				transacao.rollback();
			throw ex;
		} finally {
			sessao.close();
		}
	}
	
	public static Atividade buscarPorId(Integer id){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		
		Atividade a = null;
		
		try {
			Query query = sessao.getNamedQuery("Atividade.buscarPorId");
			query.setInteger("id", id);
			
			a = (Atividade) query.uniqueResult();
		} catch (RuntimeException ex) {
			throw ex;
		} finally {
			sessao.close();
		}
		
		return a;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Atividade> listarPorTurma(Turma turma) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		
		List<Atividade> atividades = null;
		try {
			Query query = sessao.getNamedQuery("Atividade.listarPorTurma");
			query.setEntity("turma", turma);
			atividades = query.list();
		} catch (RuntimeException ex) {
			throw ex;
		} finally {
			sessao.close();
		}
		
		return atividades;
	}

	public static void deletarPorId(Integer id){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.delete(buscarPorId(id));
			transacao.commit();
		} catch (RuntimeException ex) {
			if (transacao != null)
				transacao.rollback();
			throw ex;
		} finally {
			sessao.close();
		}
	}
	
	public static void deletar(Atividade atividade){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.delete(atividade);
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
