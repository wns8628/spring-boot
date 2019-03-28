package com.douzone.mysite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.douzone.mysite.service.FileuploadService;
import com.douzone.mysite.service.SiteService;
import com.douzone.mysite.vo.SiteVo;
import com.douzone.security.Auth;
import com.douzone.security.Auth.Role;

//@Auth(Role.ADMIN)
@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired 
	private SiteService siteService;

	@Autowired 
	private FileuploadService fileuploadService;
//---------------------------------------------
	@Auth(Role.ADMIN)
	@RequestMapping(value={"","/main"}, method=RequestMethod.GET)
	public String main(Model model){		
		SiteVo siteVo = siteService.main();
		model.addAttribute("siteVo",siteVo);
		return "admin/main";
	}
	@Auth(Role.ADMIN)
	@RequestMapping(value={"","/main"}, method=RequestMethod.POST)
	public String main(SiteVo siteVo,
					   @RequestParam(value="upload-profile") MultipartFile multipartFile){	

		
		String profile = fileuploadService.restore(multipartFile);
		siteVo.setProfile(profile);
		siteService.main(siteVo);
		
		return "redirect:/admin";		
	}
	
	
//	@Auth(Role.ADMIN)
//	@RequestMapping("/board")
//	public String board() {
//		return "admin/board";
//	}

}
