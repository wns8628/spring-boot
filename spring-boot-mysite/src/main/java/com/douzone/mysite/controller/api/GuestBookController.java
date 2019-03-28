package com.douzone.mysite.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.douzone.dto.JSONResult;
import com.douzone.mysite.service.api.GuestBookService;
import com.douzone.mysite.vo.GuestBookVo;

@Controller("guestbookApiController")
@RequestMapping("/api/guestbook/ajax")
public class GuestBookController {

	@Autowired
    private GuestBookService guestbookService;

//--------------------------------------------
	
	@RequestMapping(value="")
	public String list(){		
		return "guestbook/index-ajax";			
	}
	
	@ResponseBody
	@RequestMapping(value="/list")
	public Object list(@RequestParam (value="p",required=false, defaultValue="1") String sPage){	
		
		if(sPage.matches("[-+]?\\d*\\.?\\d+") == false) {
			sPage ="1";	
		}
		int page = Integer.parseInt(sPage);
		List<GuestBookVo> list = guestbookService.getInitList(page);

		return JSONResult.success(list);			
	}
	
	@ResponseBody
	@RequestMapping(value="/insert")
	public Object insert(GuestBookVo guestbookVo){	
	
		long no = guestbookService.insert(guestbookVo);
		guestbookVo.setNo(no);
		return JSONResult.success(guestbookVo);			
	}

	@ResponseBody
	@RequestMapping(value="/delete")
	public Object list(GuestBookVo guestbookVo){	
		int result = guestbookService.delete(guestbookVo);
		return JSONResult.success(result);			
	}
}
