package com.bitacademy.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bitacademy.mysite.repository.GuestbookRepository;
import com.bitacademy.mysite.vo.GuestbookVo;

@Service
public class GuestbookService {
	
	@Autowired
	private GuestbookRepository guestbookRepository;

	public List<GuestbookVo> getMessageList(){
		return guestbookRepository.findAll();
	}
	
	public void deleteMessage(String no) {
		guestbookRepository.delete(no);
	}
	
	public void addMessage(GuestbookVo vo) {
		guestbookRepository.insert(vo);
	}
	
	public boolean check(String no, String password) {
		return guestbookRepository.checkPassword(no, password);
	}
}
