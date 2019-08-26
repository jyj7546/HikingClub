import java.util.Calendar;
import java.util.List;

import study.java.dao.MovieRankDao;
import study.java.dao.impl.MovieRankDaoImpl;
import study.java.model.Movie;

public class MovieMain2 {

	public static void main(String[] args) {
		/** DAO객체 */
		MovieRankDao movieRankDao = new MovieRankDaoImpl();
		
		/** 어제부터 1주일간의 영화 순위 데이터 얻기 */
		Calendar calendar = Calendar.getInstance();
		
		for (int i=1; i <= 30; i++) {		
			calendar.add(Calendar.DAY_OF_MONTH, -1);
			// 영화진흥원에 보낼 날짜값 
			String targetDt = String.format("%04d%02d%02d", 
					calendar.get(Calendar.YEAR),
					calendar.get(Calendar.MONTH)+1,
					calendar.get(Calendar.DAY_OF_MONTH));
			
			System.out.println("[" + targetDt + "]");
			System.out.println("------------------------------------------");
			
			List<Movie> list = movieRankDao.getMovieRank(targetDt);
			if (list != null) {
				for (int j=0; j<list.size(); j++) {
					Movie movie = list.get(j);
					System.out.println(movie.toString());
				}
				System.out.println("\n\n");
			}
		}
	}

}
