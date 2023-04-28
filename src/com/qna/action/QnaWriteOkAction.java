package com.qna.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member.model.MemberDAO;
import com.member.model.MemberDTO;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.qna.model.QnaDAO;
import com.qna.model.QnaDTO;

public class QnaWriteOkAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 글쓰기 폼 페이지에서 넘어온 데이터들을 qna_board 테이블에 저장하는 비지니스 로직.

		HttpSession session = request.getSession();
		String member_id = (String)session.getAttribute("UserId");
		MemberDAO mdao = MemberDAO.getInstance();
		MemberDTO mdto = mdao.contentById(member_id);
		
		QnaDTO dto = new QnaDTO();
		
		// 파일 업로드 시에는 설정해야 할 내용이 있음.
		// 1. 첨부 파일 저장 경로 지정.
		String saveFolder = "C:\\Users\\user1\\Desktop\\RealSemiP\\WebContent\\qnaFileUpload";
		
		// 2. 첨부 파일 크기 지정.
		int fileSize = 10 * 1024 * 1024; // 10MB
		
		// 3. MultipartRequest 객체 생성
		// ==> 파일 업로드를 진행하기 위한 객체 생성.
		MultipartRequest multi = 
				new MultipartRequest(
						request, // 일반적인 request 객체
						saveFolder, // 첨부파일이 저장될 경로
						fileSize, // 업로드할 첨부파일의 최대 크기
						"UTF-8", // 문자 인코딩 방식
						new DefaultFileRenamePolicy() // 첨부 파일의 이름이 같은 경우 중복이 안되게 설정.
						);
		
		// 자료실 폼 페이지에서 넘어온 데이터들을 받아 주자.
		String qna_head = multi.getParameter("field").trim();
		int qna_writer = mdto.getMember_num();
		String qna_title = multi.getParameter("qna_title").trim();
		String qna_cont = multi.getParameter("qna_cont").trim();
		String qna_pwd = multi.getParameter("qna_pwd").trim();
		

		if(qna_head.equals("propose")) {
			qna_head = "건의";
		} else if(qna_head.equals("account")) {
			qna_head = "계정";
		} else if(qna_head.equals("report")) {
			qna_head = "신고";
		} else if(qna_head.equals("else")) {
			qna_head = "기타";
		}
		
		// 자료실 폼 페이지에서 type = "file" 속성으로 되어 있으면 getFile() 메서드로 받아 주어야 함.
		File qna_file = multi.getFile("qna_file");

		if(qna_file != null) { // 첨부파일이 존재하는 경우
			// 우선은 첨부파일의 이름을 알아야 함.
			// getName() 메서드를 이용하면 이름을 알 수 있음.
			String fileName = qna_file.getName();
			System.out.println("첨부파일 이름 >>> " + fileName);
			
			// 날짜 객체 생성
			Calendar cal = Calendar.getInstance();
			int year = cal.get(Calendar.YEAR);
			int month = cal.get(Calendar.MONTH) + 1;
			int day = cal.get(Calendar.DAY_OF_MONTH);
			
			// ..../qnaFileUpload/2023-03-28
			String homedir = saveFolder + "/" + year + "-" + month + "-" + day;
			
			// 날짜 폴더를 만들어 보자.
			File path1 = new File(homedir);
			
			if(!path1.exists()) { // 폴더가 존재하지 않는 경우
				path1.mkdir(); // 실제 폴더를 만들어 주는 메서드.
			}
			
			// 파일을 만들어 보자 ==> 예) 홍길동_파일명
			String reFileName = qna_writer + "_" + fileName;
			qna_file.renameTo(new File(homedir+"/"+reFileName));
			
			// 실제로 DB에 저장되는 파일 이름.
			// "/2023-03-28/홍길동_파일명" 으로 저장 예정.
			String fileDBName = "/" + year + "-" + month + "-" + day + "/" + reFileName;
			dto.setQna_file(fileDBName);
		}
		
		dto.setQna_head(qna_head);
		dto.setQna_writer(qna_writer);
		dto.setQna_title(qna_title);
		dto.setQna_content(qna_cont);
		dto.setQna_pwd(qna_pwd);
		
		QnaDAO dao = QnaDAO.getInstance();
		int check = dao.insertQna(dto);
		PrintWriter out = response.getWriter();
		
		if(check > 0) {
			out.println("<script>");
			out.println("alert('게시글 등록 성공')");
			out.println("location.href='qna_list.so?id="+member_id+"'");
			out.println("</script>");
		} else {
			out.println("<script>");
			out.println("alert('게시글 등록 실패')");
			out.println("history.back()");
			out.println("</script>");
		}
		return null;
	}

}
