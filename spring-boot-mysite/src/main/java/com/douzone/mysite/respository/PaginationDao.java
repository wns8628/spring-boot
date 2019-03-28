package com.douzone.mysite.respository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.PaginationVo;
@Repository
public class PaginationDao {

	public PaginationVo getPageinfomation(String kwd,int pageNo,int manyboard, int limitcount){

		PaginationVo vo = null;		
		Connection conn = null;
		ResultSet rs =null;
		PreparedStatement pstmt = null;
		
		try {
			 conn = getConnection();
			 pstmt = null;
			 rs = null;
		
			String sql = "  select count(*)\r\n" + 
					"    from board a, user b \r\n" + 
					"   where a.user_no = b.no\r\n" + 
					"     and (a.title like '%"+kwd+"%' or a.contents like '%"+kwd+"%' or b.name like '%"+kwd+"%') ";
			  
			pstmt = conn.prepareCall(sql);
			rs= pstmt.executeQuery();
			
			rs.next();			
				
				int totalboardcount = rs.getInt(1);
		
				int modVal = (totalboardcount % manyboard);
				int divVal = (totalboardcount / manyboard);
				int totalpage = (modVal > 0) ? divVal+1 : divVal;	

				pageNo = (int)((pageNo > totalpage) ? totalpage+1 : pageNo);
				
				int startboard = (pageNo - 1) * manyboard ;
				if(startboard < 0 ) {
					startboard = 0;
				}
				
				int startPage = (( (pageNo-1) / limitcount) * limitcount) + 1;				
				int endPage = startPage + limitcount - 1;
				
				vo = new PaginationVo();				
				vo.setTotalboardcount(totalboardcount);
				vo.setTotalpage(totalpage);
				vo.setStartboard(startboard);
				vo.setManyboard(manyboard);
				vo.setPageNo(pageNo);
				vo.setStartPage(startPage);
				vo.setEndPage(endPage);
				vo.setKwd(kwd);
				
		} catch (SQLException e) {
			System.out.println( "error: "+e );
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return vo;
	}
	
	public int getmaxpage(String kwd,int manyboard){

		int totalpage = 0;
		Connection conn = null;
		ResultSet rs =null;
		PreparedStatement pstmt = null;
		
		try {
			 conn = getConnection();
			 pstmt = null;
			 rs = null;


			String sql = "  select count(*)\r\n" + 
					"    from board a, user b \r\n" + 
					"   where a.user_no = b.no\r\n" + 
					"     and (a.title like '%"+kwd+"%' or a.contents like '%"+kwd+"%' or b.name like '%"+kwd+"%') ";
			  
			pstmt = conn.prepareCall(sql);
			rs= pstmt.executeQuery();
			
			rs.next();			
				int totalboardcount = rs.getInt(1);
		
				int modVal = (totalboardcount % manyboard);
				int divVal = (totalboardcount / manyboard);
				totalpage = (modVal > 0) ? divVal+1 : divVal;			
				
		} catch (SQLException e) {
			System.out.println( "error: "+e );
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return totalpage;
	}
		

   // 커넥트함수
   private Connection getConnection() throws SQLException {
      Connection conn = null;	      

      try {
         Class.forName("com.mysql.jdbc.Driver"); // 패키지 이름

         String url = "jdbc:mysql://localhost:3306/webdb"; // DB 종류마다 url이 다르다
         conn = DriverManager.getConnection(url, "webdb", "webdb"); // interface
      } catch (ClassNotFoundException e) {
         System.out.println("드라이버 로딩 실패" + e);
      }
      return conn;
   }
}
