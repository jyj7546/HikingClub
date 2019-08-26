package hikingclub.model1.helper;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URLEncoder;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.Thumbnails.Builder;
import net.coobird.thumbnailator.geometry.Positions;

public class DownloadHelper {

	/** Log4j 객체 */
	private static Logger logger = LoggerFactory.getLogger("DownloadHelper");

	// 싱글톤 객체 생성 시작
	private static DownloadHelper current;

	private DownloadHelper() {
		super();
	}

	public static DownloadHelper getInstance() {
		if (current == null) {
			current = new DownloadHelper();
		}
		return current;
	}

	public static void freeInstance() {
		current = null;
	}

	// 싱글톤 패턴 종료

	/**
	 * 지정된 경로의 파일을 읽어들이고 내용을 응답객체(response)를 사용해서 출력
	 * 
	 * @param response  - 응답객체
	 * @param filePath  - 서버상의 파일 경로
	 * @param orginName - 원본 파일의 이름
	 * @throws Exception
	 */
	public void download(HttpServletResponse response, String filePath, String orginName) throws Exception {
		logger.debug(String.format("[Download] filePath: %s", filePath));
		logger.debug(String.format("[Download] orginName: %s", orginName));

		/** 파일의 존재 여부를 확인하고 파일의 정보를 추출 */
		// import java.io.File;
		File f = new File(WebHelper.UPLOAD_DIR, filePath);
		if (!f.exists()) {
			logger.error("[Download] FileNotFoundException");
			// import java.io.FileNotFoundException;
			throw new FileNotFoundException(f.getAbsolutePath());
		}
		// 파일의 크기 추출
		long size = f.length();

		// 서버에 보관되어 있는 파일의 이름 추출
		String name = f.getName();

		// 원본 파일명이 전달되지 않은 경우, 서버에 보관된 파일의 이름으로 대체
		if (orginName == null) {
			orginName = name;
		}

		// 파일 형식 획득, 업로드 정보에서 추출했던 값 (contentType에 의하여 판정)
		// import javax.activation.MimetypesFileTypeMap;
		MimetypesFileTypeMap typeMap = new MimetypesFileTypeMap();
		String fileType = typeMap.getContentType(f);

		/** 웹 브라우저에게 이 메서드를 호출하는 페이지의 형식을 일반 파일로 인식시키기 위한 처리 */
		// 다른 데이터와 섞이지 않도록 응답객체를 리셋 (response)
		// 초기화하지 않을 경우, 파일이 깨지는 현상 발생
		response.reset();

		// 파일 형식의 정보를 설정
		response.setHeader("Content-Type", fileType + "; charset=UTF-8");

		// 파일의 이름 설정, 인코딩이 반드시 필요
		// import java.net.URLEncoder;
		String encFileName = URLEncoder.encode(orginName, "UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=" + encFileName + ";");

		// 파일의 용량 설정
		response.setContentLength((int) size);

		/** 스트림을 통한 파일의 바이너리 읽기 */
		// 파일 읽기와 출력을 위한 스트림을 생성
		// import java.io.InputStream;
		InputStream is = new FileInputStream(f);

		// is는 한번에 내용을 읽어야 하지만, BufferedInputStream을 사용하면 나눠서 읽기가 가능
		// 대용량 파일 처리는 BufferedInputStream 클래스를 통해 데이터를 나눠서 처리
		// import java.io.BufferedInputStream;
		BufferedInputStream bis = new BufferedInputStream(is);

		// BufferedInputStream을 통해 읽어들인 데이터를 출력하기 위해 사용되는 클래스
		// import java.io.BufferedOutputStream;
		BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());

		// 업로드 된 파일의 용량에 상관없는 크기의 배열 공간을 생성[1KByte]
		byte[] buffer = new byte[1024];

		// 버퍼링이 수행되는 동안 읽어들인 데이터의 크기를 체크하기 위한 변수
		int length = 0;

		// bis.read() 메서드는 파라미터로 전달된 배열의 길이만큼 데이터를 담고, 읽어들인 용량을 리턴
		// 읽어들인 내용이 없을 경우에는 '-1'을 리턴
		// ex) 2000byte를 읽어야 할 경우
		// 1번째 수행시 버퍼링이 배열은 1024까지 저장
		// 2번째 수행시 버퍼링이 배열은 976까지만 저장
		// 3번째 수행시 버퍼링이 종료, '-1'을 리턴

