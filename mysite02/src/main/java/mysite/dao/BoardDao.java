package mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mysite.vo.BoardVo;
import mysite.vo.ParentBoardVo;
import mysite.vo.ReplyBoardVo;

public class BoardDao {
	
	private final String SERVER_IP = "192.168.0.17";

	public void insert(BoardVo vo) {
		
		String selectQuery = "SELECT COALESCE(MAX(g_no), 0) + 1 FROM board";
		String insertQuery = "INSERT INTO board (title, contents, hit, reg_date, g_no, o_no, depth, user_id) "
				+ "VALUES(?, ?, 0, NOW(), ?, 1, 0, ?)";
		
		try (Connection conn = getConnection()) {
			Long nextGno = 1L;
			
			try(
				PreparedStatement pstmt = conn.prepareStatement(selectQuery);
				ResultSet rs = pstmt.executeQuery()
			) {
				if(rs.next()) {
					nextGno = rs.getLong(1);
				}
			}
			
			try(PreparedStatement pstmt = conn.prepareStatement(insertQuery)){				
				pstmt.setString(1, vo.getTitle());
				pstmt.setString(2, vo.getContents());
				pstmt.setLong(3, nextGno);
				pstmt.setLong(4, vo.getUserId());

				int count = pstmt.executeUpdate();
				if (count == 0) {
					System.out.println("Insert failed. No rows affected.");
				}
			}

		} catch (SQLException e) {
			System.out.println("error: " + e);
		}
	}

	public void deleteById(Long id) {
		try (Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement("delete from board where id = ?");) {
			pstmt.setLong(1, id);
			int count = pstmt.executeUpdate();

			if (count == 0) {
				System.out.println("delete failed. No rows affected.");
			}

		} catch (SQLException e) {
			System.out.println("error: " + e);
		}
	}

