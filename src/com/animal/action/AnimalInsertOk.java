package com.animal.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.animal.model.AnimalDAO;
import com.animal.model.AnimalDTO;
import com.member.model.MemberDAO;
import com.member.model.MemberDTO;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;



public class AnimalInsertOk implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
				
		// 파일 업로드 시에는 설정해야 할 내용이 있음.
		// 1. 첨부 파일 저장 경로 지정.
		String saveFolder = "C:\\Users\\user1\\Desktop\\RealSemiP\\WebContent\\animal";
		
		// 2. 첨부 파일 크기 지정
		int fileSize = 10*1024*1024; // 10MB
		
		//3. MultipartRequest
		MultipartRequest multi = new MultipartRequest(request, saveFolder, fileSize, "UTF-8", new DefaultFileRenamePolicy());
		
		int have = Integer.parseInt(multi.getParameter("have"));
		String name = multi.getParameter("name");
		int age = Integer.parseInt(multi.getParameter("age"));
		String gender = multi.getParameter("gender");
		String race = multi.getParameter("race");
		String type = multi.getParameter("type");
		
		
		File upload_file = multi.getFile("image");
		
		if(upload_file != null) {
			String fileName = upload_file.getName();
			System.out.println("첨부파일이름 >>" + fileName);
			Calendar cal = Calendar.getInstance(); 
			int year = cal.get(Calendar.YEAR);
			int month = cal.get(Calendar.MONTH) + 1;
			int day = cal.get(Calendar.DAY_OF_MONTH);
			
			String homedir = saveFolder+"/"+year+"-"+month+"-"+day;
			// 날짜 폴더를 만들어 보자.
			File path1 = new File(homedir);
			
			if(!path1.exists()) { 	// 폴더가 존재하지 않는 경우
				path1.mkdir(); // 실제 폴더를 생성
			}
			
			// 파일을 만들어 보자 => 예) 홍길동_파일명
			String reFileName = name+"_"+fileName;
			
			upload_file.renameTo(new File(homedir+"/"+reFileName));
			
			// 실제로 DB에 저장되는 파일 이름.
			// "/2023-03-28/홍길동_파일명" 으로 저장할 예정.
			String fileDBName = "/"+year+"-"+month+"-"+day+"/"+reFileName;
			HttpSession session = request.getSession();
			String userId = (String)session.getAttribute("UserId");
			MemberDAO Mdao = MemberDAO.getInstance();
			MemberDTO Mdto = Mdao.contentById(userId);
			AnimalDTO dto = new AnimalDTO();
			
			dto.setAnimal_age(age);
			dto.setAnimal_gender(gender);
			dto.setAnimal_name(name);
			dto.setAnimal_race(race);
			dto.setAnimal_type(type);
			dto.setAnimal_image(fileDBName);
			
			AnimalDAO dao = AnimalDAO.getInstance();
			int check = dao.insertAnimal(Mdto ,dto, have);
			PrintWriter out = response.getWriter();
			
			if(check > 0) {
				out.println("<script>");
				out.println("alert('동물 등록 성공')");
				out.println("location.href='content.member.do?id="+Mdto.getMember_id()+"'");
				out.println("</script>");
			}else {
				out.println("<script>");
				out.println("alert('동물 등록 실패')");
				out.println("history.back()");
				out.println("</script>");
			}
			return null;
		}else {
			
			// 동물 이미지는 등록 안한 경우
			
			HttpSession session = request.getSession();
			String userId = (String)session.getAttribute("UserId");
			MemberDAO Mdao = MemberDAO.getInstance();
			MemberDTO Mdto = Mdao.contentById(userId);
			AnimalDTO dto = new AnimalDTO();
			
			dto.setAnimal_age(age);
			dto.setAnimal_gender(gender);
			dto.setAnimal_name(name);
			dto.setAnimal_race(race);
			dto.setAnimal_type(type);
			
			AnimalDAO dao = AnimalDAO.getInstance();
			int check = dao.insertAnimal(Mdto ,dto, have);
			PrintWriter out = response.getWriter();
			
			if(check > 0) {
				out.println("<script>");
				out.println("alert('동물 등록 성공')");
				out.println("location.href='content.member.do?id="+Mdto.getMember_id()+"'");
				out.println("</script>");
			}else {
				out.println("<script>");
				out.println("alert('동물 등록 실패')");
				out.println("history.back()");
				out.println("</script>");
			}
			return null;
			
		}
		
		
		
		
		
		
		
		
		
		
	}

}
