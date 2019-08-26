package study.java.model;

public class Movie {
	private int id; // DB의 PK
	private String searchDate; // 검색일
	private int rank;
	private int rankInten;
	private String rankOldAndNew;
	private String movieCd;
	private String movieNm;
	private String openDt;
	private long salesAmt;
	private double salesShare;
	private long salesInten;
	private double salesChange;
	private long salesAcc;
	private int audiCnt;
	private long audiInten;
	private double audiChange;
	private int audiAcc;
	private int scrnCnt;
	private int showCnt;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSearchDate() {
		return searchDate;
	}

	public void setSearchDate(String searchDate) {
		this.searchDate = searchDate;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public int getRankInten() {
		return rankInten;
	}

	public void setRankInten(int rankInten) {
		this.rankInten = rankInten;
	}

	public String getRankOldAndNew() {
		return rankOldAndNew;
	}

	public void setRankOldAndNew(String rankOldAndNew) {
		this.rankOldAndNew = rankOldAndNew;
	}

	public String getMovieCd() {
		return movieCd;
	}

	public void setMovieCd(String movieCd) {
		this.movieCd = movieCd;
	}

	public String getMovieNm() {
		return movieNm;
	}

	public void setMovieNm(String movieNm) {
		this.movieNm = movieNm;
	}

	public String getOpenDt() {
		return openDt;
	}

	public void setOpenDt(String openDt) {
		this.openDt = openDt;
	}

	public long getSalesAmt() {
		return salesAmt;
	}

	public void setSalesAmt(long salesAmt) {
		this.salesAmt = salesAmt;
	}

	public double getSalesShare() {
		return salesShare;
	}

	public void setSalesShare(double salesShare) {
		this.salesShare = salesShare;
	}

	public long getSalesInten() {
		return salesInten;
	}

	public void setSalesInten(long salesInten) {
		this.salesInten = salesInten;
	}

	public double getSalesChange() {
		return salesChange;
	}

	public void setSalesChange(double salesChange) {
		this.salesChange = salesChange;
	}

	public long getSalesAcc() {
		return salesAcc;
	}

	public void setSalesAcc(long salesAcc) {
		this.salesAcc = salesAcc;
	}

	public int getAudiCnt() {
		return audiCnt;
	}

	public void setAudiCnt(int audiCnt) {
		this.audiCnt = audiCnt;
	}

	public long getAudiInten() {
		return audiInten;
	}

	public void setAudiInten(long audiInten) {
		this.audiInten = audiInten;
	}

	public double getAudiChange() {
		return audiChange;
	}

	public void setAudiChange(double audiChange) {
		this.audiChange = audiChange;
	}

	public int getAudiAcc() {
		return audiAcc;
	}

	public void setAudiAcc(int audiAcc) {
		this.audiAcc = audiAcc;
	}

	public int getScrnCnt() {
		return scrnCnt;
	}

	public void setScrnCnt(int scrnCnt) {
		this.scrnCnt = scrnCnt;
	}

	public int getShowCnt() {
		return showCnt;
	}

	public void setShowCnt(int showCnt) {
		this.showCnt = showCnt;
	}

	@Override
	public String toString() {
		return "Movie [id=" + id + ", searchDate=" + searchDate + ", rank=" + rank + ", rankInten=" + rankInten + ", rankOldAndNew=" + rankOldAndNew + ", movieCd=" + movieCd + ", movieNm=" + movieNm + ", openDt=" + openDt + ", salesAmt=" + salesAmt + ", salesShare=" + salesShare + ", salesInten=" + salesInten + ", salesChange=" + salesChange + ", salesAcc=" + salesAcc + ", audiCnt=" + audiCnt + ", audiInten=" + audiInten + ", audiChange=" + audiChange + ", audiAcc=" + audiAcc + ", scrnCnt=" + scrnCnt + ", showCnt=" + showCnt + "]";
	}

}
