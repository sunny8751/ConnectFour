<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>Connect Four</title>
</head>
<body style="background-color:lightgrey;">
	<h1 style="text-align:center">Connect Four</h1>
	<h3 style="text-align:center">Created by Sunwoo Yim</h3>
	<form action="GameServlet" method="POST" style="text-align:center">
		Name:<br>
		<input type="text" name="name"></input><br><br>
		Who goes first?<br>
		<div>
			<input type="radio" name="firstMove" value="Player">Player</input><br>
			<input type="radio" name="firstMove" value="Computer">Computer</input><br>
		</div><br>
		Difficulty:<br>
		<div>
			<input type="radio" name="difficulty" value="Easy">Easy</input><br>
			<input type="radio" name="difficulty" value="Medium">Medium</input><br>
			<input type="radio" name="difficulty" value="Hard">Hard</input><br>
		</div><br>
		<input type="submit"></input>
	</form>
</body>
</html>