package br.com.ensino.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.ensino.entidade.Aluno;
import br.com.ensino.entidade.Turma;
import br.com.ensino.util.HibernateUtil;

public class TurmaDAO {

	@SuppressWarnings("unchecked")
	public static List<Turma> listarNPertencentesAluno(Aluno a) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();

		List<Turma> turmas = null;
		try {
			Query query = sessao.getNamedQuery("Turma.listarNPertenceAluno");
			query.setParameter("aluno", a);
			turmas = query.list();
		} catch (RuntimeException ex) {
			throw ex;
		} finally {
			sessao.close();
		}

		return turmas;
	}

	public static boolean checkNome(String nome) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();

		Object user = null;

		try {
			Query query = sessao.getNamedQuery("Turma.buscarPorNome");
			query.setString("nome", nome);

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

	@SuppressWarnings("unchecked")
	public static List<Turma> listarNPertencentesProfessor() {
		Session sessao = HibernateUtil.getSessionFactory().openSession();

		List<Turma> turmas = null;
		try {
			Query query = sessao.getNamedQuery("Turma.listarNPertenceProfessor");
			turmas = query.list();
		} catch (RuntimeException ex) {
			throw ex;
		} finally {
			sessao.close();
		}

		return turmas;
	}

	public static void salvar(Turma turma) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;

		try {
			transacao = sessao.beginTransaction();
			sessao.save(turma);
			transacao.commit();
		} catch (RuntimeException ex) {
			if (transacao != null)
				transacao.rollback();
			throw ex;
		} finally {
			sessao.close();
		}
	}

	public static void editar(Turma turma) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;

		try {
			transacao = sessao.beginTransaction();
			sessao.update(turma);
			transacao.commit();
		} catch (RuntimeException ex) {
			if (transacao != null)
				transacao.rollback();
			throw ex;
		} finally {
			sessao.close();
		}
	}

	public static Turma buscarPorNome(String nome) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();

		Turma t = null;

		try {
			Query query = sessao.getNamedQuery("Turma.buscarPorNome");
			query.setString("nome", nome);

			t = (Turma) query.uniqueResult();
		} catch (RuntimeException ex) {
			throw ex;
		} finally {
			sessao.close();
		}

		return t;
	}
	
	public static Turma buscarPorId(Integer id) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();

		Turma t = null;

		try {
			Query query = sessao.getNamedQuery("Turma.buscarPorId");
			query.setInteger("id", id);

			t = (Turma) query.uniqueResult();
		} catch (RuntimeException ex) {
			throw ex;
		} finally {
			sessao.close();
		}

		return t;
	}

	@SuppressWarnings("unchecked")
	public static List<Turma> listar() {
		Session sessao = HibernateUtil.getSessionFactory().openSession();

		List<Turma> turmas = null;
		try {
			Query query = sessao.getNamedQuery("Turma.listarTodos");
			turmas = query.list();
		} catch (RuntimeException ex) {
			throw ex;
		} finally {
			sessao.close();
		}

		return turmas;
	}

	public static void deletarPorNome(String nome) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;

		try {
			transacao = sessao.beginTransaction();
			sessao.delete(buscarPorNome(nome));
			transacao.commit();
		} catch (RuntimeException ex) {
			if (transacao != null)
				transacao.rollback();
			throw ex;
		} finally {
			sessao.close();
		}
	}

	public static void deletar(Turma turma) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;

		try {
			transacao = sessao.beginTransaction();
			sessao.delete(turma);
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
