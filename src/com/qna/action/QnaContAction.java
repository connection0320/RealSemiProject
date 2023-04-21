package com.qna.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member.model.MemberDAO;
import com.member.model.MemberDTO;
import com.qna.model.QnaDAO;
import com.qna.model.QnaDTO;
import com.qna.model.QnaReplyDTO;

public class QnaContAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// get 방식으로 넘어온 글번호에 해당하는 게시글의 상세 내역을 DB에서 조회하여 view page로 이동시키는 비지니스 로직.
		int qna_num = Integer.parseInt(request.getParameter("num").trim());
		int page = Integer.parseInt(request.getParameter("page").trim());
		
		QnaDAO dao = QnaDAO.getInstance();
		
		// 조회수를 증가시켜 주는 메서드 호출
		dao.qnaHit(qna_num);
		
		// 글번호에 해당하는 게시글의 상세내역을 조회하는 메서드 호출
		QnaDTO cont = dao.getQnaContent(qna_num);
		
		int writer = cont.getQna_writer();
		
		MemberDAO mdao = MemberDAO.getInstance();
		
		MemberDTO mdto = mdao.contentByNum(writer);
		String nick = mdto.getMember_nick();
		
		HttpSession session = request.getSession();
		String userId = (String)session.getAttribute("UserId");
		
		List<QnaReplyDTO> list = dao.getReplyList(qna_num);
		
		
		request.setAttribute("User", userId);
		request.setAttribute("Nick", nick);
		request.setAttribute("Content", cont);
		request.setAttribute("Qlist", list);
		request.setAttribute("page", page);
		
		
		ActionForward forward = new ActionForward();
		forward.setRedirect(false);
		forward.setPath("qna_content.jsp");
		
		return forward;
	}
}