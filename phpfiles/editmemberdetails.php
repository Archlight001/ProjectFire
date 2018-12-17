<?php 
require 'FbCon.php';

$phonenumber =$_POST['PhoneNumber'];
$field = $_POST['Field'];
$new_data = $_POST['NewData'];
$query= mysqli_query($conn,
	
	"UPDATE `Members` SET `$field` = '$new_data' WHERE `Members`.`Phone Number` = '$phonenumber'");

if($query){
	echo "Field Updated Successfully";
}else{
	echo mysqli_error($conn);
}

?>