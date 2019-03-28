package com.douzone.mysite.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.douzone.mysite.service.BoardService;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.CommentVo;
import com.douzone.mysite.vo.PaginationVo;
import com.douzone.mysite.vo.UserVo;
import com.douzone.security.Auth;
import com.douzone.security.AuthUser;

@Controller
@RequestMapping("/board")
public class BoardController {
	@Autowired
    private BoardService boardService;

	//---------------------------------------------------------------------------
		@RequestMapping(value="")
		public String list(@RequestParam (value="pageNo",required=false, defaultValue="1") Integer pageNo, 
						   @RequestParam (value="kwd",required=false, defaultValue="") String kwd,
						   Model model){

			Map<String, Object> PageAndlist;
			PageAndlist = boardService.list(pageNo, kwd);					
			model.addAttribute("list",PageAndlist.get("list"));
			model.addAttribute("page",PageAndlist.get("page"));
 
			return "board/list";			
		}
	//--------------------------------------------------------------------------
		@RequestMapping(value= "/view")
		public String view(@RequestParam (value="no",required=false) int no,
						   @RequestParam (value="kwd",required=false) String kwd,
						   Model model){
			
			Map<String, Object> PageAndlist;
			PageAndlist = boardService.view(no,kwd);		
			
			model.addAttribute("view",PageAndlist.get("view"));
			model.addAttribute("commentlist",PageAndlist.get("commentlist"));
			return "board/view";			
		}
		
		@Auth
		@RequestMapping(value= "/write", method=RequestMethod.GET)
		public String write(@ModelAttribute BoardVo boardVo){
			return "board/write";
		}
		
		@Auth
	    @RequestMapping(value= "/write", method=RequestMethod.POST) 
	    public String write(@RequestParam (value="kwd",required=false, defaultValue="") String kwd,
	    					@RequestParam (value="pageNo",required=false, defaultValue="1") String pageNo,
	    					@AuthUser UserVo authUser,
	    					@ModelAttribute @Valid BoardVo boardVo,
	    					BindingResult result,
	    					Model model){
	
			if(result.hasErrors()) {
				model.addAllAttributes(result.getModel()); //맵으로만들어서 jsp로던져줌
				return "board/write";
			}
		    
		    boardVo.setUserNo(authUser.getNo());
		    int no = boardService.write(boardVo);
		    
		    return "redirect:/board/view?pageNo="+pageNo+"&no="+no+"&kwd="+kwd; 
	    }	 
	    
		@Auth
	    @RequestMapping(value= "/modify", method=RequestMethod.GET) 
	    public String modify(@AuthUser UserVo authUser,
	    					 Long no,
//	    					 @ModelAttribute BoardVo boardVo,
	    					 PaginationVo pagenationvo,
	    					 Model model){

		    BoardVo boardVo = boardService.modifyform(no);
		    
		    if(authUser.getNo() != boardVo.getUserNo()) {	
		    	return "redirect:/board/view?pageNo="+pagenationvo.getPageNo()+
		    								   "&no="+ boardVo.getNo()+
		    								  "&kwd="+ pagenationvo.getKwd();
		    }
		    
//		    @ModelAttribute BoardVo boardVo 이거랑 밑에거랑 같은말임
//		    model.addAttribute("boardVo", new BoardVo);
		 	model.addAttribute("boardVo", boardVo);
	    	return "board/modify"; 	    
	    }	 
		
		@Auth
	    @RequestMapping(value= "/modify", method=RequestMethod.POST) 
	    public String modify(PaginationVo pagenationvo,
	    					 @AuthUser UserVo authUser,
	    					 @ModelAttribute @Valid BoardVo boardVo,
	    					 BindingResult result,
	    					 Model model){
			
			if(result.hasErrors()) {
				model.addAllAttributes(result.getModel()); //맵으로만들어서 jsp로던져줌
				return "board/modify";
			}
			
		    boardVo.setUserNo(authUser.getNo());
		    boardService.modify(boardVo);
		    
		    return "redirect:/board/view?pageNo="+pagenationvo.getPageNo()+
										   "&no="+boardVo.getNo()+
									      "&kwd="+pagenationvo.getKwd();
	    }	
		@Auth
	    @RequestMapping(value= "/delete", method=RequestMethod.GET) 
	    public String delete(@AuthUser UserVo authUser,
	    					 PaginationVo pagenationvo,
	    					 BoardVo boardVo){
	    
//		    if(authUser == null){
//		    	return "redirect:/board?pageNo="+pagenationvo.getPageNo()+
//		    							 "&kwd="+pagenationvo.getKwd();
//	    	}		    
			
		    boardVo.setUserNo(authUser.getNo());
		    boardService.delete(boardVo);
		    
		    return "redirect:/board?pageNo="+pagenationvo.getPageNo()+
									      "&kwd="+pagenationvo.getKwd();
	    }	
	    
	    @Auth
	    @RequestMapping(value= "/reply", method=RequestMethod.GET) 
	    public String reply( @AuthUser UserVo authUser,
	    					 PaginationVo pagenationvo,
	    					 BoardVo boardVo,
	    					 Model model){

		    boardVo = boardService.replyform(boardVo);
		    model.addAttribute("boardVo",boardVo);
			
		    return "board/reply";
	    }	
	    
