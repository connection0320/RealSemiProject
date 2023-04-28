package com.qna.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.qna.model.QnaDAO;
import com.qna.model.QnaDTO;

public class QnaDeleteOkAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 삭제 폼 페이지에서 넘어온 글번호와 글 비밀번호를 가지고 qna 테이블에서 해당 게시글을 삭제하는 비지니스 로직.
		int qna_num = Integer.parseInt(request.getParameter("qna_num").trim());
		String qna_pwd = request.getParameter("pwd").trim();
		QnaDAO dao = QnaDAO.getInstance();
		
		// 글번호에 해당하는 게시글의 상세내역 조회하는 메서드 호출
		QnaDTO cont = dao.getQnaContent(qna_num);
		
		// fileUpload 폴더에 업로드된 첨부파일까지 삭제하자.
		String upload = "C:\\Users\\user1\\Desktop\\RealSemiP\\WebContent\\qnaFileUpload";
				
		// 상세내역에서 업로드된 첨부파일을 가져오자.
		String fileName = cont.getQna_file();
		PrintWriter out = response.getWriter();
		
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("UserId");
		
		if(qna_pwd.equals(cont.getQna_pwd())) {
			int check = dao.deleteQna(qna_num);
			
			if(fileName != null) {
				// 첨부파일이 존재하는 경우
				File file = new File(upload + fileName);
				file.delete(); // 파일을 제거하는 메서드.
			}
			
			if(check > 0) {
				out.println("<script>");
				out.println("alert('게시글 삭제 성공')");
				out.println("location.href='qna_list.so?id="+id+"'");
				out.println("</script>");
			} else {
				out.println("<script>");
				out.println("alert('게시글 삭제 실패')");
				out.println("history.back()");
				out.println("</script>");
			}
		} else {
			// 비밀번호가 틀린 경우
			out.println("<script>");
			out.println("alert('비밀번호가 틀립니다. 확인 요망')");
			out.println("history.back()");
			out.println("</script>");
		}
		
		return null;
	}
}