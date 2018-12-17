<?php
require "FbCon.php";

$username = $_POST["username"];
$password = $_POST["password"];

$query= "SELECT `Username` FROM `App_Users` WHERE `Username` ='$username' AND `Password` ='$password' ";
$q_user_run=mysqli_query($conn,$query);
$q_user_num_rows= mysqli_num_rows($q_user_run);

if($q_user_num_rows !=0){
  echo "Login Success";
}
else{
  echo "Incorrect Username or Password";
}

$conn->close();
?>
