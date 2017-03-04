package br.com.ensino.twitter;

import java.text.SimpleDateFormat;
import java.util.Date;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterUtil implements Runnable {

	final public static int VIDEO_AULA = 1;
	final public static int MATERIAL_COMPLEMENTAR = 2;
	final public static int ATIVIDADE = 3;
	final public static int FORUM = 4;

	private String nomeTurma;
	private int tipo;

	public TwitterUtil(String nomeTurma, int tipo) {
		this.nomeTurma = nomeTurma;
		this.tipo = tipo;
	}

	@Override
	public void run() {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true).setOAuthConsumerKey("70Dha14eTlXCYjNkXUvZbNBX4")
				.setOAuthConsumerSecret("0Tu3k3bah74badBFtEWY8XQi0tX1TRycuZ8x9eLLfC4KLIQsU4")
				.setOAuthAccessToken("2893603863-TXr3XTAvK674nfLzB2WVWarvWkUvsyBKH35DZBA")
				.setOAuthAccessTokenSecret("3CZSqRqI1nHkrPVJI4plTlnIikSr0qP82RDBRuNCUDFGe");
		TwitterFactory tf = new TwitterFactory(cb.build());
		Twitter twitter = tf.getInstance();

		try {
			switch (tipo) {
			case TwitterUtil.VIDEO_AULA:
				twitter.updateStatus("Nova videoaula adicionada na turma '" + nomeTurma + "' em "
						+ new SimpleDateFormat("dd/MM/yyyy',' 'às' HH:mm:ss").format(new Date()));
				break;
			case TwitterUtil.MATERIAL_COMPLEMENTAR:
				twitter.updateStatus("Novo material adicionado na turma '" + nomeTurma + "' em "
						+ new SimpleDateFormat("dd/MM/yyyy',' 'às' HH:mm:ss").format(new Date()));
				break;
			case TwitterUtil.ATIVIDADE:
				twitter.updateStatus("Nova atividade adicionada na turma '" + nomeTurma + "' em "
						+ new SimpleDateFormat("dd/MM/yyyy',' 'às' HH:mm:ss").format(new Date()));
				break;
			case TwitterUtil.FORUM:
				twitter.updateStatus("Novo fórum adicionado na turma '" + nomeTurma + "' em "
						+ new SimpleDateFormat("dd/MM/yyyy',' 'às' HH:mm:ss").format(new Date()));
				break;
			}
		} catch (TwitterException e) {
			e.printStackTrace();
		}
	}
}
