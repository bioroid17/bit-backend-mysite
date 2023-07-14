package com.bitacademy.mysite.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bitacademy.mysite.dao.BoardDao;
import com.bitacademy.mysite.vo.BoardVo;
import com.bitacademy.mysite.vo.UserVo;

public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		String actionName = request.getParameter("a");
		
		BoardDao dao = new BoardDao();
		
		if("writeform".equals(actionName)) {
			UserVo user = (UserVo) request.getSession().getAttribute("authUser");
			// Access Control
			///////////////////////////////////////////////////////////////
			if(user == null) {
				response.sendRedirect(request.getContextPath() + "/board");
				return;
			}
			///////////////////////////////////////////////////////////////
			request.getRequestDispatcher("/WEB-INF/views/board/writeform.jsp").forward(request, response);
		} else if("write".equals(actionName)) {
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			UserVo user = (UserVo) request.getSession().getAttribute("authUser");
			Long userNo = user.getNo();
			
			BoardVo vo = new BoardVo();
			vo.setTitle(title);
			vo.setContent(content);
			vo.setOrderNo(1L);
			vo.setDepth(0L);
			vo.setUserNo(userNo);
			
			dao.write(vo);

			response.sendRedirect(request.getContextPath() + "/board");
		} else if("updateform".equals(actionName)) {

			request.getRequestDispatcher("/WEB-INF/views/board/updateform.jsp").forward(request, response);
		} else if("update".equals(actionName)) {

			response.sendRedirect(request.getContextPath() + "/board");
		} else if("deleteform".equals(actionName)) {

			request.getRequestDispatcher("/WEB-INF/views/board/deleteform.jsp").forward(request, response);
		} else if("delete".equals(actionName)) {

			response.sendRedirect(request.getContextPath() + "/board");
		} else if("view".equals(actionName)) {
			String no = request.getParameter("no");
			
			BoardVo vo = dao.getArticle(no);
			
			request.setAttribute("vo", vo);

			request.getRequestDispatcher("/WEB-INF/views/board/view.jsp").forward(request, response);
		} else {
			/* default action */
			
			Long pageBlock = 5L;
			Long pageSize = 10L;
			
			String pageNum = null;
			Long count = dao.getCount();
			Long startNo = 0L;
			Long endNo = 0L;
			
			Long currentPage = 0L;
			Long pageCount = 0L;
			Long startPage = 0L;
			Long endPage = 0L;
			
			if(pageNum == null) { 
				pageNum = "1";
			}
			currentPage = Long.parseLong(pageNum);
			startNo = (currentPage - 1) * pageSize + 1;
			endNo = startNo + pageSize - 1;
			if (endNo > count) {
				endNo = count;
			}
			
			pageCount = (count / pageSize) + (count % pageSize == 0 ? 0L : 1L);
			startPage = (currentPage / pageBlock) * pageBlock + 1;
			if(currentPage % pageBlock == 0) {
				startPage -= pageBlock;
			}
			endPage = startPage + pageBlock - 1;
			if (endPage > pageCount) {
				endPage = pageCount;
			}

			List<BoardVo> list = dao.getArticles(startNo, endNo);
			request.setAttribute("list", list);
			
			request.getRequestDispatcher("/WEB-INF/views/board/list.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
