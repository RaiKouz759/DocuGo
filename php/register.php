<?php
error_reporting(0);
require "init.php";

$name = $_POST["name"];
$password = $_POST["password"];
$email = $_POST["email"];

//$name = "sdf";
//$password = "sdf";
//$email = "sdf@r54";

$sql = "INSERT INTO user_info (id,name, password, email) VALUES (1, '$name', '$password', '$email')"; //correct foramt

//INSERT INTO `user_info`(`id`, `name`, `password`, `email`) VALUES ([value-1],[value-2],[value-3],[value-4])
if(!mysqli_query($con, $sql)){
    echo '{"message":"Unable to save the data to the database."}';
}else{
  echo 'Able to save data to the database';
}
mysqli_close($con);
?>
