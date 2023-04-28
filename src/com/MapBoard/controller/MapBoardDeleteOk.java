package com.MapBoard.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.MapBoard.model.MapBoardDAO;
import com.MapBoard.model.MapBoardDTO;


@WebServlet("/board_delete_ok.go")
public class MapBoardDeleteOk extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public MapBoardDeleteOk() {
        super();
    }

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		
		// 삭제 폼 페이지에서 넘어온 글번호를 가지고 DB에서 해당 게시글을 삭제하는 비지니스 로직.
		int no = Integer.parseInt(request.getParameter("no").trim());
		String location = request.getParameter("location").trim();
		MapBoardDAO dao = MapBoardDAO.getInstance();
		
		// 글번호에 해당하는 게시글의 상세내역 조회하는 메서드 호출.
		MapBoardDTO dto = dao.getContentMapBoard(no);

		// fileUpload 폴더에 업로드 된 첨부파일까지 삭제하자.
		String saveFolder = "C:\\Users\\user1\\Desktop\\RealSemiP\\WebContent\\file";

		// 상세내역에서 업로드 된 첨부파일을 가져오자.
		String fn = dto.getBoard_file();
		
		int board_no = Integer.parseInt(request.getParameter("no").trim());
		
		PrintWriter out = response.getWriter();
		
		if(fn != null) {
			// 첨부파일이 존재하는 경우
			File file = new File(saveFolder+fn);
			
			file.delete();		// 파일을 제거하는 메서드.
		}
		
		int check = dao.deleteMapBoard(no);
		
		if(check > 0) {
			dao.updateSequence(board_no);
			out.println("<script>");
			out.println("alert('게시글 삭제 성공')");
			out.println("location.href='board_list.go?location="+location+"'");
			out.println("</script>");
		} else {
			out.println("<script>");
			out.println("alert('게시글 삭제 실패')");
			out.println("history.back()");
			out.println("</script>");
		}
	}
		
}


