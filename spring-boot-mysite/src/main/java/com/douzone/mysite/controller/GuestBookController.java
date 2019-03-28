package com.douzone.mysite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.douzone.mysite.service.GuestBookService;
import com.douzone.mysite.vo.GuestBookVo;

@Controller
@RequestMapping("/guestbook")
public class GuestBookController {
	
	@Autowired
	private GuestBookService guestbookService;
	
	@RequestMapping(value= {"","/list"})
	public String list(Model model){	
		
		List<GuestBookVo> list = guestbookService.getGuestbookList();
		return "guestbook/list";			
	}
	
}
