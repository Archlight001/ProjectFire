<?php 
require 'FbCon.php';

$post = $_POST['Post'];
$phonenumber = $_POST['PhoneNumber'];

$query= "SELECT `Post` FROM `Excos` WHERE `Post` ='$post'";
$q_run=mysqli_query($conn,$query);
$query_num_rows= mysqli_num_rows($q_run);

$query2 = mysqli_query($conn, "SELECT `Phone Number` FROM `Excos` WHERE `Phone Number` = '$phonenumber'");
$query_row= mysqli_num_rows($query2);

if($query_num_rows==0 && $query_row==0){

$query=mysqli_query($conn,"SELECT * FROM Members WHERE `Phone Number` = '$phonenumber' ");
$query_num_rows= mysqli_num_rows($query);
if($query_num_rows!=0)
{
	while($row=mysqli_fetch_array($query)){
	
	$firstname = $row['First Name'];
	$middlename = $row['Middle Name'];
	$surname=$row['Surname'];
	$phonenumber = $row['Phone Number'];
	$bday = $row['Birthday Day'];
	$bmonth = $row['Birthday Month'];
	$state = $row['State of Origin'];
	$course = $row['Course'];
	$level = $row['Level'];
	$year = $row['Year Joined'];
	$pic = $row['Profile_pic url'];
	}
	
	$query = mysqli_query($conn, "INSERT INTO `Excos` (`id`, `First Name`, `Middle Name`, `Surname`, `Phone Number`, `Birthday Day`, `Birthday Month`, `State of Origin`, `Course`, `Level`, `Year Joined`, `Post`, `Profile_pic url`) VALUES (NULL, '$firstname', '$middlename', '$surname', '$phonenumber', '$bday', '$bmonth', '$state', '$course', '$level', '$year', '$post', '$pic')");
	if($query){
		echo "Success";	
	}

	}

}else{
	echo "Sorry, this post has either been occupied by another Member or the Selected Member has already been assigned to another post.Kindly Check the Current Excos Tab for clarification. If this post was assigned to the Wrong person, Contact the Creator to rectify the issue";
}

?>