		// ٘ 배열을 이용한 버퍼링 처리
		while ((length = bis.read(buffer)) != -1) {
			// buffer의 내용을 지정한 위치부터의 길이만큼 읽어들인 Byte를 버퍼링을 위한 스트림으로 옮겨 담기(bos)
			bos.write(buffer, 0, length);
		}

		// 옮겨담은 내용을 웹 브라우저에 전송
		bos.flush();

		// 사용한 스트림 종료
		bos.close();
		bis.close();
		is.close();
	}

	/**
	 * 원본 파일의 경로와 함께 이미지의 가로, 세로 크기를 전달할 경우
	 * 지정된 크기로 썸네일 이미지를 생성하고, 생성된 썸네일을 출력
	 * @param response - 응답객체
	 * @param filePath - 원본 이미지의 경로
	 * @param width - 가로 크기
	 * @param height - 세로 크기
	 * @param crop - 이미지 크롭 사용 여부
	 * @throws Exception
	 */
	public void download(HttpServletResponse response, String filePath, int width, int height, boolean crop)
			throws Exception {
		// 썸네일을 생성하고 경로를 리턴
		String thumbPath = this.createThumbnail(filePath, width, height, crop);
		
		// 썸네일을 출력
		// 이 메서드를 호출하기 위해서 try~catch가 요구되지만, 현재 메서드 역시 throws를 명시하여 예외처리가 현재 메서드를 호출하는 곳으로 이관
		this.download(response, thumbPath, null);
	}

	/**
	 * 리사이즈된 썸네일 이미지를 생성
	 * 가로를 기준으로 사이즈를 변경, 세로는 자동으로 설정하여 이미지를 잘라내기
	 * @param path - 원본 파일의 경로
	 * @param width - 최대 이미지 가로 크기
	 * @param height - 최대 이미지 세로 크기
	 * @param crop - 이미지 크롭 사용 여부
	 * @return 생성된 이미지의 절대 경로
	 * @throws Exception
	 */
	public String createThumbnail(String path, int width, int height, boolean crop) throws Exception {
		logger.debug(String.format("[Thumbnail] path: %s", path));
		logger.debug(String.format("[Thumbnail] width: %s", width));
		logger.debug(String.format("[Thumbnail] height: %s", height));
		logger.debug(String.format("[Thumbnail] crop: %s", crop));
		
		// 생성된 썸네일 이미지의 경로
		String saveFile = null;
		
		// 파일명에서 저장될 파일 경로를 생성
		File loadFile = new File(WebHelper.UPLOAD_DIR, path);
		String dirPath = loadFile.getParent();
		String fileName = loadFile.getName();
		
		// 원본 파일이름에서 이름과 확장자를 분리
		int p = fileName.lastIndexOf(".");
		String name = fileName.substring(0, p);
		String ext = fileName.substring(p + 1);
		
		// 원본 이름에 요청된 사이즈를 덧붙여서 생성될 파일명을 구성
		// ex) myphoto.jpg --> myphoto_resize_320x240.jpg
		String prefix = "_resize_";
		if (crop) {
			prefix = "_crop_";
		}
		String thumbName = name + prefix + width + "x" + height + "." + ext;
		File f = new File(dirPath, thumbName);
		
		// 절대 경로를 추출
		saveFile = f.getAbsolutePath();
		
		// 해당 경로에 이미지가 없는 경우만 수행
		if (!f.exists()) {
			
			// 원본 이미지 가져오기
			// --> import net.coobird.thumbnailator.Thumbnails;
			// --> import net.coobird.thumbnailator.Thumbnails.Builder;
			Builder<File> builder = Thumbnails.of(loadFile);
			
			// 이미지 크롭 여부
			if (crop == true) {
				builder.crop(Positions.CENTER);
			}
			
			// 축소할 사이즈 지정
			builder.size(width, height);
			
			// 세로로 촬영된 사진을 회전 -> 가로로 변경
			builder.useExifOrientation(true);
			
			// 파일의 확장명 지정
			builder.outputFormat(ext);
			
			// 저장할 파일이름 지정
			builder.toFile(saveFile);
		}
		// 최종적으로 생성된 경로에서 업로드 폴더까지의 경로를 제거
		saveFile = saveFile.replace("\\", "/").replace(WebHelper.UPLOAD_DIR, "");
		logger.debug(String.format("[Thumbnail] saveFile: %s", saveFile));
		return saveFile;
	}

}
