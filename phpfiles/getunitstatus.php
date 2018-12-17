<?php 
require 'FbCon.php';

$pnumber = $_POST['PhoneNumber'];

$query = mysqli_query($conn, "SELECT `Phone Number` FROM `Members` WHERE `Phone Number` = '$pnumber'");

if(mysqli_num_rows($query) != 0){
	echo "Member";
}else{
	echo "Marshall";
}

?>