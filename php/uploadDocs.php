<?php
error_reporting(0);
require "init.php";

//$name=$_POST("");//This will be the path to the file

$path_to_pdf='sample.pdf';

$sql="INSERT INTO document_log (DocID,DocVersion,FilePath) VALUES ('1','1','$path_to_pdf')";

if(!mysqli_query($con,$sql)){
    echo 'Cannot input path into db';
}
else{
    echo 'Successfully input path file into db';
}

?>