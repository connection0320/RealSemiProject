
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>펫만나</title>
<link rel="stylesheet" href="s.css/fri_list.css">
</head>
<body>
	<%@include file="/header.jsp"%>
<div class="body">
	<div align="center" class="container">
		<form method="post" class="table_container">
			<table id="tion" cellspacing="0">
				<tr>
					<th>아이디  </th>
					<th> 닉네임  </th>
					<th> 성별 </th>
					<th> 메세지 </th>
					<th> 삭제 </th>
				</tr>
			
					<c:set var="list" value="${List }" />
					<c:if test="${!empty list}">
						<c:forEach items="${list}" var="dto">
							<tr >
								<td>${dto.getMember_id()}</td>
								<td>${dto.getMember_nick()}</td>
								<td>${dto.getMember_gender()}</td>
								<td><a href="<%=request.getContextPath() %>/fri_message.to?no=${dto.getMember_num()}">
										<img src="s.image/communication.png" class="ima">
									</a>
								</td>
								<td>
									<a href="<%=request.getContextPath()%>/fri_delete.to?no=${dto.getMember_num()}">
										<img src="s.image/close.png" class="ima">
									</a>
								</td>
							</tr>

						</c:forEach>

					</c:if>
					<c:if test="${empty list }">
						<tr>
							<td colspan="6" align="center">
								<h3>친구가 없습니다</h3>
							</td>
					</c:if>
			</table>

		</form>

	</div>
</div>	
	<%@include file="/footer.jsp"%>
</body>
</html>