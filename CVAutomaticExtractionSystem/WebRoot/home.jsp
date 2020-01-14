<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="hnust.cwz.javabeans.DBAccessBean"%>
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

<title>官查——个人简历自动抽取系统</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">

<!--
<link rel="stylesheet" type="text/css" href="styles.css">
-->
<link rel="stylesheet" type="text/css" href="CSS/my_styles.css">

<script type="text/javascript">
	function changeBC(id) {
		var mychar = document.getElementById(id); //获取要改变颜色的id
		mychar.style.color = "#242424";
		mychar.style.backgroundColor = "#BDBDBD";
	}

	function originalBC(id) {
		var mychar = document.getElementById(id); //获取要改变颜色的id
		mychar.style.color = "#BDBDBD";
		mychar.style.backgroundColor = "#242424";
	}
</script>
</head>

<body>
	<div id="container">
		<div id="menu">
			<div id="logo">
				<big_name_1 onclick="javascript:window.location.href='home.jsp'">AES</big_name_1><big_name_2 onclick="javascript:window.location.href='home.jsp'">OCV
				</big_name_2>
			</div>
			<p>
				<input type="button" id="home" value="首页 >" class="menu_button"
					onmousedown="changeBC('home')" onmouseup="originalBC('home')"
					onclick="javascript:window.location.href='home.jsp'">
				<input
					type="button" id="cv_extraction" value="查简历" class="menu_button"
					onmousedown="changeBC('cv_extraction')"
					onmouseup="originalBC('cv_extraction')"
					onclick="javascript:window.location.href='query_cv.jsp'">
				<input type="button" id="query" value="查经历" class="menu_button"
					onmousedown="changeBC('query')" onmouseup="originalBC('query')"
					onclick="javascript:window.location.href='st_query.jsp'">
				<input type="button" id="townee" value="查同乡" class="menu_button"
					onmousedown="changeBC('townee')" onmouseup="originalBC('townee')"
					onclick="javascript:window.location.href='query_townee.jsp'">
				<input type="button" id="schoolmate" value="查校友"
					class="menu_button" onmousedown="changeBC('schoolmate')"
					onmouseup="originalBC('schoolmate')"
					onclick="javascript:window.location.href='query_schoolmate.jsp'">			
			<div id="system_button">
				<input type="button" id="user" value="[用户改密]" class="system_button"
					onclick="javascript:window.location.href='user.jsp'"> | <input
					type="button" id="exit" value="[退出系统]" class="system_button"
					onclick="javascript:window.location.href='exit.jsp'">
			</div>
		</div>

		<div id="content"></div>

		<div id="links">
			友情链接： <a href="http://cpc.people.com.cn/GB/64162/394696/index.html"
				target="_blank">中国政要资料库——资料中心——人民网</a> <a
				href="http://ldzl.people.com.cn/dfzlk/front/firstPage.htm"
				target="_blank">人民网——地方领导资料库</a>
		</div>

		<div id="footer">@ 湖南科技大学——计算机科学与工程学院——网络工程专业——1405020102——陈韦铮</div>
	</div>
</body>
</html>
