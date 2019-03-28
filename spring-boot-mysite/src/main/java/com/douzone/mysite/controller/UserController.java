package com.douzone.mysite.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.douzone.mysite.service.UserService;
import com.douzone.mysite.vo.UserVo;
import com.douzone.security.Auth;
import com.douzone.security.AuthUser;
import com.douzone.security.Auth.Role;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
    private UserService userService; //이게필요함 호출해야하니깐
	
	@RequestMapping(value="/auth", method=RequestMethod.POST)
	public void auth() {		
	}
	@RequestMapping(value="/logout", method=RequestMethod.GET)
	public void logout() {		
	}
	
	
//---------------------------------------------------------------------------------------------	
	@RequestMapping(value="/join", method=RequestMethod.GET)
	public String join(@AuthUser UserVo authUser,
					   @ModelAttribute UserVo userVo) {
		
		
		System.out.println("authUser : "+ authUser);
		System.out.println(authUser == null);
		
		//로그인한사람은 들어오면안됨
//		if(authUser != null){		
//			return "redirect:/main";
//		}
		
		return "/user/join";
	}
//---------------------------------------------------------------------------------------------	
	@RequestMapping(value="/join", method=RequestMethod.POST)
	public String join(@AuthUser UserVo authUser,
					   @ModelAttribute @Valid UserVo userVo, //@ModelAttribute를달아서 jsp에서 userVo값사용가능
					   BindingResult result,
					   Model model) { 
		
		//로그인한사람은 들어오면안됨
//		if(authUser!= null){		
//			return "redirect:/main";
//		}
		
		if(result.hasErrors()) {
			
//			List<ObjectError> list = result.getAllErrors();
//			for(ObjectError error:list) {
//				System.out.println(error);
//			}
			model.addAllAttributes(result.getModel()); //맵으로만들어서 jsp로던져줌
			return "user/join";
		}

		userService.join(userVo);//이게 비즈니스지
		return "redirect:/user/joinsuccess";
	}
//---------------------------------------------------------------------------------------------
	
	@RequestMapping("/joinsuccess")
	public String joinSuccess(@AuthUser UserVo authUser) {
		//로그인한사람은 들어오면안됨
//		if(authUser!= null){		
//			return "redirect:/main";
//		}
		return "/user/joinsuccess";
	}
//---------------------------------------------------------------------------------------------
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login(@AuthUser UserVo authUser) { 
		
		//로그인한사람은 들어오면안됨
//		if(authUser!= null){		
//			return "redirect:/main";
//		}
		return "/user/login";

	}
//---------------------------------------------------------------------------------------------	
    /* 인터셉터로 처리
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String login(HttpSession session, Model model,@ModelAttribute UserVo userVo) {

		//로그인한사람은 보내면안됨
		UserVo authUser = (UserVo)session.getAttribute("authuser");
		if(authUser!= null){		
			return "redirect:/main";
		}
		
		authUser = userService.login(userVo);		
		
		if(authUser == null){		
			model.addAttribute("result",  "fail");
			return "redirect:/user/login";
		}
		
		session.setAttribute("authuser", authUser); //세션에 등록
		return "redirect:/main";
	}
	*/
//---------------------------------------------------------------------------------------------	
	/*
	@RequestMapping(value="/logout", method=RequestMethod.GET)
	public String logout(HttpSession session) {
		userService.logout(session);
		return "redirect:/main";
	}
	*/
//---------------------------------------------------------------------------------------------		
	
	@Auth(Role.USER)
	@RequestMapping(value="/modify", method=RequestMethod.GET)
	public String modify(/*HttpSession session,*/ @AuthUser UserVo authUser, Model model) {
		
		System.out.println(authUser);
		//UserVo authUser = (UserVo)session.getAttribute("authuser");
		authUser = userService.modifyform(authUser.getNo());
		model.addAttribute("vo", authUser);
		return "user/modify";
	}

	@Auth(Role.USER)
	@RequestMapping(value="/modify", method=RequestMethod.POST)
	public String modify(@AuthUser UserVo authUser,
						 @ModelAttribute UserVo uservo) {
		
		uservo.setNo(authUser.getNo());
		userService.modify(uservo);		
		authUser.setName(uservo.getName());		//세션수정 , 가리키고있어서? 
		//session.setAttribute("authuser", authUser);              
		return "redirect:/main";
	}
	
	
	
	/* 원래 전역으로 글로발익셉션핸들러만듬 이건걍 맛보기지 GlobalExceptionHandler
	@ExceptionHandler(UserDaoException.class )
	public String handleUserDaoException() {
		return "error/exception";
	}
	*/
}
