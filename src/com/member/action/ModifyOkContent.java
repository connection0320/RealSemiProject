package com.member.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.member.model.MemberDAO;
import com.member.model.MemberDTO;

public class ModifyOkContent implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		MemberDTO dto = new MemberDTO();
		
		dto.setMember_num(Integer.parseInt(request.getParameter("no").trim()));
		dto.setMember_id(request.getParameter("id").trim());
		dto.setMember_pwd(request.getParameter("pwd").trim());
		dto.setMember_name(request.getParameter("name").trim());
		dto.setMember_age(Integer.parseInt(request.getParameter("age").trim()));
		dto.setMember_phone(request.getParameter("phone").trim());
		dto.setMember_email(request.getParameter("email").trim());
		dto.setMember_nick(request.getParameter("nick").trim());
		dto.setMember_self(request.getParameter("self").trim());
		
		MemberDAO dao = MemberDAO.getInstance();
		int res = dao.ModifyMember(dto);
		
		PrintWriter out = response.getWriter();
		
		if(res > 0) {
			out.println("<script>");
			out.println("alert('회원 정보 수정 완료')");
			out.println("location.href='content.member.do?id="+request.getParameter("id")+"'");
			out.println("</script>");
		}else {
			out.println("<script>");
			out.println("alert('회원 정보 수정 실패')");
			out.println("history.back()");
			out.println("</script>");
		}
		
		return null;
	}

}
