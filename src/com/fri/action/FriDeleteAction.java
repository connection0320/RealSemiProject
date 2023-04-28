package com.fri.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fri.model.FriDAO;
import com.member.model.MemberDAO;
import com.member.model.MemberDTO;

public class FriDeleteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int fnum = Integer.parseInt(request.getParameter("no"));
		HttpSession session = request.getSession();
		String userId = (String)session.getAttribute("UserId");
		
		MemberDAO mdao = MemberDAO.getInstance();
		MemberDTO mdto = mdao.contentById(userId);
		int mynum = mdto.getMember_num();
		
		FriDAO fdao = FriDAO.getInstance();
		int check = fdao.delete(mynum, fnum);
		
		PrintWriter out = response.getWriter();
	    
	    if(check == 0) {
	    	// 친구삭제실패
	    	out.println("<script>");
	    	out.println("alert('친구 삭제 실패')");
	    	out.println("history.back()");	    	
	    	out.println("</script>");
	    }else if(check == 1) {
	    	out.println("<script>");
	    	out.println("alert('친구 삭제 성공')");
	    	out.println("location.href='fri_list.to?id="+userId+"'");
	    	out.println("</script>");
	    }

	}

}
