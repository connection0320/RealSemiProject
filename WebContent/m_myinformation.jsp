<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>펫만나</title>
<link rel="stylesheet" href="s.css/inff.css">
</head>
<body>
			<%@include file="header.jsp"%>
			
	<div class="container" align="center">
			<br>
		
	<%
		if(session.getAttribute("UserId") == null){
	%>
	
	
	    <script>
      window.location.replace("m_login.jsp");
    </script>

	
	<% 
	return;
		}else{
			String UserId = request.getParameter("id");
	%> 	
		<%-- 로그인 중이면 내정보 보여주기. --%>
		<div class="table_container">
			<table id="tion">
				<tr>
					<th> 회원 아이디 </th>
					<td> ${Dto.getMember_id()} </td>
				</tr>
				<tr>
					<th> 회원 닉네임</th>
					<td> ${Dto.getMember_nick()} </td>
				</tr>
				<tr>
					<th> 회원 나이</th>
					<td> ${Dto.getMember_age()} </td>
				</tr>
				<tr>
					<th> 회원 주소</th>
					<td> ${Dto.getMember_addr1()} ${Dto.getMember_addr2()} ${Dto.getMember_addr3()} </td>
				</tr>
				<tr class ="buts">
					<th> 첫째 </th>
					<c:if test="${Animal1 ne '애완동물 미등록'}">
						<td > 
								<a href="AnimalContent.no?no=${Animal1no}">${Animal1}</a>
	
						</td>
					</c:if>
					<c:if test="${Animal1 eq '애완동물 미등록'}">
						<td> ${Animal1}</td>
					</c:if>
				</tr>
				<tr class ="buts">
					<th> 둘째</th>
					<c:if test="${Animal2 ne '애완동물 미등록'}">
						<td> <a href="AnimalContent.no?no=${Animal2no}">${Animal2}</a></td>
					</c:if>
					<c:if test="${Animal2 eq '애완동물 미등록'}">
						<td> ${Animal2}</td>
					</c:if>
				</tr>
				<tr class ="buts">
					<th> 셋째 </th>
					<c:if test="${Animal3 ne '애완동물 미등록'}">
						<td> <a href="AnimalContent.no?no=${Animal3no}">${Animal3}</a></td>
					</c:if>
					<c:if test="${Animal3 eq '애완동물 미등록'}">
						<td> ${Animal3}</td>
					</c:if>
				</tr>
			
			<%-- <tr>
				<td colspan="2" align="center">
					<button>
						<a href ="fri_list.to?id=<%=(String)session.getAttribute("UserId")%>" id="a">
						 	친구 목록
						</a>
					</button>
					
					<button>
						<a href ="fri_acc.to?id=<%=(String)session.getAttribute("UserId")%>" id="b">
						친구 수락
						</a>
					</button>						
				</td>
			</tr> --%>
			</table>
		</div>
		<!--	<hr  style="border: solid 1px black" class="three_1">  -->
		
		
	<%} %>
	<br>
	<div align="center" id="button">
		<button onclick="location.href='fri_list.to?id=<%=(String)session.getAttribute("UserId")%>'">친구목록</button>
		
		<button  onclick="location.href='fri_acc.to?id=<%=(String)session.getAttribute("UserId")%>'">친구신청</button>
		
		 <button onclick="location.href='modify.member.do?no=${Dto.getMember_num()}'">정보수정</button>
		 
		 <button  onclick="window.open('delete.do?no=${Dto.getMember_num()}', '_blank','width=450,height=300')">회원탈퇴</button>
		 
		 <button onclick="location.href='AniamlInsert.no?no=${Dto.getMember_num()}'">동물등록</button>
	 </div>
	 
	 </div>

	<%@include file="/footer.jsp" %>
</body>
</html>