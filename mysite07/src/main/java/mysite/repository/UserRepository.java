package mysite.repository;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;

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
		return sqlSession.selectOne("user.findByEmailAndPassword",
				Map.of("email", email, "password", password));
	}

	/**
	 * 반환형 <R>을 사용함으로써 호출자가 원하는 반환 타입을 지정할 수 있다. 
	 * map의 key는 컬럼명, value는 데이터이다.
	 * ObjectMapper().convertValue을 통해 map의 value값을 원하는 객체로 반환할 수 있다.
	 */
	public <R> R findByEmail(String email, Class<R> resultType) {
		Map<String, Object> map = sqlSession.selectOne("user.findByEmail", email);
		return new ObjectMapper().convertValue(map, resultType);
	}

	public UserVo findById(Long id) {
		return sqlSession.selectOne("user.findById", id);
	}

	public void update(UserVo vo) {
		sqlSession.update("user.update", vo);
	}

}
