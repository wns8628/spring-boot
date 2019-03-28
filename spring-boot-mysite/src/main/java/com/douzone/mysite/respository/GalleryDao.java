package com.douzone.mysite.respository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.GalleryVo;

@Repository
public class GalleryDao {

	
public List<GalleryVo> getList(){
		
		List<GalleryVo> list = new ArrayList<GalleryVo>();
		
		Connection conn = null;
		ResultSet rs =null;
		PreparedStatement pstmt = null;
		
		try {
			 conn = getConnection();
			 pstmt = null;
			 rs = null;
			
			 
			String sql = "select no,comment,image_url from gallery order by no desc";
			
			pstmt = conn.prepareStatement(sql);						
			rs= pstmt.executeQuery();
			  			
			while(rs.next()) {
				
				long no = rs.getLong(1);
				String comment = rs.getString(2);
				String imageUrl = rs.getString(3);
				
				GalleryVo vo = new GalleryVo();
				vo.setNo(no);
				vo.setComment(comment);
				vo.setImageUrl(imageUrl);
				
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
	
	public int insert(GalleryVo vo) {
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
				
		try {
			 conn = getConnection();	
			 
			 String sql = "insert into gallery values(null,?,?)";
				
				pstmt = conn.prepareCall(sql);
				
				pstmt.setString(1, vo.getComment());
				pstmt.setString(2, vo.getImageUrl());
	
				pstmt.executeUpdate();

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
	

	public boolean delete(long no) {
	
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
				
		try {
			 conn = getConnection();	
			 
			String sql="delete from gallery where no=?";
			pstmt = conn.prepareCall(sql);
			pstmt.setLong(1, no);
			
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
