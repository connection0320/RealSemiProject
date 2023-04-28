package com.MapBoard.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.MapBoard.model.MapBoardDAO;
import com.MapBoard.model.MapBoardDTO;
import com.member.model.MemberDAO;
import com.member.model.MemberDTO;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

@WebServlet("/board_modify_ok.go")
public class MapBoardModifyOk extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public MapBoardModifyOk() {
        super();
    }

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 수정 폼 페이지에서 넘어온 수정 정보들을 MapBoard 테이블에 수정시키는 비지니스 로직.
		// 한글 처리 작업 진행.
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		MapBoardDTO dto = new MapBoardDTO();
		
		// 파일 업로드 시에는 설정해야 할 내용이 있음.
		// 1. 첨부파일 저장 경로 지정.
		String saveFolder = "C:\\Users\\user1\\Desktop\\RealSemiP\\WebContent\\file";
		
		// 2. 첨부파일 크기 지정.
		int fileSize = 10*1024*1024;		// 10MB
		
		// 3. MultipartRequest 객체 생성
		//    ==> 파일 업로드를 진행하기 위한 객체 생성.
		// request : 일반적인 request 객체
		// saveDirectory : 첨부타일이 저장될 경로
		// maxPostSize : 업로드할 첨부파일의 최대 크기
		// "UTF-8" : 문자 인코딩 방식
		// new DefaultFileRenamePolicy() : 첨부 파일의 이름이 같은 경우 중복이 안되게 설정
		MultipartRequest mr = new MultipartRequest(request, saveFolder, fileSize, "UTF-8", new DefaultFileRenamePolicy());
		
		
		// 1단계 : 게시글 입력 폼페이지에서 넘어온 데이터들을 받아 주자.
		HttpSession session = request.getSession();
		String member_id = (String)session.getAttribute("UserId");
		MemberDAO mdao = MemberDAO.getInstance();
		MemberDTO mdto = mdao.contentById(member_id);
		
		String field = mr.getParameter("field").trim();
		int writer = mdto.getMember_num();
		String title = mr.getParameter("title").trim();
		String cont = mr.getParameter("cont").trim();
		
		// 자료실 폼 페이지에서 type="file" 속성으로 되어있으면 getFile() 메서드로 받아 주어야 함.
		File file = mr.getFile("file");
		
		// type="hidden"으로 넘어온 정보도 받아주어야 한다.
		int no = Integer.parseInt(mr.getParameter("no"));
		
		if(file != null) {		// 첨부 파일이 존재한다면
			// 우선 첨부 파일의 이름을 알아야 함.
			// getName() 메서드를 이용하면 이름을 알 수 있다.
			String fn = file.getName();
			System.out.println("첨부파일 이름 : "+fn);
			
			// 날짜 객체 생성
			Calendar cal = Calendar.getInstance();
			
			int year = cal.get(Calendar.YEAR);
			int month = cal.get(Calendar.MONTH)+1;
			int day = cal.get(Calendar.DAY_OF_MONTH);
			
			// .../fileUpload/2023-03-28
			String homedir = saveFolder+"/"+year+"-"+month+"-"+day;
			
			// 날짜 폴더를 만들어 보자.
			File path1 = new File(homedir);
			
			if(!path1.exists()) {		// 폴더가 존재하지 않는 경우
				path1.mkdir();			// 실제 폴더를 만들어주는 메서드
			}
			
			// 파일을 만들어 보자 ==> 예) 홍길동_파일명
			String refn = writer+"_"+fn;
			
			file.renameTo(new File(homedir+"/"+refn));
			
			// 실제로 DB에 저장되는 이름.
			// "/2023-03-28/홍길동_파일명" 으로 저장 예정.
			String fDBn = "/"+year+"-"+month+"-"+day+"/"+refn;
			
			dto.setBoard_file(fDBn);
			
		}
		
		if(field.equals("dona")) {
			field = "나눔";
		} else if(field.equals("boast")) {
			field = "자랑";
		} else if(field.equals("share")) {
			field = "정보 공유";
		} else if(field.equals("free")) {
			field = "자유글";
		}
		
		dto.setBoard_num(no);
		dto.setBoard_head(field);
		dto.setBoard_writer(writer);
		dto.setBoard_title(title);
		dto.setBoard_text(cont);
		
		// DTO 객체를 DB에 전송해주어야 함.
		MapBoardDAO dao = MapBoardDAO.getInstance();
		
		int check = dao.modiftMapBoard(dto);
		int page = Integer.parseInt(request.getParameter("page").trim());
		
		PrintWriter out = response.getWriter();
		
		if(check > 0) {
			out.println("<script>");
			out.println("alert('게시글 수정 성공')");
			out.println("location.href='board_content.go?no="+dto.getBoard_num()+"&page="+page+"'");
			out.println("</script>");
		} else {
			out.println("<script>");
			out.println("alert('게시글 수정 실패')");
			out.println("history.back()");
			out.println("</script>");
		}
	}

}
