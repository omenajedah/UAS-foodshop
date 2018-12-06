<?php

class mst_category extends model {
	function __construct() {
		parent::__construct();
		$this->TableName = "mst_category";
		$this->SQLFile = "mst_category.sql";	
	}




}