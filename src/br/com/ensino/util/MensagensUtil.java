package br.com.ensino.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class MensagensUtil {

	public static void info(String cabecalho, String mensagem) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, cabecalho, mensagem));
	}

	public static void warn(String cabecalho, String mensagem) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_WARN, cabecalho, mensagem));
	}

	public static void error(String cabecalho, String mensagem) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, cabecalho, mensagem));
	}

	public static void fatal(String cabecalho, String mensagem) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_FATAL, cabecalho, mensagem));
	}
}
