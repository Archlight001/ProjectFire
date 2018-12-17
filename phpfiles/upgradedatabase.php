<?php 
require 'FbCon.php';

$year =date("Y");
$yearplus = date('Y')+1;
$query = "INSERT INTO `Marshalls` (`First Name`, `Middle Name`, `Surname`, `Phone Number`, `Birthday Day`, `Birthday Month`, `State of Origin`, `Course`,`Profile_pic url`) SELECT `First Name`, `Middle Name`, `Surname`,`Phone Number`,`Birthday Day`, `Birthday Month`, `State of Origin`, `Course`,`Profile_pic url` FROM `Members` WHERE `level` = '400' AND `Course` != 'Agriculture'";

$query_run=mysqli_query($conn,$query);

if($query_run){
	
	$query = "INSERT INTO `Marshalls` (`First Name`, `Middle Name`, `Surname`, `Phone Number`, `Birthday Day`, `Birthday Month`, `State of Origin`, `Course`,`Profile_pic url`) SELECT `First Name`, `Middle Name`, `Surname`,`Phone Number`,`Birthday Day`, `Birthday Month`, `State of Origin`, `Course`,`Profile_pic url` FROM `Members` WHERE `level` = '500' AND `Course` = 'Agriculture'";

	$query_run=mysqli_query($conn,$query);


	if($query_run){
		$query_marshall_servedas = mysqli_query($conn,"UPDATE `Marshalls` SET `Served As` = 'MEMBER' WHERE `Marshalls`.`Served As` = ''");

		$query_addfromexcos = mysqli_query($conn, "INSERT INTO `Marshalls` (`First Name`, `Middle Name`, `Surname`, `Phone Number`, `Birthday Day`, `Birthday Month`, `State of Origin`, `Course`,`Served As`,`Profile_pic url`) SELECT `First Name`, `Middle Name`, `Surname`,`Phone Number`,`Birthday Day`, `Birthday Month`, `State of Origin`, `Course`,`Post`,`Profile_pic url` FROM `Excos` WHERE `level` = '400' AND `Course` != 'Agriculture'");
		$query_addfromexcos2 = mysqli_query($conn, "INSERT INTO `Marshalls` (`First Name`, `Middle Name`, `Surname`, `Phone Number`, `Birthday Day`, `Birthday Month`, `State of Origin`, `Course`,`Served As`,`Profile_pic url`) SELECT `First Name`, `Middle Name`, `Surname`,`Phone Number`,`Birthday Day`, `Birthday Month`, `State of Origin`, `Course`,`Post`,`Profile_pic url` FROM `Excos` WHERE `level` = '500' AND `Course` = 'Agriculture'");

		$query_deleteduplicate = mysqli_query($conn, 
			"DELETE FROM `Marshalls` USING `Marshalls`, `Marshalls` as `vtable`
			WHERE (`Marshalls`.`id` < `vtable`.`id`)
			AND (`Marshalls`.`Phone Number`=`vtable`.`Phone Number`)");

		$queryrun4 = mysqli_query($conn,"ALTER TABLE `Marshalls` DROP `id`");
		$queryrun5 = mysqli_query($conn,"ALTER TABLE `Marshalls` AUTO_INCREMENT = 1");
		$queryrun6 = mysqli_query($conn,"ALTER TABLE `Marshalls` ADD `id` int UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY FIRST");
				

		$query_second = "UPDATE `Marshalls` SET `Year Graduated` = '$year' WHERE `Marshalls`.`Year Graduated` = ''";
		$query_run2=mysqli_query($conn,$query_second);

		$exco_history_query = mysqli_query($conn, "INSERT INTO `Exco_History` (`First Name`, `Middle Name`, `Surname`, `Phone Number`, `Birthday Day`, `Birthday Month`, `State of Origin`, `Course`,`Post`,`Profile_pic url`) SELECT `First Name`, `Middle Name`, `Surname`,`Phone Number`,`Birthday Day`, `Birthday Month`, `State of Origin`, `Course`,`Post`,`Profile_pic url` FROM `Excos` ");
		$exco_history_query2 = mysqli_query($conn, "UPDATE `Exco_History` SET `Year Served` = '$year' WHERE `Exco_History`.`Year Served` = ''");
	
		if($query_run2){
			$querythird = "DELETE FROM `Members` WHERE `level` = '400' AND `Course` != 'Agriculture'";
			$query_run3=mysqli_query($conn,$querythird);
		
			if($query_run3){
				$querythird = "DELETE FROM `Members` WHERE `level` = '500' AND `Course` = 'Agriculture'";
				$query_run3=mysqli_query($conn,$querythird);

				if($query_run3){
				
					$queryrun4 = mysqli_query($conn,"ALTER TABLE `Members` DROP `id`");
					$queryrun5 = mysqli_query($conn,"ALTER TABLE `Members` AUTO_INCREMENT = 1");
					$queryrun6 = mysqli_query($conn,"ALTER TABLE `Members` ADD `id` int UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY FIRST");
					$query_level_plusone = mysqli_query($conn, "UPDATE `Members` SET `Level` = `Level`+100");

					$query_clearexcotable = mysqli_query($conn, "DELETE FROM `Excos`");
					$queryrun4 = mysqli_query($conn,"ALTER TABLE `Excos` DROP `id`");
					$queryrun5 = mysqli_query($conn,"ALTER TABLE `Excos` AUTO_INCREMENT = 1");
					$queryrun6 = mysqli_query($conn,"ALTER TABLE `Excos` ADD `id` int UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY FIRST");
				

					$queryrun7 = mysqli_query($conn,"INSERT INTO `Year` (`Year`) VALUES ('$yearplus')");

					if($queryrun7){
					echo 'Done';
					}
	
			}
		}

	}
}

}else{
	echo  mysqli_error($conn);
}

?>