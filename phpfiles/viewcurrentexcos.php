<?php 
require 'FbCon.php';


$query=mysqli_query($conn,"SELECT * FROM `Excos` ORDER BY `Post`");


if($query){
	while($row=mysqli_fetch_array($query))
	{
	$flag[]=$row;
	}

print(json_encode($flag));


}else{
	echo "No Data Found";
}



mysqli_close($conn);







?>