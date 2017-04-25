<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="form-style">
	<h1>Novi nalog</h1>

	<sf:form id="details"
		action="${pageContext.request.contextPath}/createaccount"
		method="POST" modelAttribute="user">
		<table class="newaccount">
			<tr>
				<td>Korisnicko ime:</td>
				<td class="newaccount-input"><sf:input path="username"
						name="username" type="text" width="50"
						placeholder="Korisnicko ime koje cete koristiti na sajtu *" /></td>
			</tr>
			<tr>
				<td></td>
				<td class="formrow-padding"><sf:errors path="username"
						cssClass="error"></sf:errors></td>
			</tr>

			<tr>
				<td>Vase ime:</td>
				<td class="newaccount-input"><sf:input path="name" name="name"
						type="text" width="50" placeholder="Vase ime i prezime *" /></td>
			</tr>
			<tr>
				<td></td>
				<td class="formrow-padding"><sf:errors path="name"
						cssClass="error"></sf:errors></td>
			</tr>

			<tr>
				<td>Email:</td>
				<td><sf:input path="email" name="email" type="text"
						placeholder="Email adresa *" /></td>
			</tr>
			<tr>
				<td></td>
				<td class="formrow-padding"><sf:errors path="email"
						cssClass="error"></sf:errors></td>
			</tr>

			<tr>
				<td>Lozinka:</td>
				<td><input id="password" name="password" type="password"
						 placeholder="Unesite lozinku *" /></td>
			</tr>
			<tr>
				<td></td>
				<td></td>
<%-- 				<td class="formrow-padding"><sf:errors path="password"
						cssClass="error"></sf:errors></td> --%>
			</tr>

			<tr>
				<td>Potvrda lozinke:</td>
				<td><input id="confirmpass" name="confirmpass" type="password"
					placeholder="Potvrdite unetu lozinku *" /></td>
			</tr>
			<tr>
				<td></td>
				<td><div id="matchpass" class="error"></div></td>
			</tr>

			<tr>
				<td></td>
				<td><button class="newaccount">KREIRAJ NALOG</button></td>
				<!-- <td><input class="button-margin" value="KREIRAJ NALOG"
						type="submit" /></td> -->
			</tr>
		</table>
	</sf:form>
</div>