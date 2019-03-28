package com.douzone.mysite.service.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.mysite.respository.GuestBookDao;
import com.douzone.mysite.vo.GuestBookVo;

@Service("guestbookApiService")
public class GuestBookService {
	@Autowired
	private GuestBookDao guestbookDao;
	
	public List<GuestBookVo> getInitList(int page){
		return guestbookDao.getList(page);
	}
	public long insert(GuestBookVo guestbookVo){
		return guestbookDao.insert(guestbookVo);
	}
	public int delete(GuestBookVo guestbookVo){
		return guestbookDao.delete(guestbookVo);
	}
}
