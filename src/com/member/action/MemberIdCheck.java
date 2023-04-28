package com.member.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.member.model.MemberDAO;
import com.member.model.MemberDTO;

public class MemberIdCheck implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		MemberDAO dao = MemberDAO.getInstance();
		 
		String member_Id = request.getParameter("checkId").trim();
		
		int check = dao.checkId(member_Id);
		
		PrintWriter out=response.getWriter();
		
		/*
		 * if(check == ) { out.println("<script>");
		 * out.println("alert('아이디를 다시 입력해주세요')"); out.println("history.back()");
		 * out.println("</script>"); }else
		 */ 
		if(check == 1){
			out.println("<script>");
			out.println("alert('사용 불가능한 아이디입니다.')");
			out.println("history.back()");				
			out.println("</script>");
		}else if(check == 0){
			out.println("<script>");
			out.println("alert('사용 가능한 아이디입니다.')");
			out.println("history.back()");				
			out.println("</script>");	
		}else{
			out.println("<script>");
			out.println("alert('다시 입력해주세요.')");
			out.println("history.back()");				
			out.println("</script>");	

		}
		
		return null;
	}
}


