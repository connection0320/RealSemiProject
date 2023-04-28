<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>펫만나</title>
<link rel="stylesheet" href="s.css/find_id_pwd.css">
</head>
<body>
<h2>펫만나</h2>
	<form method="post" action="findId.member.do">
			
			<br>
			<input type="text" name="name" placeholder="이름 입력"> 
			<br>
			<input type="text" name="phone"placeholder="전화번호"> 
			<br>
			<div class="fun">
				<button type="submit">아이디 찾기</button>
				<img alt="슬퍼요" src="s.image/button.png">
			</div>
	</form>

</body>
</html>