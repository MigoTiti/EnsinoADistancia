package br.com.ensino.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.ensino.entidade.Aluno;
import br.com.ensino.entidade.Enunciado;
import br.com.ensino.entidade.Resposta;
import br.com.ensino.util.HibernateUtil;

public class RespostaDAO {

	public static void salvar(Resposta resposta) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.save(resposta);
			transacao.commit();
		} catch (RuntimeException ex) {
			if (transacao != null)
				transacao.rollback();
			throw ex;
		} finally {
			sessao.close();
		}
	}
	
	public static void editar(Resposta resposta) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.update(resposta);
			transacao.commit();
		} catch (RuntimeException ex) {
			if (transacao != null)
				transacao.rollback();
			throw ex;
		} finally {
			sessao.close();
		}
	}
	
	public static void deletar(Resposta resposta){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.delete(resposta);
			transacao.commit();
		} catch (RuntimeException ex) {
			if (transacao != null)
				transacao.rollback();
			throw ex;
		} finally {
			sessao.close();
		}
	}
	
	public static Resposta buscarPorAlunoEEnunciado(Aluno aluno, Enunciado enunciado){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		
		Resposta r = null;
		
		try {
			Query query = sessao.getNamedQuery("Resposta.buscarPorAlunoEEnunciado");
			query.setEntity("aluno", aluno);
			query.setEntity("enunciado", enunciado);
			
			r = (Resposta) query.uniqueResult();
		} catch (RuntimeException ex) {
			throw ex;
		} finally {
			sessao.close();
		}
		
		return r;
	}
}
