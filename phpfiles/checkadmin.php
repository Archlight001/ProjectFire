<?php 
require "FbCon.php";

$admin = $_POST["admin"];
$username = $_POST["Username"];
$password = $_POST["Password"];

$query= "SELECT `AdminId` FROM `App_Users` WHERE `AdminId` ='$admin' AND `Username` = '$username' AND `Password` = '$password'";
$q_run=mysqli_query($conn,$query);
$query_num_rows= mysqli_num_rows($q_run);

if($query_num_rows!=0){
	echo "Correct admin ID";
}else{
	echo "Wrong";
}

?>