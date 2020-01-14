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

<title>改密 官查——个人简历自动抽取系统n</title>

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
	function myAlert() {
		/*if (document.form1.Old_Password.value == "") {
			alert("请输入原密码!");
			return false;
		}*/
		if (document.form1.Password.value == "") {
			alert("请输入新密码!");
			return false;
		}
		if (document.form1.Cer_Password.value != document.form1.Password.value) {
			alert("确认密码与设定密码不一致，请重新输入!");
			return false;
		}
		alert("修改成功！");
	}

	function changeBC(id) {
		var mychar = document.getElementById(id); //获取要改变颜色的id
		mychar.style.color = "#2D2D3F";
		mychar.style.backgroundColor = "#F0F0F0";
	}

	function originalBC(id) {
		var mychar = document.getElementById(id); //获取要改变颜色的id
		mychar.style.color = "#F0F0F0";
		mychar.style.backgroundColor = "#2D2D3F";
		window.location.href = "user.jsp";
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
				<input type="button" id="user2" value="用户改密  >" class="menu_button"
					onmousedown="changeBC('user2')" onmouseup="originalBC('user2')">
				<input type="button" id="user3" value="返回首页" class="menu_button"
					onmousedown="changeBC('user3')" onmouseup="originalBC('user3')"
					onclick="javascript:window.location.href='home.jsp'">
			<div id="system_button">
				<input type="button" id="user" value="[用户改密]" class="system_button"
					onclick="javascript:window.location.href='user.jsp'"> | <input
					type="button" id="exit" value="[退出系统]" class="system_button"
					onclick="javascript:window.location.href='exit.jsp'">
			</div>
		</div>

		<div id="content">
			<mid_name>用户改密</mid_name>
			<div id="label">
				<div id="label_name">用户改密</div>
			</div>
			<div id="secondary_content">
				<form action="user_control.jsp" method="post" name="form1"
					id="form1">
					<table width="500px" border="0"
						style="color: #242424; font-size: 15px;">
						<tr>
							<th scope="row">用户名：</th>
							<td><label for="Username"></label> <input type="text"
								name="Username" class="input" disabled="disabled"
								style="background-color: #F0F0F0;" /></td>
						</tr>
						<!--
						<tr>
							<th scope="row">原密码：</th>
							<td><label for="Old_Password"></label> <input type="text"
								name="Old_Password" class="input" /> *必填</td>
						</tr>
						-->
						<tr>
							<th scope="row">新密码：</th>
							<td><label for="Password"></label> <input type="password"
								name="Password" class="input" /> *必填</td>
						</tr>
						<tr>
							<th scope="row">确认新密码：</th>
							<td><label for="Cer_Password"></label> <input
								type="password" name="Cer_Password" class="input" /> *必填</td>
						</tr>
					</table>
					<p style="margin-top: 20px;">
						<input type="submit" id="yes" value="提交" class="content_button"
							onmouseup="myAlert()" /> <input type="reset" id="no" value="重填"
							class="content_button" />
					</p>
				</form>
			</div>
		</div>

		<div id="links">
			友情链接： <a href="http://cpc.people.com.cn/GB/64162/394696/index.html"
				target="_blank">中国政要资料库——资料中心——人民网</a> <a
				href="http://ldzl.people.com.cn/dfzlk/front/firstPage.htm"
				target="_blank">人民网——地方领导资料库</a>
		</div>

		<div id="footer">@湖南科技大学——计算机科学与工程学院——网络工程——1405020102——陈韦铮</div>
	</div>
</body>
</html>
