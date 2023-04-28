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
		<form method="post" action="findPwd.member.do">
		
				이   름 : <input type="text" name="name"> 
				<br>
				아이디 : <input type="text" name="id"> 
				<br>
				이메일 : <input type="text" name="email"> 
				<br>
				<div class="fun">
					<button type="submit">비밀번호 찾기</button>
					<img alt="슬퍼요" src="s.image/button.png">
				</div>
		</form>

</body>
</html>