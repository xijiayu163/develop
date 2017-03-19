<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form"
	prefix="springForm"%>
<html>
<head>
<title>user register</title>
<style>
.error {
	color: #ff0000;
	font-style: italic;
	font-weight: bold;
}
</style>
</head>
<body>
	<springForm:form method="POST" commandName="user" action="register.do">
		<table>
			<tr>
				<td>Name:</td>
				<td><springForm:input path="userName" /></td>
				<td><springForm:errors path="userName" cssClass="error" /></td>
			</tr>
			<tr>
				<td>age:</td>
				<td><springForm:input path="age" /></td>
				<td><springForm:errors path="age" cssClass="error" /></td>
			</tr>
			<tr>
			<td colspan="2"><input type="submit" value="Save User"></td>
			</tr>
		</table>
	</springForm:form>
</body>
</html>