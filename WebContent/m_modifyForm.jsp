<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>펫만나</title>
<link rel="stylesheet" href="s.css/modi.css">
</head>
<body>
	<%@include file="header.jsp"%>
		<form method="post" action="modify.ok.member.do" class="container">
			<input type="hidden" name="no" value="${Dto.getMember_num()}">
			<table  cellspacing="0" class="join">
				<tr>
					<th>회원 아이디</th>
					<td><input type="text" name="id" readonly
						value="${Dto.getMember_id() }"></td>
				</tr>
				<tr>
					<th>회원 이름</th>
					<td><input type="text" name="name" readonly
						value="${Dto.getMember_name() }"></td>
				</tr>
	
				<tr>
					<th>회원 비밀번호</th>
					<td><input type="password" name="pwd"
						value="${Dto.getMember_pwd() }"></td>
				</tr>
	
				<tr>
					<th>회원 나이</th>
					<td><input type="text" name="age"
						value="${Dto.getMember_age() }"></td>
				</tr>
				<tr>
					<th>연락처</th>
					<td><input type="text" name="phone"
						value="${Dto.getMember_phone() }"></td>
				</tr>
				<tr>
					<th>회원 이메일</th>
					<td><input type="text" name="email"
						value="${Dto.getMember_email() }"></td>
				</tr>
				<tr>
					<th>회원 닉네임</th>
					<td><input type="text" name="nick"
						value="${Dto.getMember_nick() }"></td>
				</tr>
				<tr>
					<th>자기소개</th>
					<td><input type="text" name="self"
						value="${Dto.getMember_self() }"></td>
				</tr>
	
	
				<tr id ="sub">
					<td colspan="2" align="center">
						<button type="submit">회원수정</button>
					</td>
				</tr>
			</table>
		</form>

	<%@include file="footer.jsp"%>
</body>
</html>