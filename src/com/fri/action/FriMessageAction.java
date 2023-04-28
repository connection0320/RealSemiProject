package com.fri.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fri.action.Action;
import com.fri.model.FriDAO;
import com.fri.model.MesDTO;
import com.member.model.MemberDAO;
import com.member.model.MemberDTO;

public class FriMessageAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		int f_num = Integer.parseInt(request.getParameter("no"));
		HttpSession session = request.getSession();
		String userId = (String)session.getAttribute("UserId");
		
		MemberDAO mdao = MemberDAO.getInstance();
		MemberDTO mdto = mdao.contentById(userId);
		int mynum = mdto.getMember_num();
		
		FriDAO fdao = FriDAO.getInstance();
		List<MesDTO> list = fdao.getMessage(mynum,f_num);
		
		for(MesDTO dto : list) {
			dto.setRequestName((mdao.contentByNum((dto.getMessage_request())).getMember_nick()));
			dto.setResponseName((mdao.contentByNum((dto.getMessage_response())).getMember_nick()));
		}
		
		request.setAttribute("List", list);
		request.setAttribute("Me", mynum);
		request.setAttribute("You", f_num);
		
		
	}

}
