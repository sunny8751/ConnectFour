<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>Connect Four</title>
</head>
<body>
	<h1 style="text-align:center">Connect Four</h1>
	<h3 style="text-align:center">Created by Sunwoo Yim</h3>

	<table align="center" style="width:70%">
	<%! int[][] board = new int[6][7]; %>
	<% for (int i = 0; i < board.length; i++) { %>
		<% if (i == 0) { %>
		<tr>
			<% for (int j = 0; j < board[0].length; j++) { %>
				<td>
					<form action="GameServlet" method="GET" style="text-align:center">
						<button name="spawn" value="1" style="text-align:center; width:80px">Red</button>
					</form>
				</td>
			<% } %>
		</tr>
		<% } %>

		<tr>
		<% for (int j = 0; j < board[0].length; j++) { %>
			<%! String src = ""; %>
			<%
				if (board[i][j] == 0) { 
					src = "red_circle.svg";
				} else if (board[i][j] == 1) {
				    src = "red_circle";
				} else if (board[i][j] == 2) {
				    src = "blue_circle";
				}
			%>
			<td><img id="<%= 10*i + j %>" alt="Slot" src="<%= src %>" style="width:64px;height:64px;"></td>
		<% } %>
		</tr>
	<% } %>
	</table>

<%-- 	<% for (int i = 0; i < board[0].length; i++) { %> --%>
<!-- 		<button type="button" style="width: 100px">Click Me!</button> -->
<%-- 	<% } %> --%>

<!-- 	<form action="GameServlet" method="GET" style="text-align:center"> -->
<!-- 		<button name="spawn" value="1" style="width:100px">Red</button> -->
<!-- 		<button name="spawn" value="2" style="width:100px">Blue</button> -->
<!-- 	</form> -->
</body>
</html>