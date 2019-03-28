package com.douzone.mysite.respository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.CommentVo;
@Repository
public class CommentDao {

public CommentVo get(CommentVo commentVo){

		CommentVo vo = null;
		
		Connection conn = null;
		ResultSet rs =null;
		PreparedStatement pstmt = null;
		
		try {
			 conn = getConnection();
			 pstmt = null;
			 rs = null;
						
			String sql = "select a.no, a.contents, a.write_date, a.board_no, a.user_no, a.name, a.password, a.g_no, a.o_no, a.depth " + 
					" from comment a , board b \r\n" + 
					"where a.board_no = b.no\r\n" + 
					"  and a.no = " + commentVo.getNo();
			
			pstmt = conn.prepareStatement(sql);						
			rs= pstmt.executeQuery();
			
			rs.next();
				
				long no = rs.getLong(1);
				String contents =rs.getString(2);
				String writeDate = rs.getString(3);
				long bno = rs.getLong(4);
				long userNo = rs.getLong(5);
				String name = rs.getString(6);
				String password = rs.getString(7);
				int gNo = rs.getInt(8);
				int oNo = rs.getInt(9);
				int depth = rs.getInt(10);
				
			    vo = new CommentVo();
				vo.setNo(no);
				vo.setContents(contents);
				vo.setWriteDate(writeDate);
				vo.setBoardNo(bno);
				vo.setUserNo(userNo);
				vo.setName(name);
				vo.setPassword(password);
				vo.setgNo(gNo);
				vo.setoNo(oNo);
				vo.setDepth(depth);
			
			
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
	
	
public List<CommentVo> getList(long boardNo){
		
		List<CommentVo> list = new ArrayList<CommentVo>();
		
		Connection conn = null;
		ResultSet rs =null;
		PreparedStatement pstmt = null;
		
		try {
			 conn = getConnection();
			 pstmt = null;
			 rs = null;
			
			String sql = "select a.no, a.contents, a.write_date, a.board_no, a.user_no, a.name, a.password,  a.g_no, a.o_no, a.depth " + 
					" from comment a , board b \r\n" + 
					"where a.board_no = b.no\r\n" + 
					"  and b.no = " + boardNo + 
					" order by a.g_no asc, a.o_no asc";
			
			pstmt = conn.prepareStatement(sql);						
			rs= pstmt.executeQuery();
			
			while(rs.next()) {
				
				long no = rs.getLong(1);
				String contents =rs.getString(2);
				String writeDate = rs.getString(3);
				long bno = rs.getLong(4);
				long userNo = rs.getLong(5);
				String name = rs.getString(6);
				String password = rs.getString(7);
				int gNo = rs.getInt(8);
				int oNo = rs.getInt(9);
				int depth = rs.getInt(10);
				
				CommentVo vo = new CommentVo();
				vo.setNo(no);
				vo.setContents(contents);
				vo.setWriteDate(writeDate);
				vo.setBoardNo(bno);
				vo.setUserNo(userNo);
				vo.setName(name);
				vo.setPassword(password);
				vo.setgNo(gNo);
				vo.setoNo(oNo);
				vo.setDepth(depth);
				
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
	


	public boolean insert(CommentVo vo) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
				
		try {
			 conn = getConnection();	
			 
			 String sql = "insert into comment \r\n" + 
			 		"     values(null,?, now(), ?, ?, ?, ?, (select ifnull(max(g_no)+1, 1) from comment a), 1, 0)";
																		//그룹                                                  순서 깊이
				pstmt = conn.prepareCall(sql);
				
				
				
				pstmt.setString(1, vo.getContents());
				pstmt.setLong(2, vo.getBoardNo());	
				
				//로그인안했으면 userNo가 0으로 오기때문에 null로바꿔준다.
				if( vo.getUserNo() == 0) {
					pstmt.setString(3, null);
				}else {
					pstmt.setLong(3, vo.getUserNo());	
				}
				
				pstmt.setString(4, vo.getName());
				pstmt.setString(5, vo.getPassword());
	
			int count = pstmt.executeUpdate();
			
			result = count ==1;			
			 
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
	
	public boolean reply(CommentVo vo) {
		
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
				
		try {
			 conn = getConnection();	
			 
			 
			 
			 String sql="update comment \r\n" + 
			 		"	set o_no = o_no+1 \r\n" + 
			 		"  where g_no=?  \r\n" + 
			 		"	and o_no > ?";
			 					
			 	pstmt = conn.prepareCall(sql);
				pstmt.setInt(1, vo.getgNo());
				pstmt.setInt(2, vo.getoNo());
				int count = pstmt.executeUpdate();
				 	
			 	    sql = "insert into comment\r\n" + 
			 		"	 values(null, ?, now(), ?, ?, ?, ?, ?, ?+1, ?+1)";

											 	    			 
			 	pstmt = conn.prepareCall(sql);
				
				pstmt.setString(1, vo.getContents());
				pstmt.setLong(2,vo.getBoardNo());
				
				//로그인안했으면 userNo가 0으로 오기때문에 null로바꿔준다.
				if( vo.getUserNo() == 0) {
					pstmt.setString(3, null);
				}else {
					pstmt.setLong(3, vo.getUserNo());	
				}
				
				pstmt.setString(4, vo.getName());
				pstmt.setString(5, vo.getPassword());
				pstmt.setInt(6, vo.getgNo());
				pstmt.setInt(7, vo.getoNo());
				pstmt.setInt(8, vo.getDepth());
				
			    count = pstmt.executeUpdate();
			
			result = count ==1;			
			 
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


	public boolean update(CommentVo vo) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
				
		try {
			 conn = getConnection();	
			 
			 String sql = "update comment set contents=? where no=? and password=?";
				
				pstmt = conn.prepareCall(sql);
				
				pstmt.setString(1, vo.getContents());
				pstmt.setLong(2, vo.getNo());
				pstmt.setString(3, vo.getPassword());
				
			int count = pstmt.executeUpdate();
			result = count ==1;			
			 
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

	
	//지우기
	public boolean delete(CommentVo vo) {
		
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
				
		try {
			 conn = getConnection();	
			 
			String sql="delete from comment where no=? and password = ?";
										//이름,패스워드,글,날짜
			
			pstmt = conn.prepareCall(sql);
			
			pstmt.setLong(1, vo.getNo());
			pstmt.setString(2, vo.getPassword());
			
			int count = pstmt.executeUpdate();
		
			count = pstmt.executeUpdate();
			
			result = count ==1;			
			 
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
