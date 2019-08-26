package study.java.dao.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import study.java.dao.MovieRankDao;
import study.java.helper.HttpHelper;
import study.java.helper.JsonHelper;
import study.java.model.Movie;

public class MovieRankDaoImpl implements MovieRankDao {

	@Override
	public List<Movie> getMovieRank(String targetDt) {
		List<Movie> list = null;
		
		String apiUrl = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json?key=%s&targetDt=%s";
		String apiKey = "17047c46b9a1af11f6c9c78dec575693";
		
		String url = String.format(apiUrl, apiKey, targetDt);
		
		InputStream is = HttpHelper.getInstance().getWebData(url, "utf-8", null);		
		JSONObject json = JsonHelper.getInstance().getJSONObject(is, "utf-8");
		
		if (json == null) {
			return null;
		}
		
		JSONObject boxOfficeResult = json.getJSONObject("boxOfficeResult");
		JSONArray dailyBoxOfficeList = boxOfficeResult.getJSONArray("dailyBoxOfficeList");
		list = new ArrayList<Movie>();
		
		for (int i=0; i<dailyBoxOfficeList.length(); i++) {
			JSONObject item = dailyBoxOfficeList.getJSONObject(i);
			
			Movie movie = new Movie();
			
			movie.setSearchDate(targetDt);
			movie.setRank(item.getInt("rank"));
			movie.setRankInten(item.getInt("rankInten"));
			movie.setRankOldAndNew(item.getString("rankOldAndNew"));
			movie.setMovieCd(item.getString("movieCd"));
			movie.setMovieNm(item.getString("movieNm"));
			movie.setOpenDt(item.getString("openDt"));
			movie.setSalesAmt(item.getLong("salesAmt"));
			movie.setSalesShare(item.getDouble("salesShare"));
			movie.setSalesInten(item.getLong("salesInten"));
			movie.setSalesChange(item.getDouble("salesChange"));
			movie.setSalesAcc(item.getLong("salesAcc"));
			movie.setAudiCnt(item.getInt("audiCnt"));
			movie.setAudiInten(item.getLong("audiInten"));
			movie.setAudiChange(item.getDouble("audiChange"));
			movie.setAudiAcc(item.getInt("audiAcc"));
			movie.setScrnCnt(item.getInt("scrnCnt"));
			movie.setShowCnt(item.getInt("showCnt"));
			
			list.add(movie);
		}
		
		return list;
	}

}
