package com.bitacademy.mysite.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bitacademy.mysite.dao.BoardDao;
import com.bitacademy.mysite.dao.UserDao;
import com.bitacademy.mysite.vo.BoardVo;
import com.bitacademy.mysite.vo.UserVo;

public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String actionName = request.getParameter("a");
		
		BoardDao dao = new BoardDao();
		
		if("writeform".equals(actionName)) {
			// Access Control
			///////////////////////////////////////////////////////////////
			UserVo user = (UserVo) request.getSession().getAttribute("authUser");
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
			Long no = Long.parseLong(request.getParameter("no"));
			BoardVo vo = dao.getArticle(no);
			// Access Control
			///////////////////////////////////////////////////////////////
			UserVo user = (UserVo) request.getSession().getAttribute("authUser");
			if(user == null || user.getNo() != vo.getUserNo()) {
				response.sendRedirect(request.getContextPath() + "/board");
				return;
			}
			///////////////////////////////////////////////////////////////
			request.setAttribute("vo", vo);
			request.setAttribute("no", no);

			request.getRequestDispatcher("/WEB-INF/views/board/updateform.jsp").forward(request, response);
		} else if("update".equals(actionName)) {
			BoardVo vo = new BoardVo();
			Long no = Long.parseLong(request.getParameter("no"));
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			
			vo.setNo(no);
			vo.setTitle(title);
			vo.setContent(content);
			
			dao.update(vo);

			response.sendRedirect(request.getContextPath() + "/board");
		} else if("replyform".equals(actionName)) {
			Long no = Long.parseLong(request.getParameter("no"));
			BoardVo vo = dao.getArticle(no);
			// Access Control
			///////////////////////////////////////////////////////////////
			if(request.getSession().getAttribute("authUser") == null) {
				response.sendRedirect(request.getContextPath() + "/board");
				return;
			}
			///////////////////////////////////////////////////////////////
			request.setAttribute("vo", vo);
			request.setAttribute("no", no);
			
			request.getRequestDispatcher("/WEB-INF/views/board/replyform.jsp").forward(request, response);
			
		} else if("reply".equals(actionName)) {
			Long no = Long.parseLong(request.getParameter("no"));
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			UserVo user = (UserVo) request.getSession().getAttribute("authUser");
			Long userNo = user.getNo();
			
			BoardVo parent = dao.getArticle(no);
			
			Long groupNo = parent.getGroupNo();
			Long orderNo = parent.getOrderNo()+1;
			Long depth = parent.getDepth()+1;
			
			BoardVo child = new BoardVo();
			child.setTitle(title);
			child.setContent(content);
			child.setGroupNo(groupNo);
			child.setOrderNo(orderNo);
			child.setDepth(depth);
			child.setUserNo(userNo);
			
			dao.reply(child);
			

		} else if("deleteform".equals(actionName)) {
			Long no = Long.parseLong(request.getParameter("no"));
			BoardVo vo = dao.getArticle(no);
			// Access Control
			///////////////////////////////////////////////////////////////
			UserVo user = (UserVo) request.getSession().getAttribute("authUser");
			if(user == null || user.getNo() != vo.getUserNo()) {
				response.sendRedirect(request.getContextPath() + "/board");
				return;
			}
			///////////////////////////////////////////////////////////////
			String status = request.getParameter("status");
			
			request.setAttribute("no", no);
			request.setAttribute("userNo", vo.getUserNo());
			request.setAttribute("status", status);
			request.getRequestDispatcher("/WEB-INF/views/board/deleteform.jsp").forward(request, response);
		} else if("delete".equals(actionName)) {
			String no = request.getParameter("no");
			String userNo = request.getParameter("userNo");
			String password = request.getParameter("password");
			
			boolean equal = new UserDao().checkPassword(userNo, password);
			if (equal) {
				dao.delete(no);
				response.sendRedirect(request.getContextPath() + "/board");
				return;
			} else {
				response.sendRedirect(request.getContextPath() + "/board?a=deleteform&no="+no+"&status=fail");
				return;
			}

		} else if("view".equals(actionName)) {
			Long no = Long.parseLong(request.getParameter("no"));
			
			BoardVo vo = dao.getArticle(no);
			dao.plusHit(no);
			
			request.setAttribute("vo", vo);
			request.setAttribute("no", no);

			request.getRequestDispatcher("/WEB-INF/views/board/view.jsp").forward(request, response);
		} else {
			/* default action */
			
			Long pageBlock = 5L;
			Long pageSize = 10L;
			
			String p = null;	// page번호
			Long articleCount = dao.getCount();
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

			List<BoardVo> list = dao.getArticles(startNo, endNo);
			request.setAttribute("list", list);
			
			request.setAttribute("startNo", startNo);
			request.setAttribute("endNo", endNo);
			request.setAttribute("currentPage", currentPage);
			request.setAttribute("startPage", startPage);
			request.setAttribute("endPage", endPage);
			
			request.getRequestDispatcher("/WEB-INF/views/board/list.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
