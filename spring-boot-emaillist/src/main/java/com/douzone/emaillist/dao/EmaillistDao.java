package com.douzone.emaillist.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.douzone.emaillist.vo.EmaillistVo;

@Repository
public class EmaillistDao {
	
	public boolean insert(EmaillistVo vo) {
		boolean result =false;
		Connection conn = null;
		PreparedStatement pstmt = null;
				
		try {
			 conn = getConnection();	
			 
			String sql="insert \r\n" + 
					"  into emaillist \r\n" + 
					"values(null, ?, ?, ?)";
			
			pstmt = conn.prepareCall(sql);
			
			pstmt.setString(1, vo.getFirstName());
			pstmt.setString(2, vo.getLastName());
			pstmt.setString(3, vo.getEmail());
			
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
	
	public List<EmaillistVo> getList(){
		
		List<EmaillistVo> list = new ArrayList<EmaillistVo>();
		
		Connection conn = null;
		ResultSet rs =null;
		PreparedStatement pstmt = null;
		
		try {
			 conn = getConnection();
			 pstmt = null;
			rs = null;
			
			String sql = "select no,first_name, last_name, email\r\n" + 
					"  from emaillist\r\n" + 
					"order by no desc";
			
			pstmt = conn.prepareStatement(sql);
			
			rs= pstmt.executeQuery();
			
			while(rs.next()) {
				
				long no = rs.getLong(1);
				String firstName = rs.getString(2);
				String lastName = rs.getString(3);
				String email = rs.getString(4);
				
				EmaillistVo vo = new EmaillistVo();
				vo.setNo(no);
				vo.setFirstName(firstName);
				vo.setLastName(lastName);
				vo.setEmail(email);
				
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
