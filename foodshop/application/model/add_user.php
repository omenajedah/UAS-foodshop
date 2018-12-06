<?php

class add_user extends model {
	function __construct() {
		parent::__construct();
		$this->TableName = "mst_user";
	}

	function putData($param) {
		if (!$param->C_USERNAME || !$param->C_PASSWORD || !$param->C_NAME || !$param->C_ADDRESS) {
			return array('success'=>false, 'reason'=>'You must supplied data completely');
		}
		$param->C_STATUS = 1;
		return parent::putData($param);
	}


}