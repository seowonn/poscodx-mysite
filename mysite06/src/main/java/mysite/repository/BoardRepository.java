package mysite.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import mysite.vo.BoardVo;

@Repository
public class BoardRepository {
	
	private SqlSession sqlSession;
	
	public BoardRepository(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	public void insert(BoardVo vo) {
		sqlSession.insert("board.insert", vo);
	}

	public void deleteByIdAndUserId(Long id, Long userId) {
		sqlSession.delete("board.deleteByIdAndUserId", Map.of("id", id, "userId", userId));
	}

	public BoardVo findById(Long id) {
		sqlSession.update("board.increaseHit", id);
		return sqlSession.selectOne("board.findById", id);
	}

	public void update(BoardVo vo) {
		sqlSession.update("board.updateBoard", vo);
	}
	
	public List<BoardVo> findByKeyword(String keyword, int pageSize, int offset) {
		return sqlSession.selectList("board.findByKeyword", Map.of("keyword", keyword, "limit", pageSize, "offset", offset));
	}
	
	public int countByKeyword(String keyword) {
		return sqlSession.selectOne("board.countByKeyword", keyword);
	}

}
