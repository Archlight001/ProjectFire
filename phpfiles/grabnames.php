<?php 
require "FbCon.php";
$username = $_POST["username"];

$query= "SELECT `Surname`,`Username`,`First Name`  FROM `App_Users` WHERE `Username` ='$username' ";
$q_user_run=mysqli_query($conn,$query);
$q_user_num_rows= mysqli_num_rows($q_user_run);

if($q_user_num_rows !=0){
  $row = mysqli_fetch_assoc($q_user_run);

  echo $row['First Name']. "  ". $row['Surname'];
}
else{
  echo "Nil";
}

?>