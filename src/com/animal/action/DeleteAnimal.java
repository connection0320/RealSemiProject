package com.animal.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.animal.model.AnimalDAO;
import com.animal.model.AnimalDTO;
import com.member.model.MemberDAO;
import com.member.model.MemberDTO;

public class DeleteAnimal implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		String Member_id = (String)session.getAttribute("UserId");
		MemberDAO mdao = MemberDAO.getInstance();
		MemberDTO mdto = mdao.contentById(Member_id);
		AnimalDAO adao = AnimalDAO.getInstance();	
		
		int Member_num = mdto.getMember_num();
		int Animal_num = Integer.parseInt(request.getParameter("no"));
		AnimalDTO adto = adao.contentByNum(Animal_num);
		int result = 0;
		if(mdto.getMember_animal1() == Animal_num) {
			// 첫번째 동물 삭제
		
			if(mdto.getMember_animal2() != 0 && mdto.getMember_animal3() != 0   ) {
				// 2번째,3번재 동물이 있음
				int a = 1; 
				result = adao.delete(Animal_num, mdto, a); 
			}
			
			if(mdto.getMember_animal2() != 0 && mdto.getMember_animal3() == 0 ) {
				// 2번째만 있음
				int a = 2; 
				result = adao.delete(Animal_num, mdto, a); 
			}
			
			if(mdto.getMember_animal2() == 0 && mdto.getMember_animal3() == 0   ) {
				// 1번재만 있었음 걍 삭제하면됨
				int a = 3;
				result = adao.delete(Animal_num, mdto, a); 
			}
			
		}else if(mdto.getMember_animal2() == Animal_num) {
			// 두번째 동물 삭제
			 
			if(mdto.getMember_animal3() != 0) {
				// 3번째 동물이 있음
				int a = 4; 
				result = adao.delete(Animal_num, mdto, a);
			}
			
			if(mdto.getMember_animal3() == 0) {
				// 3번째 동물이 없음 걍 삭제하면됨
				int a = 5; 
				result = adao.delete(Animal_num, mdto, a);
			}
			
			
		}else {
			// 3번째 동물 삭제 걍 삭제하면됨
			int a = 6; 
			result = adao.delete(Animal_num, mdto, a); 
		}
		
		
		// fileUpload 폴더에 업로드된 첨부파일까지 삭제하자.
	      String upload = "C:\\Users\\user1\\Desktop\\RealSemiP\\WebContent\\animal";
	      
	      // 상세내역에서 업로드된 첨부파일을 가져오자.
	      String fileName = adto.getAnimal_image();
	      

	      
	     
	       
	         
         if(fileName != null) {
            // 첨부파일이 존재하는 경우
            File file = new File(upload + fileName);
            file.delete();    // 파일을 제거하는 메서드
            
         }

		PrintWriter out = response.getWriter();
		
		if(result == 1) {
			adao.updateSequence(Animal_num);
			out.println("<script>");
			out.println("alert('동물 삭제 성공')");
			out.println("location.href='content.member.do?id="+Member_id+"'");
			out.println("</script>");
		}
		return null;
	}

}
