<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>登陆 官查——个人简历自动抽取系统</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">

<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<link rel="stylesheet" type="text/css" href="CSS/login_exit.css"></link>
</head>

<body>
	<div id="login">
		<img width="80px" style="border-radius: 20px;" src="IMG/logo.png"></img>
		<h1>LOGIN AESOCV</h1>
		<form action="login_control.jsp" method="post">
			<input type="text" name="username" placeholder="用户名"
				required="required"></input><input type="password" name="password"
				placeholder="密码" required="required"></input>
			<button type="submit" id="go" class="button">登录</button>
			<button type="button" id="register" class="button"
				onclick="javascript:window.location.href='register.jsp'">
				注册</button>
		</form>
	</div>
</body>
</html>
