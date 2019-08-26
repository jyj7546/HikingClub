package study.java.dao;

import java.util.List;

import study.java.model.Movie;

public interface MovieRankDao {
	/**
	 * 영화진흥원 API로부터 특정 날짜에 대한 박스오피스 순위를 조회하여 리턴한다.
	 * @param targetDt - 검색일 (yyyymmdd)
 	 * @return 검색결과
	 */
	public List<Movie> getMovieRank(String targetDt);
	
}
