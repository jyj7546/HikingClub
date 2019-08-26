package hikingclub.model1.helper;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MailHelper {

	// 싱글톤 객체 생성 시작
	private static MailHelper current = null;

	public static MailHelper getInstance() {
		if (current == null) {
			current = new MailHelper();
		}
		return current;
	}

	public static void freeInstance() {
		current = null;
	}

	private MailHelper() {
		super();
	}

	// 싱글톤 객체 생성 종료

	/** Log4j 객체 */
	private static Logger logger = LoggerFactory.getLogger("MailHelper");

	/** 메일 서버에 로그인하기 위한 사용자의 아이디 */
	// 구글 메일 ID@주소
	private static final String USERNAME = "iteabang@gmail.com";

	// 구글 앱 비밀번호 설정
	private static final String PASSWORD = "jmqovekwutnjzpjy";

	// 시스템에서 자동으로 발송하는 것이므로 보내는 사람은 고정
	// 사이트 아이디와 보내는 주소가 다를 경우 발신을 거부
	private static final String SENDER_EMAIL = "iteabang@gmail.com";

	// 구글 발송 서버 주소
	private static final String SMTP_HOST = "smtp.gmail.com";

	// 구글 발송 서버 포트
	private static final int SMTP_PORT = 465;

	/**
	 * 메일을 발송
	 * 
	 * @param sender   - 발송자 메일 주소
	 * @param receiver - 수신자 메일 주소
	 * @param subject  - 제목
	 * @param content  - 내용
	 * @throws MessagingException
	 */
	// import javax.mail.MessagingException;
	public void sendMail(String receiver, String subject, String content) throws Exception {
		logger.debug("----------------------------------------------------");
		logger.debug("From: %s", SENDER_EMAIL);
		logger.debug(String.format("To: %s", receiver));
		logger.debug(String.format("Subject: %s", subject));
		logger.debug(String.format("Content: %s", content));
		logger.debug("----------------------------------------------------");

		/** 메일 발송 환경설정 */
		// 발송 정보를 담기 위한 객체
		// import java.util.Properties;
		Properties p = new Properties();

		// 발송서버에 로그인하기 위한 계정명, 서버주소, 포트번호 설정
		p.put("mail.smtp.user", USERNAME);
		p.put("mail.smtp.host", SMTP_HOST);
		p.put("mail.smtp.port", SMTP_PORT);

		// 메일 발송 환경설정
		// 수정이 불필요한 정보
		p.put("mail.smtp.starttls.enable", "true");
		p.put("mail.smtp.auth", "true");
		p.put("mail.smtp.debug", "true");
		p.put("mail.smtp.socketFactory.port", "465");
		p.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		p.put("mail.smtp.socketFactory.fallback", "false");

		/** 인증정보 객체 생성 */
		// 추상 클래스에 대한 객체이므로 상속을 받는 클래스가 필요
		// 이 기능을 위해 한 번만 재정의하는 경우, 익명 클래스 방식으로 생성
		// import javax.mail.Authenticator;
		Authenticator auth = new Authenticator() {
			@Override
			public PasswordAuthentication getPasswordAuthentication() {

				// 발송을 위한 메일 주소와 비밀번호를 인증객체로 묶어서 리턴
				return new PasswordAuthentication(USERNAME, PASSWORD);
			}
		};

		/** 인증정보와 설정정보를 묶어서 메일 서버에 대한 세션 객체를 생성 */
		// import javax.mail.Session;
		Session ses = Session.getInstance(p, auth);

		// 메일을 전송할 때 상세한 상황을 콘솔에 출력
		ses.setDebug(true);

		/** 세션을 포함하는 메시지 객체 생성 --> 발신주소, 수신주소, 제목, 내용 설정 */
		// 메일의 내용을 담기 위한 객체
		// import javax.mail.internet.MimeMessage;
		MimeMessage msg = new MimeMessage(ses);

		// 제목 설정
		msg.setSubject(subject);

		// 보내는 사람의 메일주소
		// import javax.mail.Address;
		Address fromAddr = new InternetAddress(SENDER_EMAIL);
		msg.setFrom(fromAddr);

		// 받는 사람의 메일주소
		Address toAddr = new InternetAddress(receiver);

		// import javax.mail.Message;
		// CC : 참조, 참조 인원들 확인 가능
		// BCC : 숨은 참조, 참조 인원들 확인 불가능
		msg.addRecipient(Message.RecipientType.TO, toAddr);

		// 메시지 본문의 내용과 형식, 캐릭터 셋 설정
		msg.setContent(content, "text/html;charset=UTF-8");

		/** 메일 발송하기 */
		// import javax.mail.Transport;
		Transport.send(msg);
	}
}
