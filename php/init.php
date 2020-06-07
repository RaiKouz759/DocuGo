<?php

$db_name = "test_ustapp";
$mysql_user = "root";
$mysql_pass = "";
$host = "localhost";


$con = new PDO("mysql:host=$host;dbname=$db_name", $mysql_user, $mysql_pass );

if(!$con){
    echo '<h1>MySQL Server is not connected</h1>';
}else{
  echo '<h1>MySQL Server is connected</h1>';
}
echo $con
?>
