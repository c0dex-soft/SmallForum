<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>

<script>
	function onDeleteClick() {
		var doDelete = confirm('Da li ste sigurni da zelite da obrisete temu?');
		if (doDelete == false) {
			event.preventDefault();
		}
	}
</script>

<div class="form-style">
	<h1>Izmeni ili obrisi temu</h1>

	<sf:form action="${pageContext.request.contextPath}/mytopics/edit"
		method="post" commandName="topic">
		<input name="user.name" type="hidden" value="nebitno" />
		<input name="user.password" type="hidden" value="12345" />
		<input name="id" type="hidden" value="${topic.id}" />

		<table>
			<tr>
				<td>Korisnik:</td>
				<td><sf:input path="user.username" name="user.username" readonly="true"
						type="text" width="50" placeholder="Vase korisnicko ime *" /></td>
			</tr>
			<tr>
				<td></td>
				<td class="formrow-padding"><sf:errors path="user.username"
						cssClass="error"></sf:errors></td>
			</tr>

			<tr>
				<td>Email:</td>
				<td><sf:input path="user.email" name="user.email" readonly="true"
				 		type="text" placeholder="Email adresa *" /></td>
			</tr>
			<tr>
				<td></td>
				<td class="formrow-padding"><sf:errors path="user.email"
						cssClass="error"></sf:errors></td>
			</tr>

			<tr>
				<td>Naslov:</td>
				<td><sf:input path="subject" name="subject" readonly="true"
				 		type="text" placeholder="Naslov teme *" /></td>
			</tr>
			<tr>
				<td></td>
				<td class="formrow-padding"><sf:errors path="subject"
						cssClass="error"></sf:errors></td>
			</tr>

			<tr>
				<td>Tekst:</td>
				<td><sf:textarea path="text" name="text" rows="10" cols="50"
						placeholder="Tekst teme *"></sf:textarea></td>
			</tr>
			<tr>
				<td></td>
				<td class="formrow-padding"><sf:errors path="text"
						cssClass="error"></sf:errors></td>
			</tr>
			<tr>
				<td><button class="delete"
						formaction="${pageContext.request.contextPath}/mytopics/delete" onclick="return onDeleteClick();">OBRISI</button></td>
				<td><button class="newtopic">IZMENI TEMU</button></td>
			</tr>
		</table>
	</sf:form>
</div>