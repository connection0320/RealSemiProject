package com.qna.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member.model.MemberDAO;
import com.member.model.MemberDTO;
import com.qna.model.QnaDAO;
import com.qna.model.QnaDTO;

public class QnaModifyAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// get 방식으로 넘어온 게시 글번호에 해당하는 상세내역을 DB에서 조회하여 수정 폼 페이지로 이동시키는 비지니스 로직.
		int qna_num = Integer.parseInt(request.getParameter("no").trim());
		int page = Integer.parseInt(request.getParameter("page").trim());
		QnaDAO dao = QnaDAO.getInstance();
		
		QnaDTO cont = dao.getQnaContent(qna_num);

		int writer = cont.getQna_writer();
		
		MemberDAO mdao = MemberDAO.getInstance();
		
		MemberDTO mdto = mdao.contentByNum(writer);
		String nick = mdto.getMember_nick();
		
		HttpSession session = request.getSession();
		String UserId = (String)session.getAttribute("UserId");
		
		request.setAttribute("Nick", nick);
		request.setAttribute("Modify", cont);
		request.setAttribute("User", UserId);
		request.setAttribute("page", page);
		
		ActionForward forward = new ActionForward();
		forward.setRedirect(false);
		forward.setPath("qna_modify.jsp");
		
		return forward;
	}
}