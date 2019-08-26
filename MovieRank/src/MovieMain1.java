import java.util.Calendar;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import study.java.dao.MovieRankDao;
import study.java.dao.MyBatisConnectionFactory;
import study.java.dao.impl.MovieRankDaoImpl;
import study.java.model.Movie;
import study.java.service.MovieRankService;
import study.java.service.impl.MovieRankServiceImpl;

public class MovieMain1 {
	
	public static void main(String[] args) {
		// DAO객체
		MovieRankDao movieRankDao = new MovieRankDaoImpl();
		
		// 캘린더 객체를 사용하여 1일 전 날짜 얻기
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		
		// 영화진흥원에 보낼 날짜값을 yyyymmdd 형식으로 만들기 
		String targetDt = String.format("%04d%02d%02d", 
										calendar.get(Calendar.YEAR),
										calendar.get(Calendar.MONTH)+1,
										calendar.get(Calendar.DAY_OF_MONTH) );
		
		// JSON 연동
		List<Movie> list = movieRankDao.getMovieRank(targetDt);
		
		// DB에 저장하기 위한 Service객체 생성
		SqlSession sqlSession = MyBatisConnectionFactory.getSqlSession();
		Logger logger = LogManager.getFormatterLogger(sqlSession);
		MovieRankService movieRankService = new MovieRankServiceImpl(sqlSession, logger);
		
		if (list != null) {
			for (int i=0; i<list.size(); i++) {
				Movie movie = list.get(i);
				//System.out.println(movie);
				//logger.debug(movie.toString());
				
				try {
					movieRankService.addItem(movie);
					Movie item = movieRankService.getItem(movie);
					System.out.println(item);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
