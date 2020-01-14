<%@ page import="hnust.cwz.javabeans.BusinessBean"%>
<%
	String username = request.getParameter("username");
	String password = request.getParameter("password");

	if (username == null || password == null) {
		response.sendRedirect("login.jsp");
	} else {
		BusinessBean businessBean = new BusinessBean();
		boolean isVaild = businessBean.vaild(username, password);
		out.print(isVaild);
		if (isVaild) {
			session.setAttribute("username", username);
			response.sendRedirect("home.jsp");
		} else {
			response.sendRedirect("login.jsp");
		}
	}
%>
