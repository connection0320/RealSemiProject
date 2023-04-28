package com.fri.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fri.action.Action;
import com.fri.model.FriDAO;
import com.member.model.MemberDAO;
import com.member.model.MemberDTO;

public class FriInsertMessage implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int fri_no = Integer.parseInt(request.getParameter("no"));
		HttpSession session = request.getSession();
		String userId = (String)session.getAttribute("UserId");
		
		MemberDAO mdao = MemberDAO.getInstance();
		MemberDTO mdto = mdao.contentById(userId);
		int mynum = mdto.getMember_num();
		
		String message = request.getParameter("message");
		
		FriDAO fdao = FriDAO.getInstance();
		fdao.insertMessage(mynum, fri_no, message);
		
		PrintWriter out = response.getWriter();
		out.println("<script>");
		out.println("location.href='fri_message.to?no="+fri_no+"'");
		out.println("</script>");
		
		
	}

}
