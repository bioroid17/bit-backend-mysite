package com.bitacademy.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bitacademy.mysite.repository.BoardRepository;
import com.bitacademy.mysite.repository.UserRepository;
import com.bitacademy.mysite.vo.BoardVo;

@Service
public class BoardService {
	
	@Autowired
	private BoardRepository boardRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	public List<BoardVo> getArticleList(Long startNo, Long endNo){
		return boardRepository.getArticles(startNo, endNo);
	}
	
	public Long getNumOfArticles() {
		return boardRepository.getCount();
	}

	public BoardVo getOneArticle(Long no){
		return boardRepository.getArticle(no);
	}

	public void hitUp(Long no){
		boardRepository.plusHit(no);
	}
	
	public boolean checkPasswd(String userNo, String password) {
		return userRepository.checkPassword(userNo, password);
	}

	public void writeArticle(BoardVo vo){
		boardRepository.write(vo);
	}

	public void replyArticle(BoardVo vo){
		boardRepository.reply(vo);
	}
	
	public void deleteArticle(String no) {
		boardRepository.delete(no);
	}
	
	public void updateArticle(BoardVo vo) {
		boardRepository.update(vo);
	}
}
