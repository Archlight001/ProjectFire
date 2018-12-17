<?php 
require 'FbCon.php';
   $month = date('F');
   
$query=mysqli_query($conn,"SELECT `First Name`,`Middle Name`,`Surname`,`Birthday Day`,`Birthday Month`,`Phone Number` FROM `Members` 
	WHERE `Birthday Month` = '$month' ORDER BY `Birthday Day` ASC");

$query2=mysqli_query($conn,"SELECT `First Name`,`Middle Name`,`Surname`,`Birthday Day`,`Birthday Month`,`Phone Number` FROM `Marshalls` WHERE `Birthday Month` = '$month' ORDER BY `Birthday Day` ASC");
//`Birthday Day`ASC
if($query){
	while($row=mysqli_fetch_array($query))
	{
	$flag[]=$row;
	}
	while($row=mysqli_fetch_array($query2))
	{
	$flag[]=$row;
	}
	
print(json_encode($flag));

}else{
	echo "No Data Found";
}



mysqli_close($conn);




?>