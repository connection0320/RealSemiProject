package com.member.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.animal.model.AnimalDAO;
import com.animal.model.AnimalDTO;
import com.member.model.MemberDAO;
import com.member.model.MemberDTO;

public class MemberContent implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		String id = request.getParameter("id");
		
		MemberDAO dao = MemberDAO.getInstance();
		MemberDTO dto = dao.contentById(id);
		
		String Animal1 ="애완동물 미등록";
		String Animal2 ="애완동물 미등록";
		String Animal3 ="애완동물 미등록";
		int Animal1no = 0;
		int Animal2no = 0;
		int Animal3no = 0;
		
		if(dto.getMember_animal1() != 0) {
			AnimalDAO Adao = AnimalDAO.getInstance();
			AnimalDTO Adto = Adao.contentByNum(dto.getMember_animal1());
			Animal1 = Adto.getAnimal_name();
			Animal1no = Adto.getAnimal_num();
		}
		
		if(dto.getMember_animal2() != 0) {
			AnimalDAO Adao = AnimalDAO.getInstance();
			AnimalDTO Adto = Adao.contentByNum(dto.getMember_animal2());
			Animal2 = Adto.getAnimal_name();
			Animal2no = Adto.getAnimal_num();
		}
		
		if(dto.getMember_animal3() !=0) {
			AnimalDAO Adao = AnimalDAO.getInstance();
			AnimalDTO Adto = Adao.contentByNum(dto.getMember_animal3());
			Animal3 = Adto.getAnimal_name();
			Animal3no = Adto.getAnimal_num();
		}
		
		
		request.setAttribute("Animal1", Animal1);
		request.setAttribute("Animal1no", Animal1no);
		request.setAttribute("Animal2", Animal2);
		request.setAttribute("Animal2no", Animal2no);
		request.setAttribute("Animal3", Animal3);
		request.setAttribute("Animal3no", Animal3no);
		request.setAttribute("Dto", dto);
		return "m_myinformation.jsp";
	}

}
