package com.animal.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.animal.model.AnimalDAO;
import com.animal.model.AnimalDTO;

public class AnimalContent implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int animal_no = Integer.parseInt(request.getParameter("no"));
		AnimalDAO dao = AnimalDAO.getInstance();
		AnimalDTO dto = dao.contentByNum(animal_no);
		
		request.setAttribute("dto", dto);
		return "a_content.jsp";
	}

}
