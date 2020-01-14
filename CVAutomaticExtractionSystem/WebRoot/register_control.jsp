<%@ page import="hnust.cwz.javabeans.BusinessBean"%>
<%
	String username = request.getParameter("Username");
	String password = request.getParameter("Password");
	String cer_password = request.getParameter("Cer_Password");

	if (username.equals("") || password.equals("")) {
		response.sendRedirect("register.jsp");
	} else if (!password.equals(cer_password)) {
		response.sendRedirect("register.jsp");
	} else {
		BusinessBean businessBean = new BusinessBean();
		businessBean.user_insert(username, password);
		response.sendRedirect("login.jsp");
	}
%>
