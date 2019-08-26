package hikingclub.model1.helper;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

public class WebHelper {

	/** Log4j 객체 */
	// import org.slf4j.Logger;
	// import org.slf4j.LoggerFactory;
	private static Logger logger = LoggerFactory.getLogger("WebHelper");

	// 기본 인코딩 타입 설정
	private static final String ENC_TYPE = "UTF-8";

	/** 업로드 된 결과물이 저장될 폴더 */
	public static final String UPLOAD_DIR = "D:/BTM/workspace-jsp/upload";

	/** 업로드 가능한 최대 용량 */
	public static final int MAX_FILE_SIZE = 32 * 1024 * 1024;

	// 싱글톤 객체 생성 시작
	private static WebHelper current;

	// JSP의 내장 객체는 일반 Java 클래스가 생성 불가능
	// JSP 페이지로부터 request, response 객체를 수신
	public static WebHelper getInstance(HttpServletRequest request, HttpServletResponse response) {
		if (current == null) {
			current = new WebHelper();
		}
		// JSP 내장 객체를 연결하기 위한 메서드를 호출
		current.init(request, response);
		return current;
	}

	public static void freeInstance() {
		current = null;
	}

	private WebHelper() {
		super();
	}
	// 싱글톤 객체 생성 종료

	/** JSP의 request 내장 객체 */
	// import javax.servlet.http.HttpServletRequest;
	private HttpServletRequest request;

	// import javax.servlet.http.HttpServletResponse;
	private HttpServletResponse response;

	/** File 정보를 저장하기 위한 컬렉션타입의 객체 */
	private List<UploadItem> fileList;

	/** 그 밖의 일반 데이터를 저장하기 위한 컬렉션타입의 객체 */
	private Map<String, String> paramMap;

