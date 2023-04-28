<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>펫만나</title>
</head>
<body>
<body>
<%@include file="header.jsp" %>
    <div align="center">
        <hr width="50%" color="red">
        <h3>친구 추가 가능한 회원 목록</h3>
        <hr width="50%" color="red">
        

            <table border="1" cellspacing="0">
                <tr>
                    <th>회원 번호</th>
                    <th>닉네임</th>
                    <th>이름</th>
                    <th>친구 요청</th>
                </tr>
                
                <c:set var="list" value="${otherMembers }" />
                
                <c:if test="${!empty list }">
                    <c:forEach items="${list}" var="mdto">
                        <tr>
                            <td>${mdto.member_num}</td>
                            <td>${mdto.member_nick}</td>
                            <td>${mdto.member_name}</td>
                            <td>
                                <input type="button" name="add_friend" value ="친구 요청"
									onclick="location.href='<%=request.getContextPath()%>/fri_ok_add.to?no=${mdto.member_num}'">                         
                            </td>
                        </tr>
                    </c:forEach>
                </c:if>
                <c:if test="${empty list }">
                    <tr>
                        <td colspan="4" align="center">
                            <h3>친구 추가 가능한 회원이 없습니다.</h3>
                        </td>
                    </tr>
                </c:if>
            </table>
  
    </div>
</body>
</html>