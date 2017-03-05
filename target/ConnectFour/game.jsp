<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
	<title>Connect Four</title>
</head>
<body>
	<h1 style="text-align:center">Connect Four</h1>
	<h3 style="text-align:center">Created by Sunwoo Yim</h3>

	<h1 style = "text-align:center">${winner}</h1>

	${boardView}

	<form action="GameServlet" method="GET" style="text-align:center">
		<button name="play" value="-1" style="width:100px; height:60px">Start Over</button>
	</form>
</body>
</html>