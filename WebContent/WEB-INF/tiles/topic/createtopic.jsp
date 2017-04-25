<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="form-style">
<h1>Nova tema</h1>

<sf:form id="topic" action="${pageContext.request.contextPath}/docreate" method="post" modelAttribute="topic">
<input name="user.name" type="hidden" value="nebitno"/>
<input name="user.password" type="hidden" value="01234567"/>

<table>
<tr><td>Korisnik:</td><td><sf:input path="user.username" name="user.username" type="text" width="50" readonly="true" placeholder="Vase korisnicko ime *"/></td></tr>
<tr><td></td><td class="formrow-padding"><sf:errors path="user.username" cssClass="error"></sf:errors></td></tr>

<tr><td>Email:</td><td><sf:input path="user.email" name="user.email" type="text" readonly="true" placeholder="Email adresa *"/></td></tr>
<tr><td></td><td class="formrow-padding"><sf:errors path="user.email" cssClass="error"></sf:errors></td></tr>

<tr><td>Naslov:</td><td><sf:input path="subject" name="subject" type="text" placeholder="Naslov teme *"/></td></tr>
<tr><td></td><td class="formrow-padding"><sf:errors path="subject" cssClass="error"></sf:errors></td></tr>

<tr><td>Tekst:</td><td><sf:textarea path="text" name="text" rows="10" cols="50" placeholder="Tekst teme *"></sf:textarea></td></tr>
<tr><td></td><td class="formrow-padding"><sf:errors path="text" cssClass="error"></sf:errors></td></tr>

<!-- <tr><td></td><td><input class="button-margin" name="Kreiraj temu" value="POSALJI" type="submit"/></td></tr> -->
<tr><td></td><td><button class="newtopic">KREIRAJ TEMU</button></td></tr>
</table>
</sf:form>

</div>