<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>펫만나</title>
<link rel="stylesheet" href="s.css/del.css">
</head>
<body>

	<div class="container">
		<form method="post" action="delete.ok.do" >
			
			<h3>정말 삭제하시려면 비밀번호를 입력해주세요.</h3>
			
			<br>
			
			<input type="hidden" name="no" value="${Dto.getMember_num()}">
			비밀번호 
			<input type="text" name="pwd"> <input type="submit"value="확인">
		
		</form>
	</div>	
	</body>
</html>