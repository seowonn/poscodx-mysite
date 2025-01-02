package mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import mysite.vo.UserVo;

@Repository
public class UserRepository {
	
	@Autowired
	private DataSource dataSource;
	
	private SqlSession sqlSession;

	public UserRepository(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	public void insert(UserVo vo) {
		sqlSession.insert("user.insert", vo);
	}

	public UserVo findByEmailAndPassword(String email, String password) {
		return sqlSession.selectOne("user.findByEmailAndPassword", Map.of("email", email, "password", password));
	}

	public UserVo findById(Long id) {
		UserVo userVo = null;

		try (Connection conn = dataSource.getConnection();
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

	public void update(UserVo vo) {
		sqlSession.update("user.update", vo);
	}

}
