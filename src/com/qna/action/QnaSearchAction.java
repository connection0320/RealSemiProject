package com.qna.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.MapBoard.model.MapBoardDTO;
import com.member.model.MemberDAO;
import com.member.model.MemberDTO;
import com.qna.model.QnaDAO;
import com.qna.model.QnaDTO;

public class QnaSearchAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 검색 폼 페이지에서 넘어온 데이터를 가지고 검색어에 해당하는 게시글들을 Qna 테이블에서 조회하여 view page로 이동시키는 비지니스 로직.
		// ==> 동시에 페이징 작업 진행.
		String field = request.getParameter("field").trim();
		String keyword = request.getParameter("keyword").trim();
		String member_id = request.getParameter("id");
		String nick = request.getParameter("nick");
		MemberDAO mdao = MemberDAO.getInstance();
		MemberDTO mdto = mdao.contentById(member_id);
			
		int member_num = mdto.getMember_num();
		// 요청 : 게시물 전체 목록을 보여달라는 요청
		// 응답 : DB의 Qna 테이블의 전체 게시물 목록을 조회하여 view page로 이동시키는 비지니스 로직.
		// 비지니스 로직 진행 시 페이징 처리 작업까지 동시 진행.
		
//		QnaDAO dao = QnaDAO.getInstance();
//		List<QnaDTO> list = dao.getQnaList();
//		request.setAttribute("List", list);

		QnaDAO dao = QnaDAO.getInstance();

		// 현재 페이지에 해당하는 게시물을 가져오는 메서드 호출
		List<QnaDTO> searchList = dao.getSearchQnaList(field, keyword);
		for(QnaDTO dto : searchList) {
			 dto.setQna_nick((mdao.contentByNum(dto.getQna_writer())).getMember_nick());
		}
		
		// 지금까지 페이징 처리 시 작업했던 모든 데이터들을 view page로 이동을 시키자.
		request.setAttribute("field", field);
		request.setAttribute("keyword", keyword);
		request.setAttribute("List", searchList);
		request.setAttribute("Num", member_num);
		request.setAttribute("User", member_id);
//		
		
		
		
		ActionForward forward = new ActionForward();
		forward.setRedirect(false);
		forward.setPath("qna_search.jsp");
		
		return forward;
	}

}
