<?php 
require 'FbCon.php';

$pnumber = $_POST['PhoneNumber'];


$query= "SELECT `Phone Number` FROM `Marshalls` WHERE `Phone Number` ='$pnumber' ";
$q_run=mysqli_query($conn,$query);
$query_num_rows= mysqli_num_rows($q_run);

if($query_num_rows!=0){
	echo "Marshall";
}else{
	$query= "SELECT `Phone Number` FROM `Members` WHERE `Phone Number` ='$pnumber' ";
	$q_run=mysqli_query($conn,$query);
	$query_num_rows= mysqli_num_rows($q_run);

	if($query_num_rows!=0){
	echo "Member";
}
}


?>