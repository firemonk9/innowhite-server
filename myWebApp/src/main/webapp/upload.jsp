<html>
<head></head>
<body>
	<p>Commons File Upload Example</p>
	<form action="servlet/WBDeployer" enctype="multipart/form-data"
		method="POST">

		<select name="user">
			<option value="Dhiraj">Dhiraj</option>
			<option value="Jonathan">Jonathan</option>
			<option value="Prashanth">Prashanth</option>
			<option value="Praveen">Praveen</option>
			<option value="Sanjay">Sanjay</option>
		</select> <input type="file" name="file1"><br> <input
			type="Submit" value="Upload File"><br>

	</form>
</body>
</html>