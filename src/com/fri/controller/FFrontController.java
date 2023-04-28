package com.fri.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fri.action.*;


public class FFrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		// 한글 처리 작업 진행.
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		// getRequestURI() : "/프로젝트명/파일명(*.to)" 라는
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
		
		String viewPage = null;
		
		 if(command.equals("fri_list.to")) {
			 action= new FriListAction(); //친구 목록
			 action.execute(request, response);
			 viewPage="fri_list.jsp";
		}else if(command.equals("fri_add.to")) {
			 action= new FriAddAction(); //친구 추가
			 action.execute(request, response);
			 viewPage="fri_add.jsp";			 
		}else if(command.equals("fri_ok_add.to")) { 
			action= new FriAddOkAction(); //친구 추가 로직
			action.execute(request, response); 
		}else if(command.equals("fri_acc.to")) { 
			action= new FriAccAction(); //친구 폼 페이지
			action.execute(request, response); 
			viewPage="fri_acc.jsp";			 
		}else if(command.equals("fri_acc_ok.to")) { 
			action= new FriAccOkAction(); //친구 수락 비지니스 로직
			action.execute(request, response); 	
		}else if(command.equals("fri_delete.to")) {
			action = new FriDeleteAction();
			action.execute(request, response);
		}else if(command.equals("fri_message.to")) {
			action = new FriMessageAction();
			action.execute(request, response);
			viewPage = "fri_message.jsp";
		}else if(command.equals("fri_insertMessage.to")) {
			action = new FriInsertMessage();
			action.execute(request, response);
		}
		 
		
		
		
			RequestDispatcher rd = 
					request.getRequestDispatcher(viewPage);
		
				rd.forward(request, response);
		
	}
	
}
