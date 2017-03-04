package br.com.ensino.teste;

import java.io.IOException;
import java.util.List;

import com.google.common.collect.Lists;

import br.com.ensino.youtube.Auth;

public class RealizarAuth {

	public static void main(String[] args) {
		List<String> scopes = Lists.newArrayList("https://www.googleapis.com/auth/youtube");

		try {
			Auth.authorize(scopes, "deletevideo");
		} catch (IOException e) {
			
		}
	}
}
