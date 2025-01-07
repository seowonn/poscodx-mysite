package mysite.repository;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import mysite.vo.UserVo;

@Repository
public class UserRepository {
	
	private SqlSession sqlSession;
	
	public UserRepository(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	public void insert(UserVo vo) {
		sqlSession.insert("user.insert", vo);
	}

	public UserVo findByEmailAndPassword(String email, String password) {		
		UserVo userVo = sqlSession.selectOne("user.findByEmailAndPassword", Map.of("email", email, "password", password));		
		return userVo;
	}

	public UserVo findById(Long id) {
		return sqlSession.selectOne("user.findById", id);
	}

	public void update(UserVo vo) {
		sqlSession.update("user.update", vo);
	}

}