	/**
	 * JSP의 주요 내장 객체를 멤버변수에 연결
	 * 
	 * @param request
	 * @param response
	 */
	public void init(HttpServletRequest request, HttpServletResponse response) {
		// JSP 내장 객체 참조
		// getInstance()에 전달된 객체를 수신
		this.request = request;
		this.response = response;

		// 방식(GET, POST) 조회
		String methodName = request.getMethod();

		// 현재 URL을 획득
		String url = request.getRequestURL().toString();
		if (request.getQueryString() != null) {
			url = url + "?" + request.getQueryString();
		}

		// 획득한 정보를 로그로 표시
		logger.debug(String.format("[%s] %s", methodName, url));

		/** 내장객체 초기화, utf-8 설정 */
		try {
			this.request.setCharacterEncoding(ENC_TYPE);
			this.response.setCharacterEncoding(ENC_TYPE);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 메시지 표시 후, 페이지를 지정된 곳으로 이동
	 * 
	 * @param url - 이동할 페이지의 URL, Null일 경우 이전 페이지로 이동
	 * @param msg - 화면에 표시할 메시지, Null일 경우 메시지 출력 X
	 */
	public void redirect(String url, String msg) {

		// 획득한 정보를 로그로 표시
		logger.debug(String.format(" --> [redirect] %s >> %s", url, msg));

		// 가상의 View로 만들기 위한 HTML 태그를 구성
		String html = "<!doctype html>";
		html += "<html>";
		html += "<head>";
		html += "<meta charset='utf-8'>";

		// 메시지 표시
		if (msg != null) {
			html += "<script type='text/javascript'>alert('" + msg + "');</script>";
		}

		// 페이지 이동
		if (url != null) {
			html += "<meta http-equiv='refresh' content='0; url=" + url + "' />";
		} else {
			html += "<script type='text/javascript'>history.back();</script>";
		}

		html += "</head>";
		html += "<body></body>";
		html += "</html>";

		// 구성된 HTML을 출력
		try {
			PrintWriter out = this.response.getWriter();
			out.print(html);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 파라미터를 전달받아서 리턴
	 * 
	 * @param fieldName    - 파라미터 이름
	 * @param defaultValue - 값이 없을 경우에 사용할 기본값
	 * @return String
	 */
	public String getString(String fieldName, String defaultValue) {
		// 리턴을 위한 값을 2번째 파라미터(기본값)로 설정
		String result = defaultValue;

		// GET, POST 파라미터를 수신
		String param = this.request.getParameter(fieldName);

		// 값이 null이 아니면?
		if (param != null) {
			// 앞, 뒤 공백을 제거
			param = param.trim();
			// 공백 제거 결과가 빈 문자열이 아니라면?
			if (!param.equals("")) {
				// 리턴을 위해서 준비한 변수에 수신한 값을 복사
				result = param;
			}
		}

		logger.debug(String.format("(p) <-- %s = %s", fieldName, result));

		// 값을 리턴, param 값이 존재하지 않을 경우, 미리 준비한 기본값이 그대로 리턴
		return result;
	}

	/**
	 * 파라미터를 전달받아서 리턴, 값이 없을 경우 null을 리턴
	 * 
	 * @param fieldName - 파라미터 이름
	 * @return String
	 */
	public String getString(String fieldName) {
		return this.getString(fieldName, null);
	}

	/**
	 * 파라미터를 전달받아서 int로 형변환하여 리턴
	 * 
	 * @param fieldName    - 파라미터의 이름
	 * @param defaultValue - 값이 없을 경우 사용될 기본값
	 * @return int
	 */
	public int getInt(String fieldName, int defaultValue) {
		// 리턴을 위한 값을 2번째 파라미터(기본값)로 설정
		int result = defaultValue;

		// getString() 메소드를 통해서 파라미터를 문자열 형태로 수신
		// 파라미터가 존재하지 않을 경우 2번째로 전달한 값이 리턴
		String param = this.getString(fieldName, null);

		// 숫자형인 경우 숫자값으로 변환
		try {
			result = Integer.parseInt(param);
		} catch (Exception e) {
		}
		return result;
	}

	/**
	 * 파라미터를 전달받아서 int로 형변환해서 리턴 값이 없을 경우에는 0을 리턴
	 * 
	 * @param fieldName - 파라미터의 이름
	 * @return int
	 */
	public int getInt(String fieldName) {
		return this.getInt(fieldName, 0);
	}

	/**
	 * 배열 형태의 파라미터를 리턴, 체크박스 전용 기능
	 * 
	 * @param fieldName    - 파라미터의 이름
	 * @param defaultValue - 값이 없거나 배열의 길이가 0인 경우 사용될 기본값
	 * @return String[]
	 */
	public String[] getStringArray(String fieldName, String[] defaultValue) {
		// 리턴을 위한 값을 두 번째 파라미터(기본값)로 설정
		String[] result = defaultValue;

		// 배열 형태의 GET, POST 파라미터를 수신
		String[] param = this.request.getParameterValues(fieldName);

		// 수신된 파라미터가 존재한다면?
		if (param != null) {
			// 배열의 길이가 0보다 크다면?
			if (param.length > 0) {
				// 리턴을 위해서 준비한 변수에 수신한 값을 복사
				result = param;
			}
		}

		if (result != null) {
			logger.debug(String.format("(p) <-- %s = %s", fieldName, String.join(", ", result)));
		} else {
			logger.debug(String.format("(p) <-- %s = null", fieldName));
		}

		// 값을 리턴, param의 값이 존재하지 않을 경우 미리 준비한 기본값을 그대로 리턴
		return result;
	}

	/**
	 * 배열 형태의 파라미터를 리턴, 값이 없을 경우 null을 리턴
	 * 
	 * @param fieldName - 파라미터의 이름
	 * @return String[]
	 */
	public String[] getStringArray(String fieldName) {
		return this.getStringArray(fieldName, null);
	}

	/**
	 * 업로드 된 파일의 리스트를 리턴
	 * 
	 * @return List<UploadItem>
	 */
	public List<UploadItem> getFileList() {
		return this.fileList;
	}

	/**
	 * 업로드시에 함께 전달된 파라미터들의 컬렉션을 리턴
	 * 
	 * @return Map<String, String>
	 */
	public Map<String, String> getParamMap() {
		return this.paramMap;
	}

	/**
	 * Multipart로 전송된 데이터를 판별하여 파일 리스트와 텍스트 파라미터를 분류
	 * 
	 * @throws Exception
	 */
	public void upload() throws Exception {

		/** 폴더의 존재 여부를 체크해서 생성 */
		// import java.io.File
		File uploadDirFile = new File(UPLOAD_DIR);

		if (!uploadDirFile.exists()) {
			uploadDirFile.mkdirs();
		}

		/** 업로드가 수행될 폴더를 연결 */
		// import org.apache.commons.fileupload.disk.DiskFileItemFactory
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setRepository(uploadDirFile);

		/** 업로드 시작 */
		ServletFileUpload upload = new ServletFileUpload(factory);

		// UTF-8 처리 지정
		upload.setHeaderEncoding(ENC_TYPE);

		// 최대 파일 크기
		upload.setSizeMax(MAX_FILE_SIZE);

		// 실제 업로드를 수행하여 파일 및 파라미터들을 획득
		List<FileItem> items = upload.parseRequest(request);

		// items에 저장된 데이터를 분류하여 할당할 컬렉션 타입의 객체를 생성
		fileList = new ArrayList<UploadItem>();
		paramMap = new HashMap<String, String>();

		/** 업로드 된 컬렉션 타입의 데이터 수만큼 반복하면서 파일의 정보를 처리 */
		for (int i = 0; i < items.size(); i++) {

			// 전송된 정보 1개를 추출
			// import org.apache.commons.fileupload.FileItem
			FileItem f = items.get(i);

			if (f.isFormField()) {
				/** 파일 형식의 데이터가 아닌 경우 --> paramMap으로 정보를 분류 */
				String key = f.getFieldName(); // <input> 태그의 name 속성을 획득

				// value를 UTF-8 형식으로 획득
				String value = f.getString(ENC_TYPE); // 사용자의 입력값을 UTF-8 형식으로 획득

				// 이미 동일한 키값이 map안에 존재한다면?
				// checkbox의 경우, 동일한 키로 다양한 값을 전송
				if (paramMap.containsKey(key)) {

					// 기존에 존재하는 값 뒤에 콤마(,)를 추가해서 값을 병합
					value = paramMap.get(key) + "," + value;
					paramMap.put(key, value);
				} else {

					// 그렇지 않을 경우에는 키와 값을 신규로 추가
					paramMap.put(key, value);
				}
			} else {
				/** 파일 형식의 데이터인 경우 --> fileList로 정보를 분류 */
				/** 1) 파일의 정보를 추출 */
				String fieldName = f.getFieldName(); // <input type='file' />의 name 속성을 획득
				String orginName = f.getName(); // 파일의 원본 이름
				String contentType = f.getContentType(); // 파일의 형식
				long fileSize = f.getSize(); // 파일의 크기

				// 파일 사이즈가 없다면 조건문으로 리턴
				if (fileSize < 1) {
					continue;
				}

				// 파일이름에서 확장자(.파일형식)만 추출,
				String ext = orginName.substring(orginName.lastIndexOf("."));

				/** 2) 동일한 이름의 파일이 존재하는지 검사 */

				String fileName = null; // 웹 서버에 저장될 파일의 이름
				File uploadFile = null; // 저장된 파일의 정보를 담기 위한 File 객체
				String filePath = null; // 저장된 파일의 경로
				int count = 0; // 중복된 파일의 수

				// 중복검사를 위한 무한루프
				while (true) {

					// 파일의 이름을 "현재의 Timestamp(시각) + 카운트값 + 확장자(ext)"로 지정, 중복저장에 대비
					fileName = String.format("%d%d%s", System.currentTimeMillis(), count, ext);

					// 업로드 파일이 저장될 폴더 + 파일이름으로 파일객체를 생성
					uploadFile = new File(uploadDirFile, fileName);

					// 동일한 이름의 파일이 없을 경우 반복을 중단
					if (!uploadFile.exists()) {
						filePath = uploadFile.getAbsolutePath();
						break;
					}

					// 중복된 파일이 존재하는 경우에는 count값을 1증가
					count++;

				} // end while

				// 3) 최종적으로 구성된 파일객체를 사용해 파일을 보관용 폴더에 복사한 후 임시파일을 삭제
				f.write(uploadFile);
				f.delete();

				// 최종적으로 생성된 경로에서 업로드 폴더까지의 경로를 제거
				// 변경 전 : D:\BTM\workspace-jsp\]upload\15647312189240.jpg
				// 변경 후 : /15647312189240.jpg
				filePath = filePath.replace("\\", "/").replace(UPLOAD_DIR, "");

				/** 4) 파일 정보 분류 처리 */
				// 생성된 정보를 Beans의 객체로 설정해서 컬렉션에 저장
				// 이 정보로 추후 파일의 업로드 내역을 DB에 저장할 때 사용
				UploadItem info = new UploadItem();
				info.setFieldName(fieldName);
				info.setOrginName(orginName);
				info.setFilePath(filePath);
				info.setContentType(contentType);
				info.setFileSize(fileSize);

				fileList.add(info);
			} // end if
		} // end for

		// 획득한 정보를 로그로 기록
		for (UploadItem item : fileList) {
			logger.debug(String.format("(f) <-- %s", item.toString()));
		}

		for (String key : paramMap.keySet()) {
			logger.debug(String.format("(p) <-- %s = %s", key, paramMap.get(key)));
		}

	}

	/**
	 * Gson객체를 사용하여 JSON 데이터를 출력
	 *
	 * @param key
	 * @param value
	 */
	public void printJson(String rt, Map<String, Object> data) {

		this.response.setContentType("application/json");

		Calendar c = Calendar.getInstance();
		String pubDate = String.format("%04d-%02d-%02d %02d:%02d:%02d", c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1,
				c.get(Calendar.DAY_OF_MONTH), c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE),
				c.get(Calendar.SECOND));

		Map<String, Object> map = new HashMap<String, Object>();

		map.put("rt", rt);
		map.put("pubDate", pubDate);

		// data가 전달되었다면 map에 병합
		if (data != null) {
			map.putAll(data);
		}

		Gson gson = new Gson();
		String json = gson.toJson(map);

		try {
			PrintWriter out = this.response.getWriter();
			out.print(json);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
		}
	}

	/**
	 * Gson 객체를 사용하여 JSON 형식으로 결과 메시지를 출력
	 *
	 * @param key
	 * @param value
	 */
	public void printJson(Map<String, Object> data) {
		this.printJson("OK", data);
	}

	/**
	 * Gson 객체를 사용하여 JSON 형식으로 결과 메시지를 출력
	 *
	 * @param rt
	 */
	public void printJson(String rt) {
		this.printJson(rt, null);
	}

}
