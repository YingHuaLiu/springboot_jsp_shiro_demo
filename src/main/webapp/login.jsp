<%@page contentType="text/html; UTF-8" pageEncoding="utf-8" isELIgnored="false" %>
<!doctype html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
</head>
<body>
登录页面
<form action="${pageContext.request.contextPath}/user/login" method="post">
    username:<input type="text" name="username">
    password:<input type="password" name="password">
    <button>登录</button>
</form>
</body>
</html>