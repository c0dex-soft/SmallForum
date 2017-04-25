<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags"%>

<table class="topic-page">
	<tr>
		<th>Korisnicko ime</th>
<!-- 		<th>Email</th> -->
		<th>Naslov</th>
		<th>Tekst</th>
		<th>Vreme objave</th>
	</tr>
	<c:forEach var="topic" items="${topicPosts}">
		<tr>
			<td>${topic.user.username}</td>
<%-- 			<td>${topic.user.email}</td> --%>
			<td>${topic.subject}</td>
			<td>${topic.text}</td>
			<td class="admin"><p class="timestamp">${topic.time}</p></td>
		</tr>
	</c:forEach>
</table>

<p>
	<a
		href="${pageContext.request.contextPath}/createpost?id=${topicPosts[0].id}">Odgovori na temu</a>
</p>