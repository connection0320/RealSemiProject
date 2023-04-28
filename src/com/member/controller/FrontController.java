package com.member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.member.action.*;


public class FrontController extends HttpServlet {
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
		
		if(command.equals("insert.member.do")) {
			action = new MemberInsert(); // 회원가입
		}else if(command.equals("login.member.do")) {
			action = new MemberLogin(); // 로그인
		}else if(command.equals("content.member.do")) {
			action = new MemberContent();  // 개인정보
		}else if(command.equals("idcheck.member.do")) {
			action =new MemberIdCheck();
		}else if(command.equals("modify.member.do")) {
			action = new ModifyContent();
		}else if(command.equals("modify.ok.member.do")) {
			action = new ModifyOkContent();
		}else if(command.equals("delete.do")) {
			action = new DeleteContent();
		}else if(command.equals("delete.ok.do")) {
			action = new DeleteOkContent();
		}else if(command.equals("member_match.do")) {
			action = new Match();
		}else if(command.equals("findId.member.do")) {
			action = new FindId();
		}else if(command.equals("findPwd.member.do")) {
			action = new FindPwd();
		}
		
		
		
		
		
		// path1 = "member/member_write.jsp";
		String path1 = 
				action.execute(request, response);
		
		RequestDispatcher rd =
				request.getRequestDispatcher(path1);
		
		rd.forward(request, response);
		
	}

}
