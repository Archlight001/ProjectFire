<?php 
require 'FbCon.php';

$firstname = $_POST['firstname'];
$middlename = $_POST['middlename'];
$surname = $_POST['surname'];
$phonenumber = $_POST['phonenumber'];

$query= "SELECT `First Name` FROM `Members` WHERE `First Name` ='$firstname' AND `Middle Name` = '$middlename' AND `Surname` = '$surname' AND `Phone Number`= '$phonenumber'";
$queryphone = "SELECT `Phone Number` FROM `Members` WHERE `Phone Number`='$phonenumber' ";
$qphonerun= mysqli_query($conn,$queryphone);
$queryphonenumrows = mysqli_num_rows($qphonerun);
$q_run=mysqli_query($conn,$query);
$query_num_rows= mysqli_num_rows($q_run);

if($query_num_rows!=0){
	echo 'Duplicate Data Found';
}else if($queryphonenumrows !=0){
	echo 'Phone Number has Already Been Used';
}
else{
	echo 'None';
}


?>