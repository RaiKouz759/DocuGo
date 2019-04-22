<?php

error_reporting(0);

$db_name = "id9286536_budak";
$mysql_user = "id9286536_app";
$mysql_pass = "12345";
$server_name = "localhost";

$con = mysqli_connect($server_name, $mysql_user, $mysql_pass, $db_name);

if(!$con){
    echo '{"message":"Unable to connect to the database."}';
}
