<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>펫만나</title>
<script src="https://kit.fontawesome.com/6d4b3bed20.js" crossorigin="anonymous"></script>
<link rel = "stylesheet" href = "s.css/login.css">

</head>
<body>
	<%@include file="header.jsp"%>
	
	<div align = "center" class ="container">
		<form method="post" action="login.member.do">
		<div class="log">
			 <label for="id">
			 <input type="text" name="id" placeholder="아이디"> <br> 
			 </label>
			 <label for="pwd">
			<input type="password" name="pwd"placeholder="비밀번호">
			</label>
		</div>
		
		<div class="links">


			<button type="submit">로그인</button>
			<br>
			<a href="m_join.jsp"> 회원가입 </a>
			<a href="#" onclick="window.open('m_findId.jsp','_blank','width=400,height=400')">
				아이디 찾기
			</a>
			
			<a href="#" onclick="window.open('m_findPwd.jsp','_blank','width=400,height=400')">
				비밀번호 찾기
			</a>

		</div>
		</form>
		
	</div>
	<%@include file="footer.jsp"%>
</body>