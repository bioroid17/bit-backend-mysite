package com.bitacademy.mysite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bitacademy.mysite.security.Auth;
import com.bitacademy.mysite.service.BoardService;
import com.bitacademy.mysite.vo.BoardVo;

@Controller
@RequestMapping("/board")
public class BoardController {

	@Autowired
	private BoardService boardService;
	
	@RequestMapping("")
	public String list(Model model) {
		/* default action */
		
		Long pageBlock = 5L;
		Long pageSize = 10L;
		
		String p = null;	// page번호
		Long articleCount = boardService.getNumOfArticles();
		Long startNo = 0L;
		Long endNo = 0L;
		
		// paging 계산 용도
		Long currentPage = 0L;
		Long pageCount = 0L;
		Long startPage = 0L;
		Long endPage = 0L;
		
		if(p == null) { 
			p = "1";
		}
		currentPage = Long.parseLong(p);
		startNo = (currentPage - 1) * pageSize + 1;
		endNo = startNo + pageSize - 1;
		if (endNo > articleCount) {
			endNo = articleCount;
		}
		
		pageCount = (articleCount / pageSize) + (articleCount % pageSize == 0 ? 0L : 1L);
		startPage = (currentPage / pageBlock) * pageBlock + 1;
		if(currentPage % pageBlock == 0) {
			startPage -= pageBlock;
		}
		endPage = startPage + pageBlock - 1;
		if (endPage > pageCount) {
			endPage = pageCount;
		}

		List<BoardVo> list = boardService.getArticleList(startNo, endNo);
		model.addAttribute("list", list);
		model.addAttribute("startNo", startNo);
		model.addAttribute("endNo", endNo);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		return "board/list";
	}

	@RequestMapping("/view/{no}")
	public String view(@PathVariable("no") Long no, Model model) {
		
		BoardVo vo = boardService.getOneArticle(no);
		boardService.hitUp(no);
		
		model.addAttribute("vo", vo);
		model.addAttribute("no", no);
		
		return "board/view";
	}

	@Auth
	@RequestMapping(value="/write", method=RequestMethod.GET)
	public String write() {
		return "board/write";
	}

	@Auth
	@RequestMapping(value="/write", method=RequestMethod.POST)
	public String write(BoardVo vo) {
		vo.setOrderNo(1L);
		vo.setDepth(0L);
		boardService.writeArticle(vo);
		return "redirect:/board";
	}

	@Auth
	@RequestMapping(value="/reply/{no}", method=RequestMethod.GET)
	public String reply(@PathVariable("no") String no, Model model) {
		model.addAttribute("no", no);
		model.addAttribute("vo", boardService.getOneArticle(Long.parseLong(no)));
		
		return "board/reply";
	}

	@Auth
	@RequestMapping(value="/reply/{no}", method=RequestMethod.POST)
	public String reply(@PathVariable("no") String no, BoardVo vo) {
		BoardVo parent = boardService.getOneArticle(Long.parseLong(no));
		
		vo.setGroupNo(parent.getGroupNo());
		vo.setOrderNo(parent.getOrderNo()+1);
		vo.setDepth(parent.getDepth()+1);
		
		boardService.replyArticle(vo);
		return "redirect:/board";
	}

	@Auth
	@RequestMapping(value="/delete/{no}", method=RequestMethod.GET)
	public String delete(@PathVariable("no") String no, String status, String userNo, Model model) {
		model.addAttribute("no", no);
		model.addAttribute("userNo", userNo);
		model.addAttribute("status", status);
		return "board/delete";
	}

	@Auth
	@RequestMapping(value="/delete/{no}", method=RequestMethod.POST)
	public String delete(@PathVariable("no") String no, String userNo, String status, String password, Model model) {

		boolean equal = boardService.checkPasswd(userNo, password);
		if (equal) {
			boardService.deleteArticle(no);
			return "redirect:/board";
		} else {
			model.addAttribute("no", no);
			model.addAttribute("userNo", userNo);
			model.addAttribute("status", status);
			return "board/delete";
		}
	}


	@Auth
	@RequestMapping(value="/update/{no}", method=RequestMethod.GET)
	public String update(@PathVariable("no") String no, Model model) {
		BoardVo vo = boardService.getOneArticle(Long.parseLong(no));
		
		model.addAttribute("vo", vo);
		model.addAttribute("no", no);
		return "board/update";
	}

	@Auth
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public String update(BoardVo vo) {
		boardService.updateArticle(vo);
		return "redirect:/board";
	}
}
