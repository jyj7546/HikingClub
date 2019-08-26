package hikingclub.model1.mybatis.model;

/** 테이블 구조에 맞춘 Java Beans 생성 */
public class Mountain {

	private String mntiAdd;

	private String mntiName;

	private int mntiListNo;

	private String mntiAdmin;

	private String mntiAdminNum;

	private String mntiDetails;
	
	// 페이지 구현이 필요한 경우 아래 속성도 추가
	private static int offset;
	private static int listCount;

	public static int getOffset() {
		return offset;
	}

	public static void setOffset(int offset) {
		Mountain.offset = offset;
	}

	public static int getListCount() {
		return listCount;
	}

	public static void setListCount(int listCount) {
		Mountain.listCount = listCount;
	}

	public String getMntiAdd() {
		return mntiAdd;
	}

	public void setMntiAdd(String mntiAdd) {
		this.mntiAdd = mntiAdd;
	}

	public String getMntiName() {
		return mntiName;
	}

	public void setMntiName(String mntiName) {
		this.mntiName = mntiName;
	}

	public int getMntiListNo() {
		return mntiListNo;
	}

	public void setMntiListNo(int mntiListNo) {
		this.mntiListNo = mntiListNo;
	}

	public String getMntiAdmin() {
		return mntiAdmin;
	}

	public void setMntiAdmin(String mntiAdmin) {
		this.mntiAdmin = mntiAdmin;
	}

	public String getMntiAdminNum() {
		return mntiAdminNum;
	}

	public void setMntiAdminNum(String mntiAdminNum) {
		this.mntiAdminNum = mntiAdminNum;
	}

	public String getMntiDetails() {
		return mntiDetails;
	}

	public void setMntiDetails(String mntiDetails) {
		this.mntiDetails = mntiDetails;
	}

	@Override
	public String toString() {
		return "Mountain [mntiAdd=" + mntiAdd + ", mntiName=" + mntiName + ", mntiListNo=" + mntiListNo + ", mntiAdmin="
				+ mntiAdmin + ", mntiAdminNum=" + mntiAdminNum + ", mntiDetails=" + mntiDetails + "]";
	}

}
