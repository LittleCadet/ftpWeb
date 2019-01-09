<html>
<%@ page import="com.myproj.domain.Ftp"%>
<body>
<h2>Ftp tasks are starting now!</h2>
<%
    System.out.println("------------------------tomcat容器已启动，开始跑定时任务------------------------");
    String[] arrays = {"0","1","2"};
    Ftp.main(arrays);
    System.out.println("------------------------跑定时任务中------------------------");
%>
</body>
</html>
