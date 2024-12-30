package mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import mysite.vo.UserVo;

@Repository
public class UserRepository {

	public void insert(UserVo vo) {
		int count = 0;

		try (Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement(
						"insert into user (name, email, password, gender, join_date) values(?, ?, ?, ?, now(), 'USER')");) {
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getEmail());
			pstmt.setString(3, vo.getPassword());
			pstmt.setString(4, vo.getGender());

			count = pstmt.executeUpdate();

			if (count == 0) {
				System.out.println("Insert failed. No rows affected.");
			}

		} catch (SQLException e) {
			System.out.println("error: " + e);
		}

	}

	private Connection getConnection() throws SQLException {
		Connection conn = null;

		try {
			Class.forName("org.mariadb.jdbc.Driver");

			String url = "jdbc:mariadb://192.168.0.3:3306/webdb";
			conn = DriverManager.getConnection(url, "webdb", "webdb");

		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패: " + e);
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}

		return conn;
	}

	public UserVo findByEmailAndPassword(String email, String password) {

		UserVo userVo = null;

		try (Connection conn = getConnection();
				PreparedStatement pstmt = conn
						.prepareStatement("select id, name, role from user where email=? and password=?");) {
			pstmt.setString(1, email);
			pstmt.setString(2, password);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				Long id = rs.getLong(1);
				String name = rs.getString(2);
				String role = rs.getString(3);

				userVo = new UserVo();
				userVo.setId(id);
				userVo.setName(name);
				userVo.setRole(role);
			}
			rs.close();
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}

		return userVo;

	}

	public UserVo findById(Long id) {
		UserVo userVo = null;

		try (Connection conn = getConnection();
				PreparedStatement pstmt = conn
						.prepareStatement("select name, email, gender from user where id=?");
		) {
			pstmt.setLong(1, id);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				String name = rs.getString(1);
				String email = rs.getString(2);
				String gender = rs.getString(3);
				System.out.println(name);

				userVo = new UserVo();
				userVo.setName(name);
				userVo.setEmail(email);
				userVo.setGender(gender);
			}
			rs.close();
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}

		return userVo;
	}

	public void updateUserByEmail(UserVo vo) {
		
		try (Connection conn = getConnection();
				PreparedStatement pstmt1 = conn
						.prepareStatement("UPDATE user SET name = ?, gender=? WHERE email = ?");
				PreparedStatement pstmt2 = conn
						.prepareStatement("UPDATE user SET name = ?, password = ?, gender=? WHERE email = ?");
		) {
			int count = 0;
			if(vo.getPassword() == null || vo.getPassword().length() == 0) {
				pstmt1.setString(1, vo.getName());
				pstmt1.setString(2, vo.getGender());
				pstmt1.setString(3, vo.getEmail());

				count = pstmt1.executeUpdate();
			} else {
				pstmt2.setString(1, vo.getName());
				pstmt2.setString(2, vo.getPassword());
				pstmt2.setString(3, vo.getGender());
				pstmt2.setString(4, vo.getEmail());

				count = pstmt2.executeUpdate();
			}

			if (count < 1) {
				System.out.println("Update failed. No rows affected.");
			}
			
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}

	}

}
