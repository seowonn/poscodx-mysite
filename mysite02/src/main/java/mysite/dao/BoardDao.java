package mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mysite.vo.BoardVo;

public class BoardDao {

	public List<BoardVo> findAll(int page, int offset) {
		List<BoardVo> result = new ArrayList<BoardVo>();

		try (Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement("select b.id, b.title, b.contents, b.hit, b.reg_date, "
						+ "b.g_no, b.o_no, b.depth, a.name, a.id " + "from board b "
						+ "join user a on a.id = b.user_id " + "order by g_no desc, o_no asc " + "limit ? offset ?");

		) {
			pstmt.setInt(1, page);
			pstmt.setInt(2, offset);

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				// TODO: 여기 불필요 정보까지 쿼리 해왔을 수도 있음.
				Long id = rs.getLong(1);
				String title = rs.getString(2);
				String contents = rs.getString(3);
				int hit = rs.getInt(4);
				String regDate = rs.getString(5);
				int gNo = rs.getInt(6);
				int oNo = rs.getInt(7);
				int depth = rs.getInt(8);
				String userName = rs.getString(9);
				Long userId = rs.getLong(10);

				BoardVo vo = new BoardVo();
				vo.setId(id);
				vo.setTitle(title);
				vo.setContents(contents);

				vo.setRegDate(regDate);
				vo.setDepth(depth);
				vo.setUserName(userName);
				vo.setUserId(userId);

				result.add(vo);
			}
			rs.close();
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}
		return result;
	}

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
		BoardVo result = null;

		try (Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement("select title, contents, hit, "
						+ "reg_date, g_no, o_no, depth, user_id from board where id = ?");

		) {
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
	
	public int count() {
		int count = 0;
		try (Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement(
						"select count(*) from board");
				ResultSet rs = pstmt.executeQuery();
		) {
			if(rs.next()) {
				count = rs.getInt(1);
			}

		} catch (SQLException e) {
			System.out.println("error: " + e);
		}
		return count;
	}
	
	private Connection getConnection() throws SQLException {
		Connection conn = null;

		try {
			Class.forName("org.mariadb.jdbc.Driver");

			String url = "jdbc:mariadb://192.168.0.10:3306/webdb";
			conn = DriverManager.getConnection(url, "webdb", "webdb");

		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패: " + e);
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}

		return conn;
	}

}
