package com.fri.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fri.model.FriDAO;
import com.member.model.MemberDAO;
import com.member.model.MemberDTO;

public class FriAddOkAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		//실질적으로 친구를 추가 요청하는 비지니스 로직
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("UserId");
	    int frindno = Integer.parseInt(request.getParameter("no"));
	    MemberDAO mdao = MemberDAO.getInstance();
	    MemberDTO logIn = mdao.contentById(id);
	    int loginno = logIn.getMember_num();
	    MemberDTO frind = mdao.contentByNum(frindno);
	    FriDAO fdao = FriDAO.getInstance();
	    
	    
	    int check = fdao.fri_checkfriend(loginno, frindno);
	    
	    PrintWriter out = response.getWriter();
	    
	    if(check == 0) {
	    	fdao.fri_request(loginno, frindno);
	    	out.println("<script>");
	    	out.println("alert('친구 신청 성공')");
	    	out.println("history.back()");	    	
	    	out.println("</script>");
	    }else if(check == 1) {
	    	out.println("<script>");
	    	out.println("alert('이미 친구이거나, 친구신청 대기중인 상대방입니다.')");
	    	out.println("history.back()");
	    	out.println("</script>");
	    }
	    

	    
	}

}
