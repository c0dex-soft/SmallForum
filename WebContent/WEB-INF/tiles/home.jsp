<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<table class="topic-list">
  	<tr>
    	<th>ID</th>
	    <th>Korisnicko ime</th>
<!-- 	    <th>Email</th> -->
	    <th>Naslov</th>
	    <th>Tekst</th>
	    <th>Vreme objave</th>
  	</tr>
  <c:forEach var="topic" items="${topics}">
	<tr>
		<td>${topic.id}</td>
		<td>${topic.user.username}</td>
<%-- 		<td>${topic.user.email}</td> --%>
		<td><a href="${pageContext.request.contextPath}/topic?id=${topic.id}">${topic.subject}</a></td>
		<td>${topic.text}</td>
		<td class="admin"><p class="timestamp">${topic.time}<p></td>
	</tr>  
  </c:forEach>
</table>

<p>
	<a href="${pageContext.request.contextPath}/createtopic">Kreiraj novu temu</a>
</p>
<sec:authorize access="hasAuthority('ROLE_ADMIN')">
	<p><a href="<c:url value='/admin'/>">Admin</a></p>
</sec:authorize>
