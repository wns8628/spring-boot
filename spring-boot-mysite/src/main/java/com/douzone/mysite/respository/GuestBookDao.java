package com.douzone.mysite.respository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.GuestBookVo;

@Repository
public class GuestBookDao {
	
	public long insert(GuestBookVo vo) {
		long result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		long value = 0;
		
		try {
			 conn = getConnection();	
			 
			String sql="insert into guestbook\r\n" + 
					"      values(null, ?,password(?),?,now() )";
										//이름,패스워드,글,날짜
			pstmt = conn.prepareCall(sql);
			
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getPassword());
			pstmt.setString(3, vo.getMessage());
			
			pstmt.executeUpdate();
			
			/* 방금들어간 row의 primary key no */
			sql = "select last_insert_id()";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			rs.next();
			
			value = rs.getLong(1);
			
		}  catch (SQLException e) {
			System.out.println( "error: "+e );
		} finally {
			try {
				if(rs != null)
					rs.close();
				if(conn != null) {
					conn.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return value;
	}

	//지우기
	public int delete(GuestBookVo vo) {
		
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
				
		try {
			 conn = getConnection();	
			 
			String sql="delete from guestbook where no=? and password = password(?)";
										//이름,패스워드,글,날짜
			
			pstmt = conn.prepareCall(sql);
			
			pstmt.setLong(1, vo.getNo());
			pstmt.setString(2, vo.getPassword());
			
			int count = pstmt.executeUpdate();
		
			//count = pstmt.executeUpdate();
			
			result = (count == 1) ? (int)vo.getNo() : -1;			
			 
		}  catch (SQLException e) {
			System.out.println( "error: "+e );
		} finally {
			try {
				if(conn != null) {
					conn.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		return result;
	}
	
	public List<GuestBookVo> getList(){
		
		List<GuestBookVo> list = new ArrayList<GuestBookVo>();
		
		Connection conn = null;
		ResultSet rs =null;
		PreparedStatement pstmt = null;
		
		try {
			 conn = getConnection();
			 pstmt = null;
			 rs = null;
			
			 
			String sql = "select no , name, password, message, date_format(reg_date, '%Y-%m-%d %h:%i:%s')\r\n" + 
					"       from guestbook order by reg_date desc";
			
			pstmt = conn.prepareStatement(sql);
			
			rs= pstmt.executeQuery();
			
			while(rs.next()) {
				
				long no = rs.getLong(1);
				String name = rs.getString(2);
				String password = rs.getString(3);
				String message = rs.getString(4);
				String regDate = rs.getString(5);
				
				GuestBookVo vo = new GuestBookVo();
				vo.setNo(no);
				vo.setName(name);;
				vo.setPassword(password);
				vo.setMessage(message);
				vo.setRegDate(regDate);
				
				list.add(vo);
			}
			
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
		return list;
	}
	
	public List<GuestBookVo> getList(int page){
		
		List<GuestBookVo> list = new ArrayList<GuestBookVo>();
		
		Connection conn = null;
		ResultSet rs =null;
		PreparedStatement pstmt = null;
		
		try {
			 conn = getConnection();
			 pstmt = null;
			 rs = null;
			
			String sql = "select no , name, password, message, date_format(reg_date, '%Y-%m-%d %h:%i:%s')\r\n" + 
					"       from guestbook order by reg_date desc" + 
					" limit ?, 5";
			
			pstmt = conn.prepareStatement(sql);	
			
			pstmt.setInt(1, (page-1) * 5 );
			
			rs= pstmt.executeQuery();
			
			while(rs.next()) {
				
				long no = rs.getLong(1);
				String name = rs.getString(2);
				String password = rs.getString(3);
				String message = rs.getString(4);
				String regDate = rs.getString(5);
				
				GuestBookVo vo = new GuestBookVo();
				vo.setNo(no);
				vo.setName(name);;
				vo.setPassword(password);
				vo.setMessage(message);
				vo.setRegDate(regDate);
				
				list.add(vo);
			}
			
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
		return list;
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
