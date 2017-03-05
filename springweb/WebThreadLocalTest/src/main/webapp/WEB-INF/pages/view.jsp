<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title></title>
    <script type="text/javascript" src="/js/jquery-easyui-1.3.2/jquery-1.8.0.min.js"></script>
    <script type="text/javascript">
    function submit() {
		$("#myForm").attr("action", "/user/update/${user.userId}");
		$("#myForm").submit();
    }
    </script>
</head>
<body>
<form id="myForm" action="/user" method="post" style="text-align:center">
	<input name="userId" value="${user.userId}"/>
	<input name="userName" value="${user.userName}"/>
	<a href="javascript:submit();"><em>保存</em></a>
</form>
</body>
</html>