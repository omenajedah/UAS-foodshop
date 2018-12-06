<?php

class mst_user extends model {
	function __construct() {
		parent::__construct();
		$this->TableName = "mst_user";
		$this->SQLFile = "mst_user.sql";	
	}




}