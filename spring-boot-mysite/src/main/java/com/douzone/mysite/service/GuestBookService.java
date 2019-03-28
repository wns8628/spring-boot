package com.douzone.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.mysite.respository.GuestBookDao;
import com.douzone.mysite.vo.GuestBookVo;

@Service
public class GuestBookService {

	@Autowired
	private GuestBookDao guestbookDao;
	
	public List<GuestBookVo> getGuestbookList(){
		List<GuestBookVo> list = guestbookDao.getList();
		return list;
	}
}
