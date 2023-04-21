package com.qna.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.member.model.MemberDAO;
import com.member.model.MemberDTO;
import com.qna.model.QnaDAO;
import com.qna.model.QnaDTO;

public class QnaListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		// qna_board 테이블의 전체 게시물을 조회하여 view page로 이동시키는 비지니스 로직.
		
		String member_id = request.getParameter("id");
		MemberDAO mdao = MemberDAO.getInstance();
		MemberDTO mdto = mdao.contentById(member_id);
			
		int member_num = mdto.getMember_num();
		
		// 웹페이지당 보여질 게시물의 수
		int rowsize = 15;
		
		// 아래에 보여질 페이지 최대 블럭 수
		int block = 5;
		
		// DB상의 게시물의 수
		int totalRecord = 0;
		
		// 전체 페이지 수
		int allpage = 0;
		
		// 현재 페이지 변수
		int page = 0;
		
		if(request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page").trim());
		} else {
			// 처음으로 "전체 게시글 목록" a 태그를 클릭한 경우
			page = 1;
		}
		
		// 해당 페이지에서 시작 번호
		int startNo = (page * rowsize) - (rowsize - 1);
		
		// 해당 페이지에서 끝 번호
		int endNo = (page * rowsize);
		
		// 해당 페이지에서 시작 블럭
		int startBlock = (((page-1) /block)*block) + 1;
		
		// 해당 페이지에서 마지막 블럭
		int endBlock = (((page-1) /block)*block) + block;
		
		QnaDAO dao = QnaDAO.getInstance();
		
		// 전체 게시물의 수를 확인하는 메서드 호출
		totalRecord = dao.getQnaCount();
		
		// 전체 게시물의 수를 한 페이지당 보여질 게시물의 수로 나누어 주어야 함.
		// 이 과정을 거치면 전체 페이지 수가 나오게 됨.
		// 이 때 전체 페이지 수가 나올 때 나머지가 있으면 무조건 전체 페이지 수를 하나 올려주어야 한다.
		allpage = (int)Math.ceil(totalRecord/(double)rowsize);
		
		if(endBlock > allpage) {
			endBlock = allpage;
		}
				

		List<QnaDTO> pageList = dao.getQnaList();
		// ArrayList<String> nickList = new ArrayList<String>();
		for(QnaDTO qdto : pageList) {
			// nickList.add((mdao.contentByNum(qdto.getQna_writer())).getMember_nick());
			qdto.setQna_nick((mdao.contentByNum(qdto.getQna_writer())).getMember_nick());
		}
		
		request.setAttribute("page", page);
		request.setAttribute("rowsize", rowsize);
		request.setAttribute("block", block);
		request.setAttribute("totalRecord", totalRecord);
		request.setAttribute("allpage", allpage);
		request.setAttribute("startNo", startNo);
		request.setAttribute("endNo", endNo);
		request.setAttribute("startBlock", startBlock);
		request.setAttribute("endBlock", endBlock);
		request.setAttribute("List", pageList);
		request.setAttribute("Num", member_num);
		ActionForward forward = new ActionForward();
		forward.setRedirect(false);
		forward.setPath("qna_list.jsp");
		
		return forward;
	}
}