<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script type="text/javascript">
	function onLoad() {
		$("#password").keyup(checkPasswordMatch);
		$("#confirmpass").keyup(checkPasswordMatch);

		$("#details").submit(canSubmit);
	}

	function canSubmit() {
		var password = $("#password").val();
		var confirmpass = $("#confirmpass").val();

		if (password != confirmpass) {
			alert("Lozinke se ne poklapaju");
			return false;
		} else {
			return true;
		}
	}

	function checkPasswordMatch() {
		var password = $("#password").val();
		var confirmpass = $("#confirmpass").val();

		if (password.length > 3 || confirmpass.length > 3) {
			if (password == confirmpass) {
				$("#matchpass").text("<fmt:message key="MatchedPasswords.user.password"></fmt:message>");
				$("#matchpass").removeClass("error");
				$("#matchpass").addClass("confirm");
			} else {
				$("#matchpass").text("<fmt:message key="UnmatchedPasswords.user.password"></fmt:message>");
				$("#matchpass").removeClass("confirm");
				$("#matchpass").addClass("error");
			}
		}
	}

	$(document).ready(onLoad);
</script>