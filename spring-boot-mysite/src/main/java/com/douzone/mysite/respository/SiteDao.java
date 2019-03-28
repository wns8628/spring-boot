package com.douzone.mysite.respository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.SiteVo;

@Repository
public class SiteDao {


	@Autowired
	private SqlSession sqlSession;
	
	public SiteVo get()
	{
		return sqlSession.selectOne("site.get");
	}
	
	public void update(SiteVo vo)
	{
		sqlSession.update("site.update", vo);
	}

	
	  // 커넥트함수
//	   private Connection getConnection() throws SQLException {
//	      Connection conn = null;
//
//	      try {
//	         Class.forName("com.mysql.jdbc.Driver"); // 패키지 이름
//
//	         String url = "jdbc:mysql://localhost:3306/webdb"; // DB 종류마다 url이 다르다
//	         conn = DriverManager.getConnection(url, "webdb", "webdb"); // interface
//	      } catch (ClassNotFoundException e) {
//	         System.out.println("드라이버 로딩 실패" + e);
//	      }
//	      return conn;
//	   }
}
