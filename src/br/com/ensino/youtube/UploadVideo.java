package br.com.ensino.youtube;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.googleapis.media.MediaHttpUploader;
import com.google.api.client.googleapis.media.MediaHttpUploaderProgressListener;
import com.google.api.client.http.InputStreamContent;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoSnippet;
import com.google.api.services.youtube.model.VideoStatus;
import com.google.common.collect.Lists;

import br.com.ensino.dao.VideoAulaDAO;
import br.com.ensino.entidade.VideoAula;
import br.com.ensino.twitter.TwitterUtil;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

public class UploadVideo implements Runnable {

	private static YouTube youtube;

	private static final String VIDEO_FILE_FORMAT = "video/*";

	private String nomeVideo;
	private VideoAula video;

	public UploadVideo(String nomeVideo, VideoAula v) {
		this.nomeVideo = nomeVideo;
		this.video = v;
	}

	@Override
	public void run() {
		List<String> scopes = Lists.newArrayList("https://www.googleapis.com/auth/youtube");

		try {
			Credential credential = Auth.authorize(scopes, "uploadvideo");

			youtube = new YouTube.Builder(Auth.HTTP_TRANSPORT, Auth.JSON_FACTORY, credential)
					.setApplicationName("EnsinoADistancia").build();

			System.out.println("Uploading: " + nomeVideo);

			Video videoObjectDefiningMetadata = new Video();
			VideoStatus status = new VideoStatus();
			status.setPrivacyStatus("unlisted");
			videoObjectDefiningMetadata.setStatus(status);

			VideoSnippet snippet = new VideoSnippet();

			Calendar cal = Calendar.getInstance();
			snippet.setTitle("" + cal.getTime());

			videoObjectDefiningMetadata.setSnippet(snippet);

			File videoFile = new File(
					"C:\\Users\\Inspirio\\Desktop\\UFPA\\EnsinoADistancia\\src\\main\\resources\\" + nomeVideo);

			InputStreamContent mediaContent = new InputStreamContent(VIDEO_FILE_FORMAT,
					new BufferedInputStream(new FileInputStream(videoFile)));
			mediaContent.setLength(videoFile.length());

			YouTube.Videos.Insert videoInsert = youtube.videos().insert("snippet,statistics,status",
					videoObjectDefiningMetadata, mediaContent);

			MediaHttpUploader uploader = videoInsert.getMediaHttpUploader();

			uploader.setDirectUploadEnabled(false);

			MediaHttpUploaderProgressListener progressListener = new MediaHttpUploaderProgressListener() {
				public void progressChanged(MediaHttpUploader uploader) throws IOException {
					switch (uploader.getUploadState()) {
					case INITIATION_STARTED:
						System.out.println("Inicialização iniciada");
						break;
					case INITIATION_COMPLETE:
						System.out.println("Inicialização concluída");
						break;
					case MEDIA_IN_PROGRESS:
						System.out.println("Upload em andamento");
						break;
					case MEDIA_COMPLETE:
						System.out.println("Upload concluído!");
						break;
					case NOT_STARTED:
						System.out.println("Upload não iniciado!");
						break;
					}
				}
			};
			uploader.setProgressListener(progressListener);

			Video returnedVideo = videoInsert.execute();

			video.setIdVideo(returnedVideo.getId());
			VideoAulaDAO.salvar(video);
			
			Runnable r = new TwitterUtil(video.getTurma().getNome(), TwitterUtil.VIDEO_AULA);
			new Thread(r).start();

			new File("C:\\Users\\Inspirio\\Desktop\\UFPA\\EnsinoADistancia\\src\\main\\resources\\" + nomeVideo)
					.delete();

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