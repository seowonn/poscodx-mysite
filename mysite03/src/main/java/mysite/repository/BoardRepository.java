package mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

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
//		try (Connection conn = dataSource.getConnection();
//				PreparedStatement pstmt = conn.prepareStatement("update board "
//						+ "set title=?, contents=? where id = ?");
//		) {
//			pstmt.setString(1, vo.getTitle());
//			pstmt.setString(2, vo.getContents());
//			pstmt.setLong(3, vo.getId());
//			int count = pstmt.executeUpdate();
//
//			if (count == 0) {
//				System.out.println("update failed. No rows affected.");
//			}
//
//		} catch (SQLException e) {
//			System.out.println("error: " + e);
//		}
	}
	
	public List<BoardVo> findByKeyword(String keyword, int pageSize, int offset) {
		return sqlSession.selectList("board.findByKeyword", Map.of("keyword", keyword, "limit", pageSize, "offset", offset));
	}
	
	public int countByKeyword(String keyword) {
		return sqlSession.selectOne("board.countByKeyword", keyword);
	}

}
