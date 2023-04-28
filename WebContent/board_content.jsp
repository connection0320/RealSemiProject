<%@page import="com.member.model.MemberDTO"%>
<%@page import="com.member.model.MemberDAO"%>
<%@page import="com.MapBoard.model.MapBoardDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
	MapBoardDTO cont = (MapBoardDTO)request.getAttribute("content");
	String location = request.getParameter("location"); 
	String UserId = (String)session.getAttribute("UserId");
	MemberDAO mdao = MemberDAO.getInstance();
	MemberDTO dto = mdao.contentById(UserId);
	String nick = dto.getMember_nick();
	int mem_num = dto.getMember_num();
	int pagee = Integer.parseInt(request.getParameter("page"));
	
%>
<script src="https://kit.fontawesome.com/6d4b3bed20.js" crossorigin="anonymous">

</script>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>펫만나</title>
<link rel="stylesheet" href="board_css/board.css">
</head>
<body>
	<%@include file="header.jsp"%>
	<div align="center">
	<c:set var="dto" value="${content }" />
		<div id="board_head"><span class="sp-title"><%=cont.getBoard_area() %> 게시판</span></div>
		<br><br><br>
		
		<table class="table">
		<%  if(cont != null) { %>
			<tr>
				<th id="contTh">글 No.</th>
					<td id="contTd"> <%=cont.getBoard_num() %> </td>
			</tr>
			
			<tr>
				<th id="contTh">말머리</th>
					<td id="contTd"> <%=cont.getBoard_head() %> </td>
			</tr>
			
			<tr>
				<th id="contTh">작성자</th>
					<td id="contTd"> <%=cont.getNick() %> </td>
			</tr>
			
			<tr>
				<th id="contTh">글 제목</th>
					<td id="contTd"> <%=cont.getBoard_title() %> </td>
			</tr>
			
			<tr>
				<th id="contTh">글 내용</th>
					<td id="contTd"> <%=cont.getBoard_text().replace("\r\n", "<br>") %> </td>
			</tr>
			
			<tr>
				<th id="contTh">첨부파일</th>
					<c:if test="${!empty dto.getBoard_file() }">
						<td id="contTd">
							<img src="<%=request.getContextPath() %>/file/${dto.getBoard_file() }" width="150" >
						</td>
					</c:if>
					
					<c:if test="${empty dto.getBoard_file() }">
						<td id="contTd"> </td>
					</c:if>
				</tr>	
			
			
			<tr>
				<th id="contTh">조회 수</th>
					<td id="contTd"> <%=cont.getBoard_hit() %> </td>
			</tr>
			
			<tr>
				<th id="contTh">추천</th>
      				<td id="contTd">
      					<% 
							if(session.getAttribute("UserId") == null){
						%>
							추천 기능은 <button type="button" id="newLogin" onclick = "location.href='m_login.jsp'"><b class="w3-text-blue">로그인</b></button> 후 사용 가능합니다.<br />
							<span class="rec_count"></span>					
						<% }else{%>
								<i class="fa fa-heart" style="font-size:16px;color:red" onclick="location.href='board_update.go?num=<%=cont.getBoard_num() %>&page=<%=pagee%>'"></i>
								&nbsp;<span class="rec_count"><%= cont.getBoard_like() %></span>
								
								
						<% } %>					
					</td>
			</tr>
			
			<tr>
				<th id="contTh">작성 날짜</th>
					<td id="contTd"> <%=cont.getBoard_regdate() %> </td>
			</tr>


			<% } else { %>	
				<tr>
					<td colspan="2" align="center">
						<h4>조회된 게시글 정보가 없습니다.</h4>	
					</td>
				</tr>
			<%	}	%>
			</table>	
			<br>
			
			
			<% if(mem_num == cont.getBoard_writer()){ %>
				<input id="cont_bt2" type="button" value="수정" onclick="location.href='board_modify.go?no=<%=cont.getBoard_num()%>&page=<%=pagee%>'">&nbsp;&nbsp; 
				<input id="cont_bt2" type="button" value="삭제" onclick="if(confirm('삭제하시겠습니까?')) {location.href='board_delete_ok.go?no=<%=cont.getBoard_num()%>&location=<%=cont.getBoard_area()%>'} else {return}">&nbsp;&nbsp; 
			<% } %>
			<input id="cont_bt2" type="button" value="목록" onclick="location.href='board_list.go?location=<%=cont.getBoard_area()%>'"> 
			<br>
			<br>
			
	<%@include file="footer.jsp"%>
	</div>	

</body>
</html>