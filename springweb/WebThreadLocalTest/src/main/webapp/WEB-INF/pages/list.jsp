<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>用户列表</title>
<script type="text/javascript" src="/js/jquery-easyui-1.3.2/jquery-1.8.0.min.js"></script>
    <script type="text/javascript">
    function show(userId) {
		$("#myForm").attr("action", "/user/"+userId);
		$("#myForm").submit();
    }
    function update(userId) {
		$("#myForm").attr("action", "/user/"+userId);
		$("#myForm").submit();
    }
    function del(userId) {
		$("#myForm").attr("action", "/user/del/"+userId);
		$("#myForm").submit();
    }
    function add() {
    	$("#myForm").attr("action", "/user/new");
		$("#myForm").submit();
    }
    </script>
</head>
<form id="myForm"  method="post" style="text-align:center">
<body>
	<c:choose>
		<c:when test="${not empty users}">
			<c:forEach items="${users}" var="user">
				<a href="javascript:show('${user.userId}');">${user.userId}</a> 
				${user.userName}
				<a href="javascript:update('${user.userId}');">修改</a>
				<a href="javascript:del('${user.userId}');">删除</a>
				<br />
			</c:forEach>
		</c:when>
	</c:choose>
	<a href="javascript:add();">增加</a>
</body>
</form>
</html>