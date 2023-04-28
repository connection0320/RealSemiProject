<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>펫만나</title>
<link rel="stylesheet" href="s.css/fri_acc.css">
</head>
<body>
	<%@include file="header.jsp"%>
		<!-- 수락 관련 정보만 화면 뿌려주는 페이지 -->
	<div align="center" class="container">

	   <br>

	   <table class="join">
	     
	      <tr>
	         <th>친구 이름</th> 
	         <th>친구 닉네임</th> 
	         <th>친구 수락</th>
	      </tr>

	      <c:set var="list" value="${list }" />
	    
	      <c:if test="${!empty list }">
	      
	         <c:forEach items="${list }" var="dto">
	      
	            <tr>
	               <td> ${dto.getMember_name() } </td>
	               <td> ${dto.getMember_nick() } </td>
	               <td> 
	                  <a href="<%=request.getContextPath() %>/fri_acc_ok.to?no=${dto.getMember_num() }">
	               						<img src="s.image/fri_add.png" class="img"></a></td>
	            </tr>
	            <br>
	         </c:forEach>
	      </c:if>
	      
	      <c:if test="${empty list }">
	        <tr>
	      		<td colspan="3">
	            	<h2> 친구 신청이 없습니다</h2>
	        	</td>
	         </tr> 
	      </c:if>
	   </table>
	  </div>
	  
	  	<%@include file="footer.jsp"%>
	  
</body>
</html>