<?php 
require 'FbCon.php';



$query=mysqli_query($conn,"SELECT `Year`FROM `Year` ORDER BY `Year` ");
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



mysqli_close($conn);


?>