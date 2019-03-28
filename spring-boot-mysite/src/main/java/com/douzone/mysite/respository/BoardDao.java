package com.douzone.mysite.respository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.BoardVo;

@Repository
public class BoardDao {
	public BoardVo getView(long viewNo){

		BoardVo vo = null;		
		Connection conn = null;
		ResultSet rs =null;
		PreparedStatement pstmt = null;
		
		try {
			 conn = getConnection();
			 pstmt = null;
			 rs = null;

			//조회수올리기
			String sql = " update board set hit= hit+1 where no=? ";			 
			pstmt = conn.prepareCall(sql);
			pstmt.setLong(1, viewNo);
			pstmt.executeUpdate();
			 	
			//보여줄 글 			
			  sql = "     select a.no, a.title, a.contents, date_format(a.write_date, '%Y-%m-%d %h:%i:%s'),\r\n" + 
			  		"			a.hit, a.g_no, a.o_no, a.depth, a.user_no, b.name \r\n" + 
			  		"	   from board a, user b\r\n" + 
			  		"	  where a.user_no = b.no\r\n" + 
			  		"	    and a.no = ?";
			  
			pstmt = conn.prepareCall(sql);
			pstmt.setLong(1, viewNo);
			rs= pstmt.executeQuery();
			
			rs.next();
				
				long no = rs.getLong(1);
				String title = rs.getString(2);
				String contents =rs.getString(3);
				String writeDate = rs.getString(4);
				int hit = rs.getInt(5);
				int gNo = rs.getInt(6);
				int oNo = rs.getInt(7);
				int depth = rs.getInt(8);
				int userNo = rs.getInt(9);
				String name = rs.getString(10);

				vo = new BoardVo();
				vo.setNo(no);
				vo.setTitle(title);
				vo.setContents(contents);
				vo.setWriteDate(writeDate);
				vo.setHit(hit);
				vo.setgNo(gNo);
				vo.setoNo(oNo);
				vo.setDepth(depth);
				vo.setUserNo(userNo);
				vo.setName(name);
			
			
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
	public BoardVo getView(long viewNo, String kwd){

		BoardVo vo = null;		
		Connection conn = null;
		ResultSet rs =null;
		PreparedStatement pstmt = null;
		
		try {
			 conn = getConnection();
			 pstmt = null;
			 rs = null;

			//조회수올리기
			String sql = " update board set hit= hit+1 where no=? ";			 
			pstmt = conn.prepareCall(sql);
			pstmt.setLong(1, viewNo);
			pstmt.executeUpdate();
			 	
			//보여줄 글 			
			  sql = "     select a.no, a.title, a.contents, date_format(a.write_date, '%Y-%m-%d %h:%i:%s'),\r\n" + 
			  		"			a.hit, a.g_no, a.o_no, a.depth, a.user_no, b.name \r\n" + 
			  		"	   from board a, user b\r\n" + 
			  		"	  where a.user_no = b.no\r\n" + 
			  		"	    and a.no = ?";
			  
			pstmt = conn.prepareCall(sql);
			pstmt.setLong(1, viewNo);
			rs= pstmt.executeQuery();
			
			rs.next();
				
				long no = rs.getLong(1);
				String title = rs.getString(2);
				String contents =rs.getString(3);
				String writeDate = rs.getString(4);
				int hit = rs.getInt(5);
				int gNo = rs.getInt(6);
				int oNo = rs.getInt(7);
				int depth = rs.getInt(8);
				int userNo = rs.getInt(9);
				String name = rs.getString(10);
				
				title = title.replaceAll(kwd, "<span style='background-color : yellow'>"+kwd+"</span>");
				contents = contents.replaceAll(kwd, "<span style='background-color :yellow'>"+kwd+"</span>");
				name =name.replaceAll(kwd, "<span style='background-color:yellow'>"+kwd+"</span>");
				
				vo = new BoardVo();
				vo.setNo(no);
				vo.setTitle(title);
				vo.setContents(contents);
				vo.setWriteDate(writeDate);
				vo.setHit(hit);
				vo.setgNo(gNo);
				vo.setoNo(oNo);
				vo.setDepth(depth);
				vo.setUserNo(userNo);
				vo.setName(name);
			
			
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
		

public int insert(BoardVo vo) {
	int result = 0;
	Connection conn = null;
	PreparedStatement pstmt = null;
			
	try {
		 conn = getConnection();	
		 
		 String sql = "insert into board\r\n" + 
		 		"	 values(null, ?, ?, now(), 0, (select ifnull(max(g_no)+1, 1) from board a) , 1, 0, ?)";
			
			pstmt = conn.prepareCall(sql);
			
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setLong(3, vo.getUserNo());

			pstmt.executeUpdate();
			
			sql="select last_insert_id()";
			pstmt = conn.prepareCall(sql);						
			ResultSet rs= pstmt.executeQuery();
			rs.next();
			result = rs.getInt(1);
		
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

public int reply(BoardVo vo) {
	
	int result = 0;
	Connection conn = null;
	PreparedStatement pstmt = null;
			
	try {
		 conn = getConnection();	
		 
		 
		 
		 String sql="update board \r\n" + 
		 		"	set o_no = o_no+1 \r\n" + 
		 		"  where g_no=?  \r\n" + 
		 		"	and o_no > ?";
		 					
		 	pstmt = conn.prepareCall(sql);
			pstmt.setInt(1, vo.getgNo());
			pstmt.setInt(2, vo.getoNo());
			int count = pstmt.executeUpdate();
			 	
		 	    sql = "insert into board\r\n" + 
		 		"	 values(null, ?, ?, now(), 0, ?, ?+1, ?+1, ?)";

		 
		 	pstmt = conn.prepareCall(sql);
			
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setInt(3, vo.getgNo());
			pstmt.setInt(4, vo.getoNo());
			pstmt.setInt(5, vo.getDepth());
			pstmt.setLong(6, vo.getUserNo());

		    count = pstmt.executeUpdate();
		
			sql="select last_insert_id()";
			pstmt = conn.prepareCall(sql);						
			ResultSet rs= pstmt.executeQuery();
			rs.next();
			result = rs.getInt(1);
		 
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

public boolean update(BoardVo vo) {
	boolean result = false;
	Connection conn = null;
	PreparedStatement pstmt = null;
			
	try {
		 conn = getConnection();	
		 
		 String sql = "update board set title=?, contents=? where no=? and user_no=?";
			
			pstmt = conn.prepareCall(sql);
			
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setLong(3, vo.getNo());
			pstmt.setLong(4, vo.getUserNo());
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

public boolean delete(BoardVo vo) {
	
	boolean result = false;
	Connection conn = null;
	PreparedStatement pstmt = null;
			
	try {
		 conn = getConnection();	
		 
		String sql="delete from board where no=? and user_no = ?";
		
		pstmt = conn.prepareCall(sql);
		
		pstmt.setLong(1, vo.getNo());
		pstmt.setLong(2, vo.getUserNo());
		
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
	
	public List<BoardVo> getList(String kwd, int startboard, int manyboard){
		
		List<BoardVo> list = new ArrayList<BoardVo>();
		
		Connection conn = null;
		ResultSet rs =null;
		PreparedStatement pstmt = null;
		
		try {
			 conn = getConnection();
			 pstmt = null;
			 rs = null;
			
			 
			String sql = " select a.no, a.title, a.contents, date_format(a.write_date, '%Y-%m-%d %h:%i:%s'),\r\n" + 
					"			 a.hit,a.g_no, a.o_no, a.depth, a.user_no, b.name, (select count(*)\r\n" + 
					"													       	      from comment c , board d \r\n" + 
					"																 where c.board_no = d.no\r\n" + 
					"																   and d.no = a.no) as commentcount\r\n" + 
					"    from board a, user b \r\n" + 
					"   where a.user_no = b.no\r\n" + 
					"     and (a.title like '%" + kwd +"%' or a.contents like '%"+ kwd +"%' or b.name like '%"+ kwd +"%') \r\n" + 
					"order by a.g_no desc, a.o_no asc limit "+ startboard + ","+ manyboard;
			
			pstmt = conn.prepareStatement(sql);						
			rs= pstmt.executeQuery();
			
			  			
			while(rs.next()) {
				
				long no = rs.getLong(1);
				String title = rs.getString(2);
				String contents =rs.getString(3);
				String writeDate = rs.getString(4);
				int hit = rs.getInt(5);
				int gNo = rs.getInt(6);
				int oNo = rs.getInt(7);
				int depth = rs.getInt(8);
				int userNo = rs.getInt(9);
				String name = rs.getString(10);
				int commentCount = rs.getInt(11);
				
				title = title.replaceAll(kwd, "<span style='background-color : yellow'>"+kwd+"</span>");
				contents = contents.replaceAll(kwd, "<span style='background-color :yellow'>"+kwd+"</span>");
				name =name.replaceAll(kwd, "<span style='background-color:yellow'>"+kwd+"</span>");
				
				BoardVo vo = new BoardVo();
				vo.setNo(no);
				vo.setTitle(title);
				vo.setContents(contents);
				vo.setWriteDate(writeDate);
				vo.setHit(hit);
				vo.setgNo(gNo);
				vo.setoNo(oNo);
				vo.setDepth(depth);
				vo.setUserNo(userNo);
				vo.setName(name);
				vo.setCommentCount(commentCount);
				
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
