package hikingclub.model1.helper;

/**
 * 업로드 된 파일의 정보를 저장하기 위한 JavaBeans - 이 클래스의 객체가 업로드 된 파일의 수만큼 생성되어 List의 형태로 보관
 */
public class UploadItem {

	private String fieldName; // <input type="file">의 name 속성
	private String orginName; // 원본 파일 이름
	private String filePath; // 서버상의 파일 경로
	private String contentType; // 파일의 형식
	private long fileSize; // 파일의 용량

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getOrginName() {
		return orginName;
	}

	public void setOrginName(String orginName) {
		this.orginName = orginName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	@Override
	public String toString() {
		return "UploadItem [fieldName=" + fieldName + ", orginName=" + orginName + ", filePath=" + filePath
				+ ", contentType=" + contentType + ", fileSize=" + fileSize + "]";
	}

}
