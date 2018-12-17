<?php 
require 'FbCon.php';

$query =mysqli_query($conn,"SELECT count(1) FROM `Excos`") ;
$result = mysqli_fetch_array($query);

$total = $result[0];

echo $total;

?>