<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h2>Sve teme korisnika <span style="color: #716f6f;">@</span><span style="white-space:nowrap; color: #ea6e00">${username}</span></h2>
<table class="topic-list">
  	<tr>
    	<th>ID</th>
	    <th>Naslov</th>
	    <th>Tekst</th>
	    <th></th>
  	</tr>
  <c:forEach var="mytopic" items="${mytopics}">
	<tr>
		<td>${mytopic.id}</td>
		<td><a href="${pageContext.request.contextPath}/topic?id=${mytopic.id}">${mytopic.subject}</a></td>
		<td>${mytopic.text}</td>
		<td class="admin"  style="width: 100px;"><a href="${pageContext.request.contextPath}/mytopics/${username}?ed=true&id=${mytopic.id}">Izmeni / Obrisi</a></td>
	</tr>  
  </c:forEach>
</table>