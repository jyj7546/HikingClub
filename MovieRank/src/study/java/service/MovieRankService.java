package study.java.service;

import study.java.model.Movie;

public interface MovieRankService {
	/**
	 * 영화 순위 데이터 한 건을 DB에 저장한다.
	 * 단, 검색일(searchDate)와 영화제목(movieNm)이 일치하는 데이터가 있을 경우
	 * 추가가 아닌 수정으로 동작해야 한다.
	 * @param movie
	 * @throws Exception
	 */
	public void addItem(Movie movie) throws Exception;
	
	public Movie getItem(Movie movie) throws Exception;
}
