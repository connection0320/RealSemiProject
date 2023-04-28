package com.member.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.animal.model.AnimalDAO;
import com.animal.model.AnimalDTO;
import com.member.model.MemberDAO;
import com.member.model.MemberDTO;

public class Match implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		
		
		HttpSession session = request.getSession();
		String loginid = (String)session.getAttribute("UserId");
		if(session.getAttribute("UserId") == null) {
			return "match_notlogined.jsp";
		}
		String location = request.getParameter("location");
		MemberDAO dao = MemberDAO.getInstance();
		
		int loginNum = dao.contentById(loginid).getMember_num();
		
		ArrayList<MemberDTO> list =  dao.getListMatch(location, loginNum);
		System.out.println(list);
		ArrayList<MemberDTO> result = new ArrayList<MemberDTO>();
		if(list.size() >= 3) {
			Collections.shuffle(list);
			for (int i = 0; i < 3; i++) {
		            result.add(list.get(i));
		        }
		}
	
		AnimalDAO adao = AnimalDAO.getInstance();
		AnimalDTO adto0 = adao.contentByNum(result.get(0).getMember_animal1());
		AnimalDTO adto1 = adao.contentByNum(result.get(1).getMember_animal1());
		AnimalDTO adto2 = adao.contentByNum(result.get(2).getMember_animal1());
		
		
		request.setAttribute("animal0", adto0);
		request.setAttribute("animal1", adto1);
		request.setAttribute("animal2", adto2);
        request.setAttribute("List", result);
       
		return "match_main.jsp";
	}

}
