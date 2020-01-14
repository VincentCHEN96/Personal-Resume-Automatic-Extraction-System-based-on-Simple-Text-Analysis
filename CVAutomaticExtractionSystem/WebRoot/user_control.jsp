<%@ page import="hnust.cwz.javabeans.BusinessBean"%>
<%
	//String old_password = request.getParameter("Old_Password");
	String password = request.getParameter("Password");
	String cer_password = request.getParameter("Cer_Password");
	
	//if ("".equals(old_password)) {
		//response.sendRedirect("user.jsp");
	//} else 
	if ("".equals(password)) {
		response.sendRedirect("user.jsp");
	} else if (!password.equals(cer_password)) {
		response.sendRedirect("user.jsp");
	} else {
		BusinessBean businessBean = new BusinessBean();
		businessBean.user_update(password,
		session.getAttribute("username").toString());
		response.sendRedirect("home.jsp");
	}
%>
