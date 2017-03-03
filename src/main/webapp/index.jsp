<html>
<head>
	<title>Connect Four</title>
</head>
<body>
	<h2 align="center">Created by Sunwoo Yim</h2>
	<canvas id="myCanvas" width="600" height="500" style="border:1px solid #000000;"></canvas>
	<form method="get" action="${pageContext.request.contextPath}/GameServlet">
    	<input type="button" value="spawn">
	</form>
</body>
</html>