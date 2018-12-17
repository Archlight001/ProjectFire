<?php 
require "FbCon.php";

$adminvalue = $_POST["AdminValue"];

$query= "SELECT `AdminId` FROM `App_Users` WHERE `AdminId` ='FB001' AND `Username` = '$adminvalue'";
$q_run=mysqli_query($conn,$query);
$query_num_rows= mysqli_num_rows($q_run);

if($query_num_rows!=0){
	echo "Correct";
}else{
	echo "Wrong";
}

?>