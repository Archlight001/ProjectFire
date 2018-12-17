<?php 
require "FbCon.php";

$phonenumber = $_POST['PhoneNumber'];
$query=mysqli_query($conn,"SELECT * FROM Marshalls WHERE `Phone Number` = '$phonenumber' ");

if($query)
{
while($row=mysqli_fetch_array($query))
	{
	$flag[]=$row;
	}
print(json_encode($flag));

}
mysqli_close($conn);


?>