	public BoardVo findById(Long id) {
		
		String updateQuery = "UPDATE board SET hit = hit + 1 WHERE id = ?";
		String seleectQuery = "SELECT title, contents, hit, reg_date, g_no, o_no, depth, user_id FROM board WHERE id = ?";
		BoardVo result = null;
		
		try (Connection conn = getConnection()) {
			
			try(PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {
				pstmt.setLong(1, id);
				pstmt.executeUpdate();
			}
			
			try(PreparedStatement pstmt = conn.prepareStatement(seleectQuery)){				
				pstmt.setLong(1, id);

				ResultSet rs = pstmt.executeQuery();
				if (rs.next()) {
					String title = rs.getString(1);
					String contents = rs.getString(2);
					int hit = rs.getInt(3);
					String regDate = rs.getString(4);
					int gNo = rs.getInt(5);
					int oNo = rs.getInt(6);
					int depth = rs.getInt(7);
					Long userId = rs.getLong(8);

					BoardVo vo = new BoardVo();
					vo.setId(id);
					vo.setTitle(title);
					vo.setContents(contents);
					vo.setHit(hit);

					vo.setRegDate(regDate);
					vo.setgNo(gNo);
					vo.setoNo(oNo);
					vo.setDepth(depth);
					vo.setUserId(userId);

					result = vo;
				}
				rs.close();
			}

		} catch (SQLException e) {
			System.out.println("error: " + e);
		}
		return result;
	}

	public void update(BoardVo vo) {
		try (Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement("update board "
						+ "set title=?, contents=? where id = ?");
		) {
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setLong(3, vo.getId());
			int count = pstmt.executeUpdate();

			if (count == 0) {
				System.out.println("update failed. No rows affected.");
			}

		} catch (SQLException e) {
			System.out.println("error: " + e);
		}
	}

	public ParentBoardVo getParentBoardInfo(Long id) {
		ParentBoardVo result = new ParentBoardVo();

		try (Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT g_no, o_no, depth "
						+ "FROM board WHERE id = ?");
		) {
			pstmt.setLong(1, id);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				result.setgNo(rs.getInt(1));
				result.setoNo(rs.getInt(2));
				result.setDepth(rs.getInt(3));
			}
			rs.close();
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}
		return result;
	}
	
	public void insertBoardReply(ReplyBoardVo vo) {
		
		String updateQuery = "UPDATE board SET o_no = o_no + 1 WHERE g_no = ? AND o_no >= ?";
		String insertQuery = "INSERT INTO board (title, contents, hit, reg_date, g_no, o_no, depth, user_id) "
				+ "VALUES(?, ?, 0, NOW(), ?, ?, ?, ?)";
		
		try (Connection conn = getConnection()) {
			
			try(PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {
				pstmt.setInt(1, vo.getgNo());
				pstmt.setInt(2, vo.getoNo() + 1);
				pstmt.executeUpdate();
			}
			
			try(PreparedStatement pstmt = conn.prepareStatement(insertQuery)){				
				pstmt.setString(1, vo.getTitle());
				pstmt.setString(2, vo.getContents());
				pstmt.setInt(3, vo.getgNo());
				pstmt.setInt(4, vo.getoNo() + 1);
				pstmt.setInt(5, vo.getDepth() + 1);
				pstmt.setLong(6, vo.getUserId());

				int count = pstmt.executeUpdate();
				if (count == 0) {
					System.out.println("Insert failed. No rows affected.");
				}
			}

		} catch (SQLException e) {
			System.out.println("error: " + e);
		}
	}
	
	public List<BoardVo> findByKeyword(String keyword, int pageSize, int offset) {
	    List<BoardVo> result = new ArrayList<>();
	    
	    String query = "SELECT b.id, b.title, b.contents, b.hit, b.reg_date, "
					+ "b.g_no, b.o_no, b.depth, a.name, a.id " 
					+ "FROM board b "
					+ "JOIN user a ON a.id = b.user_id ";
	    
	    if(keyword != null && keyword.length() != 0) {
	    	query += "WHERE b.title LIKE ? OR b.contents LIKE ? ";
	    }
	    
	    query += "ORDER BY b.g_no DESC, b.o_no LIMIT ? OFFSET ?";

	    try (Connection conn = getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(query)) {
	    	
	    	int index = 1;
	    	if(keyword != null && keyword.length() != 0) {	    		
	    		pstmt.setString(index++, "%" + keyword + "%");
	    		pstmt.setString(index++, "%" + keyword + "%");
	    	}
	        pstmt.setInt(index++, pageSize);
	        pstmt.setInt(index++, offset);

	        try (ResultSet rs = pstmt.executeQuery()) {
	            while (rs.next()) {
	                BoardVo vo = new BoardVo();
	                vo.setId(rs.getLong(1));
	                vo.setTitle(rs.getString(2));
	                vo.setContents(rs.getString(3));
	                vo.setHit(rs.getInt(4));
	                vo.setRegDate(rs.getString(5));
	                vo.setgNo(rs.getInt(6));
	                vo.setoNo(rs.getInt(7));
	                vo.setDepth(rs.getInt(8));
	                vo.setUserName(rs.getString(9));
	                vo.setUserId(rs.getLong(10));
	                result.add(vo);
	            }
	        }
	    } catch (SQLException e) {
	        System.err.println("Error: " + e.getMessage());
	    }
	    return result;
	}
	
	public int countByKeyword(String keyword) {
		int count = 0;
		String selectQuery;
		
		if(keyword == null || keyword.length() == 0) {
			selectQuery = "SELECT COUNT(*) FROM board";
		} else {
			selectQuery = "SELECT COUNT(*) FROM board b WHERE b.contents LIKE ? OR b.title LIKE ?";
		}
		
		try (Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement(selectQuery);
		) {
			if(keyword != null && keyword.length() != 0) {				
				pstmt.setString(1, "%" + keyword + "%");
				pstmt.setString(2, "%" + keyword + "%");
			}
			
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				count = rs.getInt(1);
			}
			rs.close();
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}
		
		return count;
	}
	
	private Connection getConnection() throws SQLException {
		Connection conn = null;

		try {
			Class.forName("org.mariadb.jdbc.Driver");

			String url = "jdbc:mariadb://" + SERVER_IP + ":3306/webdb";
			conn = DriverManager.getConnection(url, "webdb", "webdb");

		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패: " + e);
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}
		
		return conn;
	}

}
