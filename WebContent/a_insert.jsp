<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>   
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>펫만나</title>
<link rel="stylesheet" href="s.css/a_insert.css">
</head>
<body>


	<%@include file="header.jsp" %>

	
	<div align="center" class="container">
	<br>
	<br><br><br><br>
	<table class="my_ani">
		<tr class="a_th">
			<th > 첫째</th>
			<td>${Animal1.getAnimal_name()}</td>
		</tr>
		<tr class="a_th">
			<th> 둘쨰 </th>
			<td>${Animal2.getAnimal_name()}</td>
		</tr>
		<tr class="a_th">
			<th class="th3"> 셋째 </th>
			<td>${Animal3.getAnimal_name()}</td>
		</tr>
	</table>
	
	<%-- 애완동물 더이상 등록 불가한 경우 --%>
	<c:if test="${Animal1 != null and Animal2 != null and Animal3 != null}">
		<h3>아이를 더이상 등록하실 수 없습니다.</h3>
	</c:if>
	
	<c:if test="${(Animal1 == null and Animal2 == null and Animal3 == null) or (Animal1 != null and Animal2 == null and Animal3 == null) or (Animal1 != null and Animal2 != null and Animal3 == null)}">
		
				<form method="post" action="AniamlInsertOk.no" enctype="multipart/form-data" class="join">
		
					<table class="join">
						<tr>
							<th> 이름 </th>
							<td> <input name="name">  </td>
						</tr>
						<tr>
							<th> 나이</th>
							<td> <input name="age">  </td>
						</tr>
						<tr class="choice">
							<th> 성별 </th>
							<td> 
								수컷 <input type="radio" value="1" name="gender" >
								암컷 <input type="radio" value="2" name="gender" >				
							</td>
						</tr>
						<tr class="choice">
							<th> 종류 </th>
							<td> 
								강아지 <input type="radio" value="1" name="type">
								고양이 <input type="radio" value="2" name="type" >	  
							</td>
						</tr>
						<tr>
							<th> 품종</th>
							<td> <input name="race">  </td>
						</tr>
						<tr class= "po">
						    <th>사진</th>
						    <td>
						        <input type="file" name="image" onchange="previewImage(event)">
						        <br>
						        <img id="preview" src="#" alt="프로필 사진 미리보기" style="max-width:200px; max-height:200px;">
						    </td>
						</tr>
						</table>
						<table class="tata">
							<td colspan="2" >
								<c:if test="${Animal1 == null and Animal2 == null and Animal3 == null}">
									<h4>아직 등록된 아이가 없습니다. 첫번째 아이를 등록해보세요 :)</h4>
									<input type="hidden" name="have" value ="1">
								</c:if>
								
								<c:if test="${Animal1 != null and Animal2 == null and Animal3 == null}">
									<h4>아이를 더 등록하실 수 있습니다.</h4>
									<input type="hidden" name="have" value ="2">
								</c:if>
								
								<c:if test="${Animal1 != null and Animal2 != null and Animal3 == null}">
									<h4>아이를 더 등록하실 수 있습니다.</h4>
									<input type="hidden" name="have" value ="3">
								</c:if>
								<input type="submit" value="등록하기" id="sub">
							</td>
						</table>
	
		<div id="commentary">
			
		</div>
			
		</form>
		
		<br>
		<br>	
	</c:if>
	<br><br><br><br><br><br><br><br><br><br><br>
	</div>
	<br>
	<%@include file="footer.jsp" %>
<script>
	function previewImage(event) {
	    var reader = new FileReader();
	    reader.onload = function(){
	        var img = document.getElementById("preview");
	        img.src = reader.result;
	        // 이미지 크기를 조정합니다.
	        img.style.width = "200px"; // 원하는 가로 크기를 설정합니다.
	        img.style.height = "200px"; // 원하는 세로 크기를 설정합니다.
	    }
	    reader.readAsDataURL(event.target.files[0]);
	}
</script>
</body>
</html>