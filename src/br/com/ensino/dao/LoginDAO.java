package br.com.ensino.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.ensino.entidade.Administrador;
import br.com.ensino.entidade.Aluno;
import br.com.ensino.entidade.Login;
import br.com.ensino.entidade.Professor;
import br.com.ensino.util.HibernateUtil;

public class LoginDAO {

	public static void salvar(Login login) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.save(login);
			transacao.commit();
		} catch (RuntimeException ex) {
			if (transacao != null)
				transacao.rollback();
			throw ex;
		} finally {
			sessao.close();
		}
	}
	
	public static void editar(Login login) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.update(login);
			transacao.commit();
		} catch (RuntimeException ex) {
			if (transacao != null)
				transacao.rollback();
			throw ex;
		} finally {
			sessao.close();
		}
	}
	
	public static void deletar(Login login){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.delete(login);
			transacao.commit();
		} catch (RuntimeException ex) {
			if (transacao != null)
				transacao.rollback();
			throw ex;
		} finally {
			sessao.close();
		}
	}
	
	public static Object buscarUsuario(String usuario, String senha){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		
		Login user = null;
		
		try {
			Query query = sessao.getNamedQuery("Login.buscarPorUsuarioESenha");
			query.setString("usuario", usuario);
			query.setString("senha", senha);
			
			user = (Login) query.uniqueResult();
		} catch (RuntimeException ex) {
			throw ex;
		} finally {
			sessao.close();
		}
		
		if (user != null){
			Administrador adm = user.getAdministrador();
			if (adm != null)
				return adm;
			
			Professor p = user.getProfessor();
			if (p != null)
				return p;
			
			Aluno al = user.getAluno();
			if (al != null)
				return al;
		}
		
		return null;
	}
}
