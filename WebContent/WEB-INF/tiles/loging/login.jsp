<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript">
	$(document).ready(function() {
		document.f.username.focus();
	});
</script>
<div class="login-page">
	<div class="form">
		<form class="login-form" name="f"
			action="${pageContext.request.contextPath}/login" method="post">
			<table>
				<tr>
					<th colspan="3"><c:choose>
							<c:when test="${param.error == 'true'}">
								<p class="error-title">Neuspelo logovanje</p>
								<p class="error-text">Proverite da li su korisnicko ime i
									lozinka ispravni</p>
							</c:when>
							<c:when test="${param.error == null}">LOGOVANJE NA SISTEM</c:when>
						</c:choose></th>
				</tr>
				<tr>
					<td colspan="3"><input type="text" name="username" value=""
						placeholder="Korisnik *"></td>
				</tr>
				<tr>
					<td colspan="3"><input type="password" name="password"
						value="" placeholder="Lozinka *"></td>
				</tr>
				<tr>
					<td class="cekdugme"><input type="checkbox" name="remember-me" /></td>
					<td class="cekdugmetekst">Zapamti me</td>
					<td><button>Uloguj se</button></td>
				</tr>
				<tr>
					<td colspan="3"><br>
					<a href="<c:url value="/newaccount"></c:url>">Kreiraj novi
							nalog</a></td>
				</tr>
			</table>
		</form>
	</div>
</div>