package com.fri.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fri.model.FriDAO;
import com.member.model.MemberDAO;
import com.member.model.MemberDTO;

public class FriAddAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

	    String m_Id = request.getParameter("id");
	    MemberDAO mdao = MemberDAO.getInstance();
	    MemberDTO logIn = mdao.contentById(m_Id);
	    FriDAO fdao = FriDAO.getInstance();
	    List<MemberDTO> otherMembers = fdao.OtherMembers(logIn.getMember_num());

	    request.setAttribute("logInMember", logIn);
	    request.setAttribute("otherMembers", otherMembers);

	}
	}


