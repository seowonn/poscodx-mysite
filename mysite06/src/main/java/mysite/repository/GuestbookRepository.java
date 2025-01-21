package mysite.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import mysite.vo.GuestbookVo;

@Repository
public class GuestbookRepository {
	
	private SqlSession sqlSession;
	
	// applicationContext.xml에 등록해 놓은 Bean을 주입해줌
	public GuestbookRepository(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	public GuestbookVo findById(Long id) {
		return sqlSession.selectOne("guestbook.findById", id);
	}

	public List<GuestbookVo> findAll() {
		return sqlSession.selectList("guestbook.findAll");		
	}

	public int insert(GuestbookVo vo) {
		return sqlSession.insert("guestbook.insert", vo);
	}
	
	public int deleteByIdAndPassword(Long id, String password) {
		return sqlSession.delete("guestbook.deleteByIdAndPassword", Map.of("id", id, "password", password));
	}

}
