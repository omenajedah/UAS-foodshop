<?php

class mst_seller extends model {
	function __construct() {
		parent::__construct();
		$this->TableName = "mst_seller";
		$this->SQLFile = "mst_seller.sql";	
	}




}