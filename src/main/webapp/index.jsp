<html>
<%@ page import="com.myproj.domain.Ftp"%>
<body>
<h2>Ftp tasks are starting now!</h2>
<%
    System.out.println("------------------------tomcat is started------------------------");
    String[] arrays = {"0","1","2"};
    Ftp.main(arrays);
%>
</body>
</html>