package mysite.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import mysite.vo.SiteVo;

@Repository
public class SiteRepository {
	
	private SqlSession sqlSession;
	
	public SiteRepository(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	public SiteVo find() {
		SiteVo sdfs = sqlSession.selectOne("site.find");
		System.out.println(sdfs.getDescription());
		System.out.println(sdfs.getTitle());
		System.out.println(sdfs.getProfile());
		System.out.println(sdfs.getWelcome());
		return sdfs;
	}
	
	public void update(SiteVo siteVo) {
		sqlSession.update("site.update", siteVo);
	}

}
