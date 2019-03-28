package com.douzone.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.mysite.respository.GalleryDao;
import com.douzone.mysite.vo.GalleryVo;

@Service
public class GalleryService {
	@Autowired
	GalleryDao galleryDao;
	
	public List<GalleryVo> getGalleryList(){
		List<GalleryVo> list = galleryDao.getList();
		System.out.println(list);
		return list;
	}
	
	public void upload(GalleryVo galleryVo){
		
		galleryDao.insert(galleryVo);
	}
	
	public void delete(long no){
		galleryDao.delete(no);
	}
}
