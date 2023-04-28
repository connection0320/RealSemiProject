package com.qna.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qna.model.QnaDAO;
import com.qna.model.QnaReplyDTO;

public class QnaReplyInsertOkAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 답변글 폼에서 넘어온 데이터들을 가지고 DB에 저장하는 비지니스 로직.
		
		String reply_writer = request.getParameter("re_writer").trim();
		String reply_cont = request.getParameter("re_content").trim();
		int reply_qno = Integer.parseInt(request.getParameter("qno").trim());
		int page = Integer.parseInt(request.getParameter("page").trim());
		
		QnaReplyDTO dto = new QnaReplyDTO();
		
		dto.setQna_bno(reply_qno);
		dto.setQna_rewriter(reply_writer);
		dto.setQna_recont(reply_cont);
		
		QnaDAO dao = QnaDAO.getInstance();
		dao.replyInsert(dto);
		
		
		ActionForward forward = new ActionForward();
		forward.setRedirect(false);
		forward.setPath("qna_content.so?num="+reply_qno+"&page="+page);
		
		return forward;
	}

}
