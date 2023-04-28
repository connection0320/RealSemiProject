package com.member.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.member.model.MemberDAO;
import com.member.model.MemberDTO;

public class MemberInsert implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		MemberDTO dto = new MemberDTO();
		
		dto.setMember_id(request.getParameter("id").trim());
		dto.setMember_pwd(request.getParameter("pwd").trim());
		dto.setMember_name(request.getParameter("name").trim());
		dto.setMember_age(Integer.parseInt(request.getParameter("age").trim()));
		dto.setMember_phone(request.getParameter("phone").trim());
		dto.setMember_addr1(request.getParameter("addr1").trim());
		dto.setMember_addr2(request.getParameter("addr2").trim());
		dto.setMember_addr3(request.getParameter("addr3").trim());
		dto.setMember_email(request.getParameter("email").trim());
		dto.setMember_nick(request.getParameter("nick").trim());
		dto.setMember_self(request.getParameter("self").trim());
		dto.setMember_gender(request.getParameter("gender").trim());
		
		MemberDAO dao = MemberDAO.getInstance();
		int res = dao.insertMember(dto);
		
		PrintWriter out = response.getWriter();
		if(res > 0) {
			out.println("<script>");
			out.println("alert('회원 가입 성공')");
			out.println("location.href='m_login.jsp'");
			out.println("</script>");
		}else {
			out.println("<script>");
			out.println("alert('회원 가입 실패')");
			out.println("history.back()");
			out.println("</script>");
		}
		return null;
	}

}
