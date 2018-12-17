<?php 
define('DB_NAME', 'Firebrand Drama');
define('DB_HOST', 'localhost');
define('DB_PASSWORD', '');
define('DB_USERNAME', 'root');


$upload_path = ('profilepics/');
$server_ip = gethostbyname(gethostname());
$upload_url = 'http://192.168.43.194/FB_DATA/'.$upload_path;
$response = array();

$First_Name=$_POST["firstname"];
$Surname=$_POST["surname"];
$Phone_Number=$_POST["phonenumber"];
$State =$_POST["state"];
$Course = $_POST["course"];
$Level =$_POST["level"];
$Year = $_POST["year"];
$bday = $_POST["birthday_day"];
$bmonth = $_POST["birthday_month"];


if($_SERVER['REQUEST_METHOD']== 'POST'){
	$conn = mysqli_connect(DB_HOST, DB_USERNAME, DB_PASSWORD, DB_NAME) or die('Unable to connect to database');
	$fileinfo = pathinfo($_FILES['image']['name']);
	$extension = $fileinfo['extension'];

	$file_url = $upload_url .getFileName() . '.'.$extension;
	$file_path = $upload_path .getFileName() . '.'.$extension;

	try{
		move_uploaded_file($_FILES['image']['tmp_name'], $file_path);
		if(isset($_POST['middlename'])){
			$Middle_name= $_POST['middlename'];
				$sql="INSERT INTO `Members` VALUES('','".mysqli_real_escape_string($conn,$First_Name)."','".mysqli_real_escape_string($conn,$Middle_name)."','".
    mysqli_real_escape_string($conn,$Surname)."','".mysqli_real_escape_string($conn,$Phone_Number)."','".mysqli_real_escape_string($conn,$bday)."','".mysqli_real_escape_string($conn,$bmonth)."','".mysqli_real_escape_string($conn,$State)."','".mysqli_real_escape_string($conn,$Course)."','".mysqli_real_escape_string($conn,$Level)."','".mysqli_real_escape_string($conn,$Year)."','".mysqli_real_escape_string($conn,$file_url)."')";
		}else{
	
		$sql="INSERT INTO `Members` VALUES('','".mysqli_real_escape_string($conn,$First_Name)."','','".
    mysqli_real_escape_string($conn,$Surname)."','".mysqli_real_escape_string($conn,$Phone_Number)."','".mysqli_real_escape_string($conn,$bday)."','".mysqli_real_escape_string($conn,$bmonth)."','".mysqli_real_escape_string($conn,$State)."','".mysqli_real_escape_string($conn,$Course)."','".mysqli_real_escape_string($conn,$Level)."','".mysqli_real_escape_string($conn,$Year)."','".mysqli_real_escape_string($conn,$file_url)."')";
    }
    $result =mysqli_query($conn,$sql); 
		if($result){
					echo 'Uploaded Successfully';
				}else{
					printf("Error Message: %s\n", mysqli_error($conn));
					
				}

			}catch(Exception $e){
				$response['message'] = $e->getMessage();
			}
			mysqli_close($conn);

		echo json_encode($response);
}

	function getFileName(){
		$con = mysqli_connect(DB_HOST, DB_USERNAME, DB_PASSWORD, DB_NAME) or die('Unable to connect');
		$sql = "SELECT max(id) as id FROM Members";
		$result = mysqli_fetch_array(mysqli_query($con, $sql));
		mysqli_close($con);
		$First_Name=$_POST["firstname"];
		$Surname=$_POST["surname"];
		if($result['id']==null){
			return $First_Name.$Surname."1";
		}else{
			return $First_Name.$Surname.++$result['id'];
		}
	}

?>