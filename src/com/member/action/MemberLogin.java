package com.member.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member.model.MemberDAO;

public class MemberLogin implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String id = request.getParameter("id").trim();
		String pwd = request.getParameter("pwd").trim();
		
		MemberDAO dao = MemberDAO.getInstance();
		
		int idcheck = dao.checkId(id);
		
		
		PrintWriter out = response.getWriter();
		if(idcheck == 1) {
			// 아이디는 존재
			int pwdCheck = dao.checkPwd(id, pwd);
			if(pwdCheck == 1) {
				// 로그인 성공
				
				HttpSession session = request.getSession();
				session.setAttribute("UserId", id);
				out.println("<script>");
				out.println("alert('로그인 성공')");
				out.println("location.href='main.jsp'");
				out.println("</script>");
			}else {
				// 비번 틀림
				out.println("<script>");
				out.println("alert('비밀번호가 틀립니다.')");
				out.println("history.back()");
				out.println("</script>");
			}
		}else {
			// 아이디 존재x
			out.println("<script>");
			out.println("alert('아이디가 존재하지 않습니다.')");
			out.println("history.back()");
			out.println("</script>");
		}
		
		return null;
	}

}
