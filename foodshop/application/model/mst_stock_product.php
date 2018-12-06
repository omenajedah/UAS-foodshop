<?php

class mst_stock_product extends model {
	function __construct() {
		parent::__construct();
		$this->TableName = "mst_stock_product";
		$this->SQLFile = "mst_stock_product.sql";	
	}




}