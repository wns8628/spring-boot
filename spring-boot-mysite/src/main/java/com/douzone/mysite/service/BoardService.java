package com.douzone.mysite.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.mysite.respository.BoardDao;
import com.douzone.mysite.respository.CommentDao;
import com.douzone.mysite.respository.PaginationDao;
import com.douzone.mysite.respository.UserDao;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.CommentVo;
import com.douzone.mysite.vo.PaginationVo;
import com.douzone.mysite.vo.UserVo;

@Service
public class BoardService {	
	
	@Autowired
	private BoardDao boardDao;
	@Autowired
	private PaginationDao paginationdao;
	@Autowired
	private CommentDao commentDao;
	@Autowired
	private UserDao userdao;
	
	//리스트
	public Map<String, Object> list(Integer pageNo ,String kwd){
		    
		//------------------------------------------------------------------전체검색위해		
			kwd = kwd.replaceAll(" ","");	
		//-----------------------------------------------------------------		
			int manyboard = 5; //몇개당 한페이지할건지
			int limitcount = 5; //게시글이 10000개라도 한페이지당 10개의 페이지네이션이나오게
			int totalpage = paginationdao.getmaxpage( kwd ,manyboard );

			//URL page 최대 최소 체크
			if(pageNo < 1 ) {
				pageNo = 1;
			} else if( pageNo > totalpage ){ 
				pageNo = totalpage;
			}
			//페이지를위한정보 다가져와
			PaginationVo vo = paginationdao.getPageinfomation(kwd ,pageNo , manyboard , limitcount); 			
		//-----------------------------------------------------------------

			List<BoardVo> list = boardDao.getList(kwd , vo.getStartboard(), vo.getManyboard());			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("list", list);
			map.put("page",vo);
			
		return map;
	}	
	
	public Map<String, Object> view(long viewNo,String kwd){
		BoardVo vo = boardDao.getView(viewNo,kwd);		
		List<CommentVo> list = commentDao.getList(viewNo);

		Map<String, Object> map = new HashMap<String, Object>();
			
		map.put("view",vo);
		map.put("commentlist",list);

		return map;
	}
	
	public int write(BoardVo boardvo){
		return boardDao.insert(boardvo);
	}
	public BoardVo modifyform(Long no){
		return boardDao.getView(no);
	}	
	public void modify(BoardVo boardvo){
		boardDao.update(boardvo);
	}
	public void delete(BoardVo boardvo){
		boardDao.delete(boardvo);
	}
	public BoardVo replyform(BoardVo boardvo){
		return boardDao.getView(boardvo.getNo());
	}
	public int reply(BoardVo boardvo){
		return boardDao.reply(boardvo);
	}
	public UserVo commentgetpassword(long userNo){
		return userdao.get(userNo);
	}
	public void commentwrite(CommentVo commentvo){
		 commentDao.insert(commentvo);
	}
	public void commentdelete(CommentVo commentvo){
		 commentDao.delete(commentvo);
	}
	public CommentVo commentmodifyform(CommentVo commentvo){
		return commentDao.get(commentvo);
	}
	public void commentmodify(CommentVo commentvo){
		 commentDao.update(commentvo);
	}
	public CommentVo commentreplyform(CommentVo commentvo){
		return commentDao.get(commentvo);
	}
	public void commentreply(CommentVo commentvo){
		 commentDao.reply(commentvo);
	}
}
