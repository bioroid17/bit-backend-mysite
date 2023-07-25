package com.bitacademy.mysite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bitacademy.mysite.vo.UserVo;

@Controller
public class MainController {

	@RequestMapping({"/", "/main"})
	public String index() {
		return "main/index";
	}
	
	@ResponseBody
	@RequestMapping("/msg01")
	public String message01(){
		return "<h1>Hello World";
	}
	
	@ResponseBody
	@RequestMapping("/msg02")
	public String message02(String name){
		return "<h1>안녕" + name + "</h1>";
	}
	
	@ResponseBody
	@RequestMapping("/msg03")
	public UserVo message03(){
		UserVo vo = new UserVo();
		vo.setNo(1L);
		vo.setName("둘리");
		vo.setEmail("dooly@gmail.com");
		
		// 바로 UserVo를 반환 시 에러 뜸
		return vo;
	}
}
