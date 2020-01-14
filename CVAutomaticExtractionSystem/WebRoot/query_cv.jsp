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
				<input type="button" id="home" value="首页" class="menu_button"
					onmousedown="changeBC('home')" onmouseup="originalBC('home')"
					onclick="javascript:window.location.href='home.jsp'"> 
				<input
					type="button" id="cv_extraction" value="查简历 >" class="menu_button"
					onmousedown="changeBC('cv_extraction')"
					onmouseup="originalBC('cv_extraction')"
					onclick="javascript:window.location.href='query_cv.jsp'"> 
				<input
					type="button" id="query" value="查经历" class="menu_button"
					onmousedown="changeBC('query')" onmouseup="originalBC('query')"
					onclick="javascript:window.location.href='st_query.jsp'"> 
				<input type="button" id="townee" value="查同乡" class="menu_button"
					onmousedown="changeBC('townee')" onmouseup="originalBC('townee')"
					onclick="javascript:window.location.href='query_townee.jsp'">
				<input type="button" id="schoolmate" value="查校友" class="menu_button"
					onmousedown="changeBC('schoolmate')"
					onmouseup="originalBC('schoolmate')"
					onclick="javascript:window.location.href='query_schoolmate.jsp'">
			<div id="system_button">
				<input type="button" id="user" value="[用户改密]" class="system_button"
					onclick="javascript:window.location.href='user.jsp'"> | <input
					type="button" id="exit" value="[退出系统]" class="system_button"
					onclick="javascript:window.location.href='exit.jsp'">
			</div>
		</div>

		<div id="content">
			<mid_name>查简历</mid_name>
			<p>
			<form action="query_cv.jsp" method="get">
				<a>字段：</a>
				<select name="column" class="input">
  					<option value="姓名">姓名</option>
  					<option value="性别">性别</option>
  					<option value="民族">民族</option>
 					<!--option value="籍贯">籍贯</option>  -->
  					<option value="党派">党派</option>
 					<!--<option value="毕业学校">毕业学校</option>  -->
				</select>
				<a>值：</a><input type="text" name="value" class="input"> <input
					type="submit" id="query" value="查" class="content_button">
			</form>
			<div id="label">
				<div id="label_name">简历</div>
			</div>
			<div id="secondary_content">
				<%
					String column = request.getParameter("column");
					String value = request.getParameter("value");
					DBAccessBean db1 = new DBAccessBean("curriculum_vitae");
					if (db1.createConnection()) {
						String cv_no = "0000";// 记录简历编号
						// 查基本信息
						String sql = "select * from basic_information where " + column +"='"
								+ value + "'";
						db1.query(sql);
						while (db1.hasNext()) {
							cv_no = db1.getValue("简历编号");
							out.println("姓名：" + db1.getValue("姓名") + "<p>");
							out.println("性别：" + db1.getValue("性别") + "<p>");
							out.println("民族：" + db1.getValue("民族") + "<p>");
							out.println("出生年月：" + db1.getValue("出生年月") + "<p>");
							out.println("籍贯：" + db1.getValue("籍贯") + "<p>");
							out.println("参加工作时间：" + db1.getValue("参加工作时间") + "<p>");
							out.println("党派：" + db1.getValue("党派") + "<p>");
							out.println("毕业学校：" + db1.getValue("毕业学校") + "<p>");
							DBAccessBean db2 = new DBAccessBean("curriculum_vitae");
							if (db2.createConnection()) {
								// 查任职信息
								sql = "select * from position where 简历编号='" + cv_no
										+ "'";
								db2.query(sql);
								boolean flag = true;
								while (db2.hasNext()) {
									if (flag) {
										out.println("现任职位：");
										flag = false;
									}
									out.println(db2.getValue("现任职位") + " ");
								}
								out.println("<p>");
								// 查履历信息
								sql = "select * from experience where 简历编号='" + cv_no
										+ "'";
								db2.query(sql);
								flag = true;
								while (db2.hasNext()) {
									if (flag) {
										out.println("个人履历：<p>");
										flag = false;
									}
									out.println(db2.getValue("起始时间"));
									if ("null".equals(db2.getValue("截止时间")))
										out.println("  ");
									else
										out.println("至" + db2.getValue("截止时间") + " ");
									if (!"null".equals(db2.getValue("地点")))
										out.println(db2.getValue("地点") + "");
									out.println(db2.getValue("职位") + "<p>");
								}
							}
							db2.close();
							out.println("------------------------------------------------------------------------------------" + "<p>");
						}
						db1.close();
					}
				%>
			</div>
		</div>

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
