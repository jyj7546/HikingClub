package study.java.service.impl;

import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.Logger;

import study.java.model.Movie;
import study.java.service.MovieRankService;

public class MovieRankServiceImpl implements MovieRankService {
	
	SqlSession sqlSession;
	Logger logger;
	
	public MovieRankServiceImpl(SqlSession sqlSession, Logger logger) {
		this.sqlSession = sqlSession;
		this.logger = logger;
	}

	@Override
	public void addItem(Movie movie) throws Exception {		
		try {
			int cnt = sqlSession.update("MovieMapper.edit_item", movie);
			
			if (cnt == 0) {
				cnt = sqlSession.insert("MovieMapper.add_item", movie);
				
				if (cnt == 0) {
					throw new NullPointerException();
				}
			}
		} catch (NullPointerException e) {
			sqlSession.rollback();
			logger.error(e);
			throw new Exception("저장된 데이터가 없습니다.");
		} catch (Exception e) {
			sqlSession.rollback();
			logger.error(e);
			throw new Exception("데이터 저장에 실패했습니다.");
		} finally {
			sqlSession.commit();
		}
	}

	@Override
	public Movie getItem(Movie movie) throws Exception {
		Movie item = null;
		
		try {
			item = sqlSession.selectOne("MovieMapper.get_item", movie);
			
			if (item == null) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			logger.error(e);
			throw new Exception("저장된 데이터가 없습니다.");
		} catch (Exception e) {
			logger.error(e);
			throw new Exception("데이터 저장에 실패했습니다.");
		}
		
		return item;
	}
}
