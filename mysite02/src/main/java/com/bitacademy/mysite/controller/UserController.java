package com.bitacademy.mysite.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UserController
 */
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String actionName = request.getParameter("a");
		if ("joinform".equals(actionName)) {
			request
				.getRequestDispatcher("/WEB-INF/views/user/joinform.jsp")
				.forward(request, response);
		} else if ("joinsuccess".equals(actionName)) {
			request
			.getRequestDispatcher("/WEB-INF/views/user/joinsuccess.jsp")
			.forward(request, response);
		} else if ("join".equals(actionName)) {
			// 파라미터 뽑아내기
			// dao.insert(vo)
			
			response.sendRedirect(request.getContextPath() + "/user?a=joinsuccess");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
