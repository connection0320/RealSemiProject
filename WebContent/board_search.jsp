<%@page import="java.util.List"%>
<%@page import="com.member.model.MemberDTO"%>
<%@page import="com.member.model.MemberDAO"%>
<%@page import="com.MapBoard.model.MapBoardDTO"%>
<%@page import="com.MapBoard.model.MapBoardDAO"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<% 
   String location = request.getParameter("location"); 
   String UserId = (String)session.getAttribute("UserId");
   MemberDAO mdao = MemberDAO.getInstance();
   MemberDTO dto = mdao.contentById(UserId);
   String nick = dto.getMember_nick();
%>
<%
	List<MapBoardDTO> search = (List<MapBoardDTO>)request.getAttribute("Search");
%>     
    
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
		<div id="board_head"><span class="sp-title"><%=location %> 게시판 </span></div>
		<br><br><br>

		<table class="table">
			<tr>
				<th id="search-th">No.</th>		
				<th id="search-th">말머리</th>		
				<th id="search-th">글 제목</th>		
				<th id="search-th">작성자</th>		
				<th id="search-th">조회수</th>		
				<th id="search-hit-th">작성일자</th>		
			</tr>
			
			<%
				if(search.size() != 0) {
					for(int i = 0; i < search.size(); i++) {
						MapBoardDTO bdto = search.get(i);
			%>
						<tr>
							<td> <%=bdto.getBoard_num()%> </td>			
							<td> <%=bdto.getBoard_head()%> </td>				
							<td>
								<a href="<%=request.getContextPath() %>/board_content.go?no=<%=bdto.getBoard_num() %>&page=1"><%=bdto.getBoard_title()%></a></td>
																			
							<td> <%=bdto.getNick()%> </td>
							<td> <%=bdto.getBoard_hit()%> </td>			
							<td id="xboard"> <%=bdto.getBoard_regdate().substring(0,10)%> </td>
						</tr>				
			<% } 
			} else {		// 조회된 게시물이 없는 경우
			%>	<tr>
					<td id="xboard" colspan="6" align="center">
						<h3>조회 된 게시물이 없습니다.</h3>
					</td>
				</tr>		
		<%	} %>

		</table>
		
		<br>
		<input id="cont_bt2" type="button" value="목록" onclick="location.href='board_list.go?location=<%=location %>'">
		<br>
		<br>
		
		<%@include file="footer.jsp"%>
	</div>
	
	

</body>
</html>