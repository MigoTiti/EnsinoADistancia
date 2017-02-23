package br.com.ensino.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.ensino.entidade.Aluno;
import br.com.ensino.util.HibernateUtil;

public class AlunoDAO {
	
	public static void salvar(Aluno aluno) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.save(aluno);
			transacao.commit();
		} catch (RuntimeException ex) {
			if (transacao != null)
				transacao.rollback();
			throw ex;
		} finally {
			sessao.close();
		}
	}
	
	public static void editar(Aluno aluno) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.update(aluno);
			transacao.commit();
		} catch (RuntimeException ex) {
			if (transacao != null)
				transacao.rollback();
			throw ex;
		} finally {
			sessao.close();
		}
	}
	
	public static Aluno buscarPorUsuario(String usuario){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		
		Aluno a = null;
		
		try {
			Query query = sessao.getNamedQuery("Aluno.buscarPorUsuario");
			query.setString("usuario", usuario);
			
			a = (Aluno) query.uniqueResult();
		} catch (RuntimeException ex) {
			throw ex;
		} finally {
			sessao.close();
		}
		
		return a;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Aluno> listar() {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		
		List<Aluno> alunos = null;
		try {
			Query query = sessao.getNamedQuery("Aluno.listarTodos");
			alunos = query.list();
		} catch (RuntimeException ex) {
			throw ex;
		} finally {
			sessao.close();
		}
		
		return alunos;
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
	
	public static void deletar(Aluno aluno){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.delete(aluno);
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
