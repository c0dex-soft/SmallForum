<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script>
	function onDeleteClick() {	
		var doDelete = confirm('Da li ste sigurni da zelite da obrisete korisnika?');
		if (doDelete == false) {
			event.preventDefault();
		}
	}
</script>

<h2>Samo za korisnike sa administratorskim privilegijama</h2>
<table class="topic-list" style="width: 100%">
<tr>
<th>Korisnicko ime</th>
<th>Ime i prezime</th>
<th>Email</th>
<th>Aktivan</th>
<th>Privilegija</th>
<th>Izmeni status</th>
<th>Izmeni privilegiju</th>
<th>Obrisi</th>
</tr>
<c:forEach var="user" items="${users}">
<tr>
<td id="username">${user.username}</td>
<td>${user.name}</td>
<td>${user.email}</td>
<td class="admin">${user.enabled}</td>
<td>${user.authority}</td>
<c:choose>
<c:when test="${user.enabled == true}">
	<td class="admin"><a href="${pageContext.request.contextPath}/admin/user?uid=${user.username}&sc=true">Deaktiviraj</a></td>
</c:when>
<c:when test="${user.enabled == false}">
	<td class="admin"><a href="${pageContext.request.contextPath}/admin/user?uid=${user.username}&sc=true">Aktiviraj</a></td>
</c:when>
</c:choose>
<c:choose>
<c:when test="${user.authority == 'ROLE_USER'}">
	<td class="admin"><a href="${pageContext.request.contextPath}/admin/user?uid=${user.username}&rc=true">Admin</a></td>
	<td class="admin"><a href="${pageContext.request.contextPath}/admin/user?uid=${user.username}&del=true" onclick="return onDeleteClick();">Obrisi</a></td>
</c:when>
<c:when test="${user.authority == 'ROLE_ADMIN'}">
	<td class="admin"><a href="${pageContext.request.contextPath}/admin/user?uid=${user.username}&rc=true">User</a></td>
	<td></td>
</c:when>
</c:choose>
</tr>
</c:forEach>
</table>