<?php
require "FbCon.php";

$username = $_POST['username'];
$adminid = $_POST['adminid'];
$password = $_POST['password'];

$query= "SELECT `AdminId` FROM `App_Users` WHERE `AdminId` ='$adminid'";
$q_run=mysqli_query($conn,$query);
$query_num_rows= mysqli_num_rows($q_run);

if($query_num_rows !=0){

  $query= "SELECT `Username` FROM `App_Users` WHERE `Username` ='$username'";
  $q_user_run=mysqli_query($conn,$query);
  $q_user_num_rows= mysqli_num_rows($q_user_run);

  if($q_user_num_rows != 0){
    $query_update = "UPDATE `App_Users` SET  `Password` = '$password' WHERE `Username` = '$username'";
    $query_runner = mysqli_query($conn, $query_update);
    echo "Password successfully changed";

  }else{
    echo "Username not Found";
  }

}else{
  echo "AdminID not found";
}

$conn->close();
?>
