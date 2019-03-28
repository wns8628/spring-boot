package com.douzone.mysite.service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.mysite.respository.UserDao;
import com.douzone.mysite.vo.UserVo;

@Service
public class UserService {
	@Autowired
	private UserDao userDao;
	
	public void join(UserVo userVo) {
		//1.DB에 가입회원정보 insert하기
			userDao.insert(userVo);	//익세셥안받음 컨트롤러로 올라감
		//2.email 주소확인하는 메일보내기 이걸 서비스에넣어야하잔아 다오에넣을순없으니 서비스계층이 필요함	
	}
	
	public UserVo login(UserVo userVo) {
		
		Map<String, String> loginMap = new HashMap<String,String>();
		loginMap.put("email",userVo.getEmail());
		loginMap.put("password",userVo.getPassword());

		return	userDao.get(loginMap);	//익세셥안받음 컨트롤러로 올라감		
	}
	
	public void logout(HttpSession session) {
		UserVo authUser = (UserVo)session.getAttribute("authuser");
		if(authUser != null){		
			session.removeAttribute("authuser"); 
			session.invalidate();
		}
	}	
	
	public UserVo existEmail(String email){
		return userDao.get(email); 
	}
	public UserVo modifyform(Long no) {
		return userDao.get(no);
	}	
	public void modify(UserVo uservo) {
		userDao.update(uservo);
	}	
}
