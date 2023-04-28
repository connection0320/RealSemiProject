package com.fri.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fri.model.FriDAO;
import com.fri.model.FriDTO;
import com.member.model.MemberDAO;
import com.member.model.MemberDTO;


public class FriListAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 친구 목록
		//요청 : 친구 리스트 보여달라
		//응답 : db에서 서로 친구 상태 (accept 상태:수락) 상태라면 테이블에 띄울 수 있게끔함
		
		MemberDAO mdao = MemberDAO.getInstance();
		String id = request.getParameter("id");		
		MemberDTO mem = mdao.contentById(id);
		int mnum = mem.getMember_num();	
		
		FriDAO fdao = FriDAO.getInstance();
		List<FriDTO> fList = fdao.FList(mnum);
		List<MemberDTO> memberList = new ArrayList();
		
		
		for(FriDTO dto : fList) {
			if(dto.getFriend_request() != 0) {
				// 내가 친구신청을 받아서 친구가 된 경우
				memberList.add((mdao.contentByNum(dto.getFriend_request())));
			}else if(dto.getFriend_response() != 0) {
				// 내가 친구신청을 보내서 친구가 된 경우
				memberList.add((mdao.contentByNum(dto.getFriend_response())));
			}
			
			System.out.println((mdao.contentByNum(dto.getFriend_response()).getMember_name()));
			System.out.println((mdao.contentByNum(dto.getFriend_response()).getMember_gender()));
		}
		
		request.setAttribute("List", memberList);
		
			}

}
