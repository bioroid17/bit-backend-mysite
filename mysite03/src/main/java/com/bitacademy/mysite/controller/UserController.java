package com.bitacademy.mysite.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bitacademy.mysite.service.UserService;
import com.bitacademy.mysite.vo.UserVo;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/join", method=RequestMethod.GET)
	public String join() {
		// return 값은 ViewResolver로 간다.
		return "user/join";
	}
	
	@RequestMapping(value="/join", method=RequestMethod.POST)
	public String join(UserVo vo) {
		System.out.println(vo);
		userService.addUser(vo);
		return "redirect:/user/joinsuccess";
	}
	
	@RequestMapping("/joinsuccess")
	public String joinsuccess() {
		return "user/joinsuccess";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login() {
		return "user/login";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String login(HttpSession session, UserVo vo, Model model) {
		UserVo authUser = userService.getUser(vo);
		
		if(authUser == null) {
			model.addAttribute("result", "fail");
			return "user/login";
		}
		/* 로그인 처리 */
		session.setAttribute("authUser", authUser);
		return "redirect:/";
	}

	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		// 접근 제어
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/";
		}
		////////////////////////////////////////////////////////////
		
		/* 로그아웃 처리 */
		session.removeAttribute("authUser");
		session.invalidate();
		return "redirect:/";
	}
	
	@RequestMapping(value="/update", method=RequestMethod.GET)
	public String update(HttpSession session, Model model) {
		// 접근 제어
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/";
		}
		////////////////////////////////////////////////////////////
		Long no = authUser.getNo();
		UserVo userVo = userService.getUser(no);
		model.addAttribute("userVo", userVo);
		
		return "user/update";
	}
	
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public String update(HttpSession session, UserVo vo) {
		// 접근 제어
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/";
		}
		////////////////////////////////////////////////////////////
		vo.setNo(authUser.getNo());
		userService.updateUser(vo);
		
		authUser.setName(vo.getName());
		
		return "redirect:/user/update";
	}
}
