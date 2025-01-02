package mysite.repository;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import mysite.vo.GuestbookVo;

@Repository
public class GuestbookRepository {
	
	@Autowired
	private DataSource dataSource;
	
	private SqlSession sqlSession;
	
	// applicationContext.xml에 등록해 놓은 Bean을 주입해줌
	public GuestbookRepository(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
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
