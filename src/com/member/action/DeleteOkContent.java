package com.member.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.member.model.MemberDAO;
import com.member.model.MemberDTO;

public class DeleteOkContent implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		int member_num = Integer.parseInt(request.getParameter("no"));
		String member_pwd = request.getParameter("pwd");
		
		MemberDAO dao = MemberDAO.getInstance();
		MemberDTO dto = dao.contentByNum(member_num);
		
		
		PrintWriter out = response.getWriter();
		
		if(dto.getMember_pwd().equals(member_pwd)) {
			dao.deleteMember(member_num);
			out.println("<script>");
			out.println("alert('계정 탈퇴 성공')");
			out.println("location.href='m_logout.jsp'");
			out.println("</script>");
		}else {
			out.println("<script>");
			out.println("alert('계정 탈퇴 실패')");
			out.println("history.back()");
			out.println("</script>");
		}
		
		return null;
	}

}
