package hikingclub.model1.mybatis.model;

/** 테이블 구조에 맞춘 Java Beans 생성 */
public class MountainImageInfo {

	public String imgFileName;

	public String imgName;

	public String getImgFileName() {
		return imgFileName;
	}

	public void setImgFileName(String imgFileName) {
		this.imgFileName = imgFileName;
	}

	public String getImgName() {
		return imgName;
	}

	public void setImgName(String imgName) {
		this.imgName = imgName;
	}

	@Override
	public String toString() {
		return "MountainImageInfo [imgFileName=" + imgFileName + ", imgName=" + imgName + "]";
	}

}
