package com.douzone.mysite.respository;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.UserVo;
@Repository
public class UserDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	public UserVo get(String email) {
		return sqlSession.selectOne("user.getByEmail", email);
	}
	public UserVo get(long userNo) {
		return sqlSession.selectOne("user.getAll", userNo);
	}
	
	public UserVo get(Map<String, String> loginMap) {
		return sqlSession.selectOne("user.getByEmailAndPassword",loginMap);
	}
	
	public int insert(UserVo vo) {
		return sqlSession.insert("user.insert", vo);
	}
	
	public int update(UserVo vo) {	
		return sqlSession.update("user.updateUserInfo", vo);
	}
}

