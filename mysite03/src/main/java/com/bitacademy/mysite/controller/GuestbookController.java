package com.bitacademy.mysite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bitacademy.mysite.service.GuestbookService;
import com.bitacademy.mysite.vo.GuestbookVo;

@Controller
@RequestMapping("/guestbook")
public class GuestbookController {
	
	@Autowired
	private GuestbookService guestbookService;


	@RequestMapping("/list")
	public String list(Model model) {
		List<GuestbookVo> list = guestbookService.getMessageList();
		int count = list.size();
		
		model.addAttribute("list", list);
		model.addAttribute("count", count);
		
		return "guestbook/list";
	}

	@RequestMapping("/add")
	public String add(GuestbookVo vo) {
		guestbookService.addMessage(vo);
		
		return "redirect:/guestbook/list";
	}

	@RequestMapping(value="/delete", method=RequestMethod.GET)
	public String delete(String no, Model model) {

		model.addAttribute("no", no);
		return "guestbook/delete";
	}

	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public String delete(String no, String password, Model model) {
		boolean equal = guestbookService.check(no, password);
		if (equal){
			guestbookService.deleteMessage(no);
			return "redirect:/guestbook/list";
		} else {
			model.addAttribute("status", "비밀번호가 일치하지 않습니다.");
			model.addAttribute("no", no);
			return "guestbook/delete";
		}
		
	}
}
