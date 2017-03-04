package br.com.ensino.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.ensino.entidade.Aluno;
import br.com.ensino.entidade.Turma;
import br.com.ensino.util.HibernateUtil;

public class AlunoDAO {
	
	@SuppressWarnings("unchecked")
	public static List<Aluno> listarNPertenceTurma(Turma t) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		
		List<Aluno> alunos = null;
		try {
			Query query = sessao.getNamedQuery("Aluno.listarNPertenceTurma");
			query.setEntity("turma", t);
			alunos = query.list();
		} catch (RuntimeException ex) {
			throw ex;
		} finally {
			sessao.close();
		}
		
		return alunos;
	}
	
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

	public static boolean checkEmail(String email) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();

		Object user = null;

		try {
			Query query = sessao.getNamedQuery("Aluno.buscarPorEmail");
			query.setString("email", email);

			user = query.uniqueResult();
		} catch (RuntimeException ex) {
			throw ex;
		} finally {
			sessao.close();
		}

		if (user == null)
			return true;
		else
			return false;
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
			Aluno a = buscarPorUsuario(usuario);

			for (Turma turma : a.getTurmas()) {
				turma.removerAluno(a);
				TurmaDAO.editar(turma);
			}
			
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

			for (Turma turma : aluno.getTurmas()) {
				turma.removerAluno(aluno);
				TurmaDAO.editar(turma);
			}
			
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
