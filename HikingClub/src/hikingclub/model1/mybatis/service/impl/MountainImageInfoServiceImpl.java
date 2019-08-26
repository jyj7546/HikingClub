package hikingclub.model1.mybatis.service.impl;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hikingclub.model1.mybatis.model.MountainImageInfo;
import hikingclub.model1.mybatis.service.MountainImageInfoService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MountainImageInfoServiceImpl implements MountainImageInfoService {

	/** Log4j 객체 생성 */
	private static Logger log = LoggerFactory.getLogger("MountainImageInfoServiceImpl");
	
	/** MyBatis */
	// --> import org.apache.ibatis.session.SqlSession
	SqlSession sqlSession;

	/** 생성자를 통한 객체 생성 */
	public MountainImageInfoServiceImpl(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	@Override
	public MountainImageInfo getMountainImageInfoItem(MountainImageInfo input) throws Exception {
		MountainImageInfo result = null;

		try {
			result = sqlSession.selectOne("MountainImageMapper.selectItem", input);

			if (result == null) {
				throw new NullPointerException("result=null");
			}
		} catch (NullPointerException e) {
			sqlSession.rollback();
			log.error(e.getLocalizedMessage());
			throw new Exception("조회된 데이터가 없습니다.");
		} catch (Exception e) {
			sqlSession.rollback();
			log.error(e.getLocalizedMessage());
			throw new Exception("데이터 조회에 실패했습니다.");
		} finally {
			sqlSession.commit();
		}

		return result;
	}

	@Override
	public List<MountainImageInfo> getMountainImageInfoList(MountainImageInfo input) throws Exception {
		List<MountainImageInfo> result = null;

		try {
			result = sqlSession.selectList("MountainImageMapper.selectList", input);

			if (result == null) {
				throw new NullPointerException("result=null");
			}
		} catch (NullPointerException e) {
			sqlSession.rollback();
			log.error(e.getLocalizedMessage());
			throw new Exception("조회된 데이터가 없습니다.");
		} catch (Exception e) {
			sqlSession.rollback();
			log.error(e.getLocalizedMessage());
			throw new Exception("데이터 조회에 실패했습니다.");
		} finally {
			sqlSession.commit();
		}

		return result;
	}

	@Override
	public int getMountainImageInfoCount(MountainImageInfo input) throws Exception {
		int result = 0;

		try {
			result = sqlSession.selectOne("MountainImageMapper.selectCount", input);
		} catch (Exception e) {
			sqlSession.rollback();
			log.error(e.getLocalizedMessage());
			throw new Exception("데이터 조회에 실패했습니다.");
		} finally {
			sqlSession.commit();
		}

		return result;
	}

	@Override
	public int addMountainImageInfo(MountainImageInfo input) throws Exception {
		int result = 0;

		try {
			result = sqlSession.insert("MountainImageMapper.insertItem", input);

			if (result == 0) {
				throw new NullPointerException("result=0");
			}
		} catch (NullPointerException e) {
			sqlSession.rollback();
			log.error(e.getLocalizedMessage());
			throw new Exception("저장된 데이터가 없습니다.");
		} catch (Exception e) {
			sqlSession.rollback();
			log.error(e.getLocalizedMessage());
			throw new Exception("데이터 저장에 실패했습니다.");
		} finally {
			sqlSession.commit();
		}

		return result;
	}

	@Override
	public int editMountainImageInfo(MountainImageInfo input) throws Exception {
		int result = 0;

		try {
			result = sqlSession.update("MountainImageMapper.updateItem", input);

			if (result == 0) {
				throw new NullPointerException("result=0");
			}
		} catch (NullPointerException e) {
			sqlSession.rollback();
			log.error(e.getLocalizedMessage());
			throw new Exception("수정된 데이터가 없습니다.");
		} catch (Exception e) {
			sqlSession.rollback();
			log.error(e.getLocalizedMessage());
			throw new Exception("데이터 수정에 실패했습니다.");
		} finally {
			sqlSession.commit();
		}

		return result;
	}

	@Override
	public int deleteMountainImageInfo(MountainImageInfo input) throws Exception {
		int result = 0;

		try {
			result = sqlSession.delete("MountainImageMapper.deleteItem", input);
			if (result == 0) {
				throw new NullPointerException("result=0");
			}
		} catch (NullPointerException e) {
			sqlSession.rollback();
			log.error(e.getLocalizedMessage());
			throw new Exception("삭제된 데이터가 없습니다.");
		} catch (Exception e) {
			sqlSession.rollback();
			log.error(e.getLocalizedMessage());
			throw new Exception("데이터 삭제에 실패했습니다.");
		} finally {
			sqlSession.commit();
		}

		return result;
	}
}