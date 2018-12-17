<?php 
require 'FbCon.php';
$username = $_POST['Username'];
$password= $_POST['Password'];

$query= mysqli_query($conn, "SELECT `AdminId` FROM `App_Users` WHERE `Username` = '$username' And `Password`='$password'");

$row= mysqli_fetch_array($query);

echo $row['AdminId'];

?>