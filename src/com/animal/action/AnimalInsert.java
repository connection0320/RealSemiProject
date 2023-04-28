package com.animal.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.animal.model.AnimalDAO;
import com.animal.model.AnimalDTO;
import com.member.model.MemberDAO;
import com.member.model.MemberDTO;

public class AnimalInsert implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int Member_num = Integer.parseInt(request.getParameter("no"));
		MemberDAO dao = MemberDAO.getInstance();
		MemberDTO dto =dao.contentByNum(Member_num);
		
		
		
		AnimalDTO Adto1 = null;
		AnimalDTO Adto2 = null;
		AnimalDTO Adto3 = null;
		
		
		if(dto.getMember_animal1() != 0) {
			AnimalDAO Adao = AnimalDAO.getInstance();
			Adto1 = Adao.contentByNum(dto.getMember_animal1());
			
		}
		
		if(dto.getMember_animal2() != 0) {
			AnimalDAO Adao = AnimalDAO.getInstance();
			Adto2 = Adao.contentByNum(dto.getMember_animal2());
			
		}
		
		if(dto.getMember_animal3() !=0) {
			AnimalDAO Adao = AnimalDAO.getInstance();
			Adto3 = Adao.contentByNum(dto.getMember_animal3());
			
		}
		
		request.setAttribute("Animal1", Adto1);
		request.setAttribute("Animal2", Adto2);
		request.setAttribute("Animal3", Adto3);
		return "a_insert.jsp";
	}

}