	    @Auth
	    @RequestMapping(value= "/reply", method=RequestMethod.POST) 
	    public String reply(@AuthUser UserVo authUser,
	    					 PaginationVo pagenationvo,
	    					 @ModelAttribute @Valid BoardVo boardVo,
	    					 BindingResult result,
	    					 Model model){
	    
	    	
			if(result.hasErrors()) {
				model.addAllAttributes(result.getModel());
				return "board/reply";
			}
			
		    boardVo.setUserNo(authUser.getNo());
		    int replyNo= boardService.reply(boardVo);
		    
		    return "redirect:/board/view?pageNo="+pagenationvo.getPageNo()+
										   "&no="+replyNo+
										  "&kwd="+pagenationvo.getKwd();
	    }	
	    
	    @RequestMapping(value= "/comment", method=RequestMethod.POST) 
	    public String commentwrite(@AuthUser UserVo authUser,
	    						   PaginationVo pagenationvo,
	    						   BoardVo boardVo, //사실 이거랑 코멘트도 no가 각각있잔아.. 중복되니 원래이렇게하면 좀안맞긴하지만 되긴됨
	    						   @ModelAttribute @Valid CommentVo commentvo, //여기서는 일단 no가 필요없으니깐 보더no가세팅됨 ㅅㅂ..
	    						   BindingResult result,
	    						   Model model){
	    	
			if(result.hasErrors()) {
				Map<String, Object> PageAndlist;
				PageAndlist = boardService.view(boardVo.getNo(),pagenationvo.getKwd());		
				model.addAttribute("view",PageAndlist.get("view"));
				model.addAttribute("commentlist",PageAndlist.get("commentlist"));
				model.addAllAttributes(result.getModel());
				return "board/view";
			}
			
		    if(authUser != null){
		    	commentvo.setName(authUser.getName());			
		    	UserVo vo = boardService.commentgetpassword(authUser.getNo());
				commentvo.setPassword(vo.getPassword());
				commentvo.setUserNo(authUser.getNo());
		    }
		    
		    commentvo.setBoardNo(boardVo.getNo());
		    boardService.commentwrite(commentvo);
		    
		    return "redirect:/board/view?pageNo="+pagenationvo.getPageNo()+
		    							   "&no="+boardVo.getNo()+
										  "&kwd="+pagenationvo.getKwd();
	    }	
	    
	    @RequestMapping(value= "/commentdelete", method=RequestMethod.GET) 
	    public String commentdelete(){
	    
		    return "board/commentdelete";
	    }
	    @RequestMapping(value= "/commentdelete", method=RequestMethod.POST) 
	    public String commentdelete(HttpSession session,
    								PaginationVo pagenationvo,
									CommentVo commentvo){
	    	
	    	boardService.commentdelete(commentvo);
		    return "redirect:/board/view?pageNo="+pagenationvo.getPageNo()+
										   "&no="+commentvo.getBoardNo()+
										  "&kwd="+pagenationvo.getKwd();
	    }	
	    
	    @RequestMapping(value= "/commentmodify", method=RequestMethod.GET) 
	    public String commentmodify(PaginationVo pagenationvo,
								   CommentVo commentvo,
								   Model model){
	    	
	    	commentvo = boardService.commentmodifyform(commentvo);
	    	model.addAttribute("vo",commentvo);
			
		    return "board/commentmodify";
		}
	    
	    
	    @RequestMapping(value= "/commentmodify", method=RequestMethod.POST) 
	    public String commentmodify(PaginationVo pagenationvo,
								   CommentVo commentvo){
	    	
	    	boardService.commentmodify(commentvo);
	    	
	        return "redirect:/board/view?pageNo="+pagenationvo.getPageNo()+
										   "&no="+commentvo.getBoardNo()+
										  "&kwd="+pagenationvo.getKwd();
	    }
	    
	    @RequestMapping(value= "/commentreply", method=RequestMethod.GET) 
	    public String commentreply(PaginationVo pagenationvo,
								   CommentVo commentvo,
								   Model model){
	    	
	    	commentvo = boardService.commentreplyform(commentvo);
	    	
	  
	    	model.addAttribute("vo",commentvo);
	        
	    	return "board/commentreply";			
	    }
	    
	    @RequestMapping(value= "/commentreply", method=RequestMethod.POST) 
	    public String commentreply(HttpSession session,
	    						   PaginationVo pagenationvo,
								   CommentVo commentvo){
	    	
	    	UserVo authUser = (UserVo)session.getAttribute("authuser");
		    if(authUser != null){
		    	commentvo.setName(authUser.getName());			
		    	UserVo vo = boardService.commentgetpassword(authUser.getNo());
				commentvo.setPassword(vo.getPassword());
				commentvo.setUserNo(authUser.getNo());
		    }
	    	
	    	boardService.commentreply(commentvo);
	    	
	        return "redirect:/board/view?pageNo="+pagenationvo.getPageNo()+
										   "&no="+commentvo.getBoardNo()+
										  "&kwd="+pagenationvo.getKwd();
	    }
}
