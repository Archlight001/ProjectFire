<?php 
require 'FbCon.php';

$value= $_POST['Value'];


if(isset($_POST['Value'])){
$query=mysqli_query($conn,"SELECT `First Name`,`Middle Name`,`Surname`,`Phone Number`,`Profile_pic url`,`Year Graduated` FROM `Marshalls` WHERE `Year Graduated` = '$value' ");
$query_num_rows= mysqli_num_rows($query);

if($query_num_rows!=0){
	while($row=mysqli_fetch_array($query))
	{
	$flag[]=$row;
	}

print(json_encode($flag));


}else{
	echo "No Data Found";
}
}


mysqli_close($conn);


?>