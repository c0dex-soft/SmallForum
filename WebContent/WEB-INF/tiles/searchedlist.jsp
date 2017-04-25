<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<h2>Pretraga je izvrsena za pojam <span style="color: #716f6f;">#</span><span style="white-space:nowrap; color: #ea6e00">${term}</span></h2>
<table class="topic-list">
  	<tr>
    	<th>ID</th>
    	<th>Korisnicko ime</th>
	    <th>Naslov</th>
	    <th>Tekst</th>
	    <th>Vreme objave</th>
  	</tr>
  <c:forEach var="topic" items="${searchedTopics}">
	<tr>
		<td>${topic.id}</td>
		<td>${topic.user.username}</td>
		<td><a href="${pageContext.request.contextPath}/topic?id=${topic.id}">${topic.subject}</a></td>
		<td>${topic.text}</td>
		<td class="admin"><p class="timestamp">${topic.time}</p></td>
	</tr>  
  </c:forEach>
</table>