package com.member.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.member.model.MemberDAO;
import com.member.model.MemberDTO;

public class DeleteContent implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int Member_num = Integer.parseInt((request.getParameter("no")));
		MemberDAO dao = MemberDAO.getInstance();
		MemberDTO dto = dao.contentByNum(Member_num);
		
		request.setAttribute("Dto", dto);
		
		
		return "m_delete.jsp";
	}

}
