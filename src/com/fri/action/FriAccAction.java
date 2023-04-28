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

import oracle.net.aso.s;

public class FriAccAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 친구 수락시 받은 정보를 입력 받아서 친구 상태를 만든다
		MemberDAO mdao = MemberDAO.getInstance();		
		FriDAO fdao = FriDAO.getInstance();

		String id =
				request.getParameter("id");
		
		MemberDTO log =  mdao.contentById(id);
		int logno = log.getMember_num();
		
		List<FriDTO> list = fdao.acc(logno); 
		List<MemberDTO> mlist = new ArrayList<MemberDTO>();
		
		for(FriDTO dto : list) {
			MemberDTO mdto = mdao.contentByNum((dto.getFriend_request()));
			mlist.add(mdto);
		}
		
		request.setAttribute("list", mlist);
	

		
		
/*//
 * <input type ="button" value = "신청 수락"
onclick ="location.href=<%=request.getContextPath() %>/fir_acc.to?no= ${fdto.request_accept}">

* 현재 프렌드 list에 있음
*/
//select * from friend where friend_response = '로그인 한 사람' and request_wait = '1';  // 나한테 친구신청이 온사람 (아직 친구아님)

		
///update frined set request_wait = '0', request_accept = '1' where friend_response = '로그인 한 사람' and friend_request = '건사람'; // 친구 신청 온 사람중 내가 친구 수락할때 sql문
		
		
		
	
	}
	

}
