package hikingclub.model1.helper;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {

	/** Log4j 객체 생성 */
	private static Logger logger = LoggerFactory.getLogger("Retrofit");

	// 싱글톤 객체 생성 시작
	private static RetrofitHelper current;

	private RetrofitHelper() {
	}

	public static RetrofitHelper getInstance() {
		if (current == null) {
			current = new RetrofitHelper();
		}
		return current;
	}

	public static void freeInstance() {
		current = null;
	}

	// 싱글톤 객체 생성 종료

	/**
	 * Retrofit 객체를 생성, 기본 설정들을 적용한 후 리턴
	 * 
	 * @param baseUrl - 접근하고자 하는 API의 기본 주소(ex: http://도메인)
	 * @return Retrofit 객체
	 */
	public Retrofit getRetrofit(String baseUrl) {

		/** 1) 통신 객체를 생성 */
		// 통신 객체를 생성하기 위한 Builder 객체를 정의
		// 내부 클래스의 형태
		OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

		// 통신 객체에 Log 기록 객체를 연결
		// import okhttp3.logging.HttpLoggingInterceptor;
		HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new MyLogger());
		// import okhttp3.logging.HttpLoggingInterceptor.Level;
		logging.level(Level.BODY);
		httpClient.addInterceptor(logging);

		// MyInterceptor 클래스의 객체를 통신객체에 연결
		httpClient.addNetworkInterceptor(new MyInterceptor());
		
		// Retrofit을 생성하는 기능을 수행하는 객체를 정의
		Retrofit.Builder builder = new Retrofit.Builder();

		// 기본 주소를 지정
		builder.baseUrl(baseUrl);
		
		// 통신 결과를 Gson 객체를 통해 처리하도록 Gson을 연결
		// JSON의 자동 파싱화
		builder.addConverterFactory(GsonConverterFactory.create());
		
		// 통신 객체를 연결
		builder.client(httpClient.build());

		// 생성된 결과 객체를 리턴
		return builder.build();
	}

	/**
	 * Log4j 객체를 통해 로그를 기록하도록 하는 메소드를 구현하는 클래스를 정의 Retrofit이 이 클래스의 객체를 통해 통신 과정을
	 * 의미하는 문자열을 log() 메소드로 전달
	 */
	public class MyLogger implements HttpLoggingInterceptor.Logger {

		@Override
		public void log(String log) {
			// 전달받은 문자열을 로그로 저장
			// 통신 과정의 기록
			logger.debug(log);
		}
	}

	/**
	 * 통신 절차를 가로채서 보조적인 처리를 수행하는 클래스를 정의 이 클래스의 객체를 통신 객체에 연결
	 */
	// import okhttp3.Interceptor;
	public class MyInterceptor implements Interceptor {

		@Override
		public Response intercept(Chain chain) throws IOException {
			Response response = null;

			// import okhttp3.Request;
			Request originalRequest = chain.request();
			Request.Builder builder = originalRequest.newBuilder();

			// 기존 객체의 User-Agent 부분을 삭제
			builder.removeHeader("User-Agent");
			
			// User-Agent 부분을 웹 브라우저에서 접속한 것처럼 변경
			builder.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.87 Safari/537.36");
			Request newRequest = builder.build();

			// import okhttp3.Headers;
			Headers headers = newRequest.headers();

			for (int i = 0; i < headers.size(); i++) {
				String name = headers.name(i);
				String values = headers.get(name);
				logger.debug("(H) -> " + name + ": " + values);
			}
			
			response = chain.proceed(newRequest);

			return response;
		}

	}

}
