<?php 
require "FbCon.php";

$phonenumber = $_POST['PhoneNumber'];
$query=mysqli_query($conn,"SELECT * FROM Members WHERE `Phone Number` = '$phonenumber' ");
$query_num_rows= mysqli_num_rows($query);
if($query_num_rows!=0)
{
while($row=mysqli_fetch_array($query))
	{
	$flag[]=$row;
	}
print(json_encode($flag));

}else if ($query_num_rows==0){
	$query2=mysqli_query($conn,"SELECT * FROM Excos WHERE `Phone Number` = '$phonenumber' ");
	if($query2){
	while($row=mysqli_fetch_array($query2))
	{
	$flag[]=$row;
	}

print(json_encode($flag));

}}
mysqli_close($conn);


?>