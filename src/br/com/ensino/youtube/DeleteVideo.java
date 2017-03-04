package br.com.ensino.youtube;

import java.io.IOException;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.services.youtube.YouTube;
import com.google.common.collect.Lists;

public class DeleteVideo implements Runnable{

	private static YouTube youtube;

	private String idVideo;
	
	public DeleteVideo(String idVideo) {
		this.idVideo = idVideo;
	}

	@Override
	public void run() {
		List<String> scopes = Lists.newArrayList("https://www.googleapis.com/auth/youtube", "https://www.googleapis.com/auth/youtubepartner");

		try {
			Credential credential = Auth.authorize(scopes, "deletevideo");

			youtube = new YouTube.Builder(Auth.HTTP_TRANSPORT, Auth.JSON_FACTORY, credential)
					.setApplicationName("EnsinoADistancia").build();

			YouTube.Videos.Delete deletar = youtube.videos().delete(idVideo);
			deletar.execute();


		} catch (GoogleJsonResponseException e) {
			System.err.println("GoogleJsonResponseException code: " + e.getDetails().getCode() + " : "
					+ e.getDetails().getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("IOException: " + e.getMessage());
			e.printStackTrace();
		} catch (Throwable t) {
			System.err.println("Throwable: " + t.getMessage());
			t.printStackTrace();
		}		
	}
}
