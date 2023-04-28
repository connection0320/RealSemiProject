package com.animal.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.animal.action.*;


public class AFrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		// 한글 처리 작업 진행.
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		// getRequestURI() : "/프로젝트명/파일명(*.do)" 라는
		//                   문자열을 반환해 주는 메서드.
		String uri = request.getRequestURI();
		System.out.println("URI >>> " + uri);
		
		// getContextPath() : 현재 프로젝트명을 문자열로
		//                    반환해 주는 메서드.
		String path = request.getContextPath();
		System.out.println("Path >>> " + path);
		
		String command = uri.substring(path.length() + 1);
		System.out.println("Command >>> " + command);
		
		Action action = null;
		
		if(command.equals("AniamlInsert.no")) {
			action = new AnimalInsert(); // 회원가입
		}else if(command.equals("AniamlInsertOk.no")) {
			action = new AnimalInsertOk();
		}else if(command.equals("AnimalContent.no")) {
			action = new AnimalContent();
		}else if(command.equals("DeleteAnimal.no")) {
			action = new DeleteAnimal();
		}
		
		
		
		
		
		// path1 = "member/member_write.jsp";
		String path1 = 
				action.execute(request, response);
		
		RequestDispatcher rd =
				request.getRequestDispatcher(path1);
		
		rd.forward(request, response);
		
	}

}
