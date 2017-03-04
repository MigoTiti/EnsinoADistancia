package br.com.ensino.servidor;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import br.com.ensino.util.HibernateUtil;

public class ContextListener implements ServletContextListener{

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		HibernateUtil.getSessionFactory().close();		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		HibernateUtil.getSessionFactory().openSession();
	}

}
