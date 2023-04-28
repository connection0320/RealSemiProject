<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>펫만나</title>
<link rel="stylesheet" href="s.css/join.css">
<script type="text/javascript">
	function idcheck() {
		var id = f.id.value;
		location.href = "idcheck.member.do?checkId=" + id;
	}

	function check() {

		if (f.id.value == "") {
			alert("아이디를 입력하세요");
			f.id.focus();

			return false;
		} //f는 폼 이름

		if (f.name.value == "") {
			alert("이름를 입력하세요");
			f.name.focus();
			return false;
		} //f는 폼 이름

		if (f.pwd.value == "") {
			alert("비밀번호를 입력하세요");
			f.pwd.focus();
			return false;
		} //f는 폼 이름

		if (isNaN(f.age.value)) {
			alert("나이는 숫자만 입력이 가능합니다");
			f.age.focus();
			return false;
		} //f는 폼 이름

		if (f.age.value == "") {
			alert("나이를 입력하세요");
			f.age.focus();
			return false;
		} //f는 폼 이름

		if (f.phone.value == "") {
			alert("연락처를 입력하세요");
			f.phone.focus();
			return false;
		} //f는 폼 이름

		if (f.addr3.value == "") {
			alert("주소를 입력하세요");
			f.addr3.focus();
			return false;
		} //f는 폼 이름

		if (f.email.value == "") {
			alert("이메일을 입력하세요");
			f.email.focus();
			return false;
		} //f는 폼 이름
		if (f.nick.value == "") {
			alert("닉네임을 입력하세요");
			f.nick.focus();
			return false;
		} //f는 폼 이름

		if (f.gender.value == "") {
			alert("성별을 확인하세요");
			f.gender.focus();
			return false;
		} //f는 폼 이름
	}
</script>
</head>
<body>	

	<%@include file="header.jsp"%>
	<div class="boby">
		<form method="post" action="insert.member.do" name="f"
			onsubmit="return check()" class="container">

			<table class="join" align="center">
				<tr>
					<!-- <th id="w">회원 아이디</th> -->
					<td><span>회원 아이디</span> <br> <input type="text" name="id"
						maxlength="30" placeholder="아이디 입력"> <input type="button"
						value="중복확인" onclick="idcheck()" id="m_sub"></td>
				</tr>
				<tr>
					<!-- <th>회원 이름</th> -->
					<td><span>회원 이름</span> <br> <input type="text"
						name="name" placeholder="이름 입력"></td>
				</tr>

				<tr>
					<!-- <th>회원 비밀번호</th> -->
					<td><span>회원 비밀번호</span> <br> <input type="password"
						name="pwd" placeholder="비밀번호 입력"></td>
				</tr>
				<tr>
					<!-- 				<th>비밀번호 확인</th> -->
					<%-- 자바스크립트로 구현 예정 --%>
					<td><span>비밀번호 확인</span> <br> <input type="password"
						placeholder="비밀번호 확인"></td>
				</tr>
				<tr>
					<!-- <th>회원 나이</th> -->
					<td><span>회원 나이</span> <br> <input type="text" name="age"
						placeholder="나이 입력"></td>
				</tr>
				<tr>
					<!-- <th>연락처</th> -->
					<td><span>회원 연락처</span> <br> <input type="text"
						name="phone" placeholder="연락처 입력"></td>
				</tr>


				<tr>
					<!-- <th>주소</th> -->
					<td><span>회원 주소</span> <br> <input type="text"
						name="addr1" value="서울시" readonly> <select name="addr2">
							<option value="구">:::구 선택:::</option>
							<option value="강남구">강남구</option>
							<option value="강동구">강동구</option>
							<option value="강북구">강북구</option>
							<option value="강서구">강서구</option>
							<option value="관악구">관악구</option>
							<option value="광진구">광진구</option>
							<option value="구로구">구로구</option>
							<option value="금천구">금천구</option>
							<option value="노원구">노원구</option>
							<option value="도봉구">도봉구</option>
							<option value="동대문구">동대문구</option>
							<option value="동작구">동작구</option>
							<option value="마포구">마포구</option>
							<option value="서대문구">서대문구</option>
							<option value="서초구">서초구</option>
							<option value="성동구">성동구</option>
							<option value="성북구">성북구</option>
							<option value="양천구">양천구</option>
							<option value="영등포구">영등포구</option>
							<option value="용산구">용산구</option>
							<option value="은평구">은평구</option>
							<option value="종로구">종로구</option>
							<option value="중구">중구</option>
							<option value="중랑구">중랑구</option>
					</select> <input type="text" name="addr3" placeholder="상세 주소"></td>

				</tr>
				<tr>
					<!-- <th>회원 이메일</th> -->
					<td><span>회원 이메일</span> <br> <input type="text"
						name="email" placeholder="이메일 입력"></td>
				</tr>
				<tr>
					<!-- <th>회원 닉네임</th> -->
					<td><span>회원 닉네임</span> <br> <input type="text"
						name="nick" placeholder="닉네임 입력"></td>
				</tr>
				<tr>
					<!-- <th>자기소개</th> -->
					<td><span>자기소개</span> <br> <input type="text" name="self"
						placeholder="자기소개를 입력해주세요"></td>
				</tr>
				<tr>
					<!-- <th >회원 성별</th> -->
					<td><span>성별</span> <br>
						<div class="gender">
							남자<input type="radio" value="man" name="gender">
						</div> <br>
						<div class="gender">
							여자<input type="radio" value="woman" name="gender">
						</div></td>
				</tr>
				<br>
				<tr>
					<td colspan="2" id="s" id="subm">
						<button type="submit" id="but">회원등록</button>
					</td>
				</tr>
			</table>
		</form>

	</div>

	<%@include file="footer.jsp"%>
</body>
</html>