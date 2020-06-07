<?php

    include_once 'config.php';

    class DB_connect {

      private $connect;



      public function __construct() {
        $DB_host = "localhost";
        $DB_name = "test_ustapp";
        $this->connect = mysqli_connect(DB_HOST, DB_USER, DB_PASSWORD, DB_NAME);

    }

      public function getDb() {
              return $this->connect;
          }
    }

?>
