package com.fri.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fri.model.FriDAO;
import com.member.model.MemberDAO;
import com.member.model.MemberDTO;

public class FriAccOkAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// friacc = 친구 수락창에서 넘어온 클라이언트가 수락버튼을 누를 경우 넘어갈 페이지의 비지니스 로직
		int fnum = Integer.parseInt(request.getParameter("no"));
		MemberDAO mdao = MemberDAO.getInstance();
		
		HttpSession sesion = request.getSession();
		String id =(String)sesion.getAttribute("UserId");
		MemberDTO log =  mdao.contentById(id);
		int logno = log.getMember_num();
		
		
		FriDAO fdao = FriDAO.getInstance();
		
		
		int result = fdao.fri_acc(logno, fnum);
		//fri_list.to?id
		PrintWriter out = response.getWriter();
		if(result == 0) {
			// 친구수락 실패
			out.println("<script>");
			out.println("alert('친구 수락 실패')");
			out.println("history.back()");
			out.println("</script>");
		}else {
			// 친구수락 성공
			out.println("<script>");
			out.println("alert('친구 수락 성공')");
			out.println("location.href='fri_list.to?id="+id+"'");
			out.println("</script>");
		}
		
		
	}

}
