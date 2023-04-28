package com.member.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.member.model.MemberDAO;
import com.member.model.MemberDTO;

public class FindId implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String name = request.getParameter("name").trim();
		String phone = request.getParameter("phone").trim();
		System.out.println(name + phone);
		
		
		MemberDAO dao = MemberDAO.getInstance();
		MemberDTO dto = dao.findId(name, phone);
		int result = 0;
		
		if(dto != null) {
			result = 1;
		}
		
		
		PrintWriter out = response.getWriter();
		
		if(result == 0) {
			out.println("<script>");
			out.println("alert('등록된 회원정보가 없습니다.')");
			out.println("history.back()");
			out.println("</script>");
		}else {
			// 이름, 폰번호가 있는경우
			out.println("<script>");
			out.println("alert('해당 정보 회원의 ID는 "+dto.getMember_id()+" 입니다.')");
			out.println("location.href ='m_login.jsp'");
			out.println("</script>");
		}
		return null;
	}

}
