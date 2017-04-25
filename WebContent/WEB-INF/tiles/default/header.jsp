<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>	
<a class="title" href="<c:url value='/'/>">small::forum</a>

<form class="search" action="${pageContext.request.contextPath}/search" method="get">
	<input type="text" name="term" value="" placeholder="* Pretraga postojecih tema"/>
	<input type="submit" value="Pretrazi"/>
</form>

<sec:authorize access="!isAuthenticated()">
	<a class="login" href="<c:url value='/login'/>">Uloguj se</a>
</sec:authorize>
<sec:authorize access="isAuthenticated()">
	<c:if test="${topicsexists}">
	<a class="mytopics" href="${pageContext.request.contextPath}/mytopics">Moje teme</a>
	</c:if>
	<a class="login" href="<c:url value='/logout'/>">Izloguj se</a>
</sec:authorize>