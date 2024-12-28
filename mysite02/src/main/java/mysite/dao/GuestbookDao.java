package mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mysite.vo.GuestbookVo;

public class GuestbookDao {
	
	private final String SERVER_IP = "192.168.1.12";

	public List<GuestbookVo> findAll() {
		List<GuestbookVo> result = new ArrayList<>();

		try (
			Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT id, name, contents, DATE_FORMAT(reg_date, '%Y-%m-%d %h:%i:%s') AS reg_date"
					+ " FROM guestbook"
					+ " ORDER BY reg_date DESC");
			ResultSet rs = pstmt.executeQuery();
		) {
			while (rs.next()) {
				Long id = rs.getLong(1);
				String name = rs.getString(2);
				String contents = rs.getString(3);
				String regDate = rs.getString(4);
				
				GuestbookVo vo = new GuestbookVo();
				vo.setId(id);
				vo.setName(name);
				vo.setContents(contents);
				vo.setRegDate(regDate);

				result.add(vo);
			}
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}

		return result;
	}

	public int insert(GuestbookVo vo) {
		int count = 0;

		try (
			Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement("insert into guestbook (name, password, contents, reg_date) values(?, ?, ?, now())");
		) {
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getPassword());
			pstmt.setString(3, vo.getContents());

			count = pstmt.executeUpdate();
			
			if (count == 0) {
			    System.out.println("Insert failed. No rows affected.");
			}

		} catch (SQLException e) {
			System.out.println("error: " + e);
		}

		return count;
	}
	
	public int deleteByIdAndPassword(Long id, String password) {
		int count = 0;
		System.out.println(id);
		System.out.println(password);

		try (
			Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement("DELETE FROM guestbook WHERE id=? AND password=?");
		) {
			pstmt.setLong(1, id);
			pstmt.setString(2, password);
			count = pstmt.executeUpdate();
			
			if (count == 0) {
			    System.out.println("Delete failed. No rows affected.");
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
