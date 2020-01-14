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

<title>退出 官查——个人简历自动抽取系统</title>

<meta http-equiv="refresh" content="5;URL=login.jsp">
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
	<div id="exit">
		<h1>已退出系统，5秒后返回至登陆界面...</h1>
		<button type="button" class="button"
			onclick="javascript:window.location.href='login.jsp'">退出</button>
	</div>
</body>
</html>
