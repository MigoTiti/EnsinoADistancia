package br.com.ensino.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.ensino.entidade.Atividade;
import br.com.ensino.entidade.Enunciado;
import br.com.ensino.util.HibernateUtil;

public class EnunciadoDAO {
	
	public static void salvar(Enunciado enunciado) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.save(enunciado);
			transacao.commit();
		} catch (RuntimeException ex) {
			if (transacao != null)
				transacao.rollback();
			throw ex;
		} finally {
			sessao.close();
		}
	}
	
	public static void editar(Enunciado enunciado) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.update(enunciado);
			transacao.commit();
		} catch (RuntimeException ex) {
			if (transacao != null)
				transacao.rollback();
			throw ex;
		} finally {
			sessao.close();
		}
	}
	
	public static Enunciado buscarPorAtividadeETexto(String texto, Atividade atividade){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		
		Enunciado e = null;
		
		try {
			Query query = sessao.getNamedQuery("Enunciado.buscarPorAtividadeETexto");
			query.setString("texto", texto);
			query.setEntity("atividade", atividade);
			
			e = (Enunciado) query.uniqueResult();
		} catch (RuntimeException ex) {
			throw ex;
		} finally {
			sessao.close();
		}
		
		return e;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Enunciado> listarPorAtividade(Atividade atividade) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		
		List<Enunciado> enunciados = null;
		try {
			Query query = sessao.getNamedQuery("Enunciado.listarPorAtividade");
			query.setEntity("atividade", atividade);
			enunciados = query.list();
		} catch (RuntimeException ex) {
			throw ex;
		} finally {
			sessao.close();
		}
		
		return enunciados;
	}
	
	public static void deletar(Enunciado enunciado){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.delete(enunciado);
